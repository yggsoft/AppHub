package com.angelo.logging.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angelo.logging.io.TextFileReader;
import com.angelo.logging.job.RetryAnalyster;
import com.angelo.logging.logger.ExceptionFragment;
import com.angelo.logging.logger.Job;
import com.angelo.logging.logger.LoggerFile;
import com.angelo.logging.templete.Rule;
import com.angelo.logging.templete.StringRule;
import com.angelo.logging.templete.Templete;
import com.angelo.logging.util.Constants;

public class DataCentre {
	private static final Logger LOG = LoggerFactory
			.getLogger(DataCentre.class);
	
	private String url = Constants.getInstance().getH2url();
	private String userName = "sa";
	private String password = "";
	
	static {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			LOG.error("database driver " + e);
			throw new DataAccessException(e);
		}
	}

	public DataCentre() {
	}
	
	public DataCentre(String url, String userName, String password) {
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	public Connection getConnection() {
		return getConnection(url, userName, password);
	}
	
	public Connection getConnection(String url, String userName, String password) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, userName, password);
			
		} catch (SQLException e) {
			LOG.error("database connection " + e);
			throw new DataAccessException(e);
		}
		return conn;
	}

	public boolean createTable() {
		Connection connection = getConnection();
		Statement st = null;
		try {
			st = connection.createStatement();
			st.execute(new TextFileReader("resources/scripts/initDatabase.sql").read());
		} catch (SQLException e) {
			LOG.error("database error " + e);
			return false;
		} finally {
			free(null, null, connection);
		}
		return true;
	}


	/**
	 * gets templetes which ExceptionFragment did not match. 
	 * 
	 * now get all templetes.
	 */
	public List<Templete> getNewTempletes(ExceptionFragment fragment) {
		return getTemplates();
	}

	private java.sql.Timestamp getTimeStamp(Date date) {
		return date == null ? null : new java.sql.Timestamp(date.getTime());
	}

	public ResultSet query(Connection conn, String sql, Object... params)
			throws SQLException {
		PreparedStatement ps = conn.prepareStatement(sql);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
		}
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public int update(Connection conn, String sql, Object... params)
			throws SQLException {
		PreparedStatement ps = conn.prepareStatement(sql);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
		}
		int count = ps.executeUpdate();
		return count;
	}

	public void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			LOG.error("database " + e);
			throw new DataAccessException();
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				LOG.error("database " + e);
				throw new DataAccessException();
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					LOG.error("database " + e);
					throw new DataAccessException();
				}
			}
		}
	}

	public List<ExceptionFragment> getUnAnalysizedFragments() {
		return getFragments("SELECT TOP "+Constants.DB_RESULTSET_SIZE+" ID, LOGFILEID, CONTEXT, DETAILMESSAGES, DATE FROM EXCEPTIONFRAGMENT E WHERE E.ISMATCHED = FALSE AND E.ANALYSISCOMPLETED = FALSE");
	}

	public List<ExceptionFragment> getFragments(String fragmentSql) {
		Connection connection = getConnection();

		List<ExceptionFragment> fragments = new ArrayList<ExceptionFragment>();
		ResultSet rs = null;
		try {
			rs = query(connection, fragmentSql);
			while (rs.next()) {
				ExceptionFragment fragment = new ExceptionFragment();
				fragment.setId(rs.getString("ID"));
				fragment.setLogfileId(rs.getString("LOGFILEID"));
				fragment.setContext(rs.getString("CONTEXT"));
				fragment.setDetailMessages(rs.getString("DETAILMESSAGES"));
				fragment.setDate(rs.getDate("DATE"));

				fragments.add(fragment);
			}
		} catch (SQLException e) {
			LOG.error("" + e);
		} finally {
			free(rs, null, connection);
		}
		return fragments;
	}

	public boolean extractLogger(LoggerFile loggerFile,
			List<ExceptionFragment> fragments) {
		Connection connection = getConnection();
		TransactionMgr.beginTansction(connection);

		String loggerFileId = IDGenerator.generateId();
		String logFileSql = "INSERT INTO LOGGERFILE(ID, FILENAME, WHICHDAY, IMPORTDAY) VALUES(?,?,?,?);";
		String fragmentSql = "INSERT INTO EXCEPTIONFRAGMENT(ID, CONTEXT, DETAILMESSAGES, DATE, LOGFILEID) VALUES(?,?,?,?,?)";

		try {
			update(connection, logFileSql, loggerFileId,
					loggerFile.getFileName(),
					getTimeStamp(loggerFile.getWhichDay()),
					getTimeStamp(loggerFile.getImportDay()));
			for (ExceptionFragment fragment : fragments) {
				String id = IDGenerator.generateId();
				update(connection, fragmentSql, id, fragment.getContext(),
						fragment.getDetailMessages(),
						getTimeStamp(fragment.getDate()), loggerFileId);
			}
		} catch (SQLException e) {
			LOG.error("" + e);
			TransactionMgr.rollbackAndClose(connection);
			return false;
		}
		TransactionMgr.commitAndClose(connection);
		return true;
	}

	public boolean completeAnalysis(Templete templete,
			ExceptionFragment fragment) {
		fragment.setAnalysisCompleted(true);
		fragment.setMatched(true);
		return analysize(templete, fragment);
	}

	private boolean analysize(Templete templete, ExceptionFragment fragment) {
		Connection connection = getConnection();
		TransactionMgr.beginTansction(connection);

		String relationSql = "INSERT INTO TEMPLATEEXCEPTIONFRAGMENT(TEMPID, FRAGMENTID) VALUES(?,?)";
		String fragmentSql = "UPDATE EXCEPTIONFRAGMENT SET ISMATCHED = ?, ANALYSISCOMPLETED = ? WHERE ID = ?";

		try {
			update(connection, relationSql, templete.getId(), fragment.getId());
			update(connection, fragmentSql, fragment.isMatched(),
					fragment.isAnalysisCompleted(), fragment.getId());
		} catch (SQLException e) {
			LOG.error("" + e);
			TransactionMgr.rollbackAndClose(connection);
			return false;
		}
		TransactionMgr.commitAndClose(connection);
		return true;
	}

	public boolean skipAnalysis(ExceptionFragment fragment) {
		Connection connection = getConnection();
		
		TransactionMgr.beginTansction(connection);
		String fragmentSql = "UPDATE EXCEPTIONFRAGMENT SET ANALYSISCOMPLETED = FALSE, ISMATCHED = TRUE WHERE ID = ?";

		try {
			update(connection, fragmentSql, fragment.getId());
		} catch (SQLException e) {
			LOG.error("" + e);
			TransactionMgr.rollbackAndClose(connection);
			return false;
		}
		TransactionMgr.commitAndClose(connection);
		return true;
	}
	
	public List<Templete> getTemplates() {
		Connection connection = getConnection();
		
		List<Templete> templetes = new ArrayList<Templete>();
		
		String templeteSql = "SELECT ID, NAME, CATEGORY, TITLE, RCA, REPRODUCESTEPS, PRIORITY, TIMESTAMP, IGNORE FROM TEMPLETE";
		ResultSet rs = null;
		try {
			rs = query(connection, templeteSql);
			while (rs.next()) {
				Templete templete = new Templete();
				templete.setId(rs.getString("ID"));
				templete.setName(rs.getString("NAME"));
				templete.setCategory(rs.getString("CATEGORY"));
				templete.setTitle(rs.getString("TITLE"));
				templete.setRCA(rs.getString("RCA"));
				templete.setReProduceSteps(rs.getString("REPRODUCESTEPS"));
				templete.setPriority(rs.getInt("PRIORITY"));
				templete.setTimestamp(getTimeStamp(rs.getDate("TIMESTAMP")));
				templete.setIgnore(rs.getBoolean("IGNORE"));
				
				templetes.add(templete);
				
			}
		} catch (SQLException e) {
			LOG.error("" + e);
		} finally {
			free(rs, null, connection);
		}
		
		return setTempleteRules(templetes);
	}

	private List<Rule> getRules(Templete templete) {
		Connection connection = getConnection();
		String ruleSql = "SELECT ID, TEMPLETEID,  FEATURE FROM STRINGRULE S WHERE S.TEMPLETEID = ?";
		List<Rule> rules = new ArrayList<Rule>();
		ResultSet rs = null;
		
		try {
			rs = query(connection, ruleSql, templete.getId());
			while (rs.next()) {
				StringRule rule = new StringRule();
				rule.setId(rs.getString("ID"));
				rule.setTempleteId(rs.getString("TEMPLETEID"));
				rule.setFeature(rs.getString("FEATURE"));
				rules.add(rule);
			}
		} catch (SQLException e) {
			LOG.error("" + e);
		} finally {
			free(rs, null, connection);
		}
		return rules;
	}
	
	private List<Templete> setTempleteRules(List<Templete> templetes) {
		List<Rule> rules = null;
		for (Templete templete : templetes) {
			rules = getRules(templete);
			templete.setRules(rules);
		}
		return templetes;
	}
	
	public List<Templete> getIgnoreTempletes() {
		List<Templete> templetes = getTemplates("SELECT ID, NAME, CATEGORY, TITLE, RCA, REPRODUCESTEPS, PRIORITY, TIMESTAMP, IGNORE FROM TEMPLETE WHERE IGNORE = TRUE");
		return setTempleteRules(templetes);
	}

	private List<Templete> getTemplates(String sql) {
		Connection connection = getConnection();
		List<Templete> templetes = new ArrayList<Templete>();
		ResultSet rs = null;
		try {
			rs = query(connection, sql);
			while (rs.next()) {
				Templete templete = new Templete();
				templete.setId(rs.getString("ID"));
				templete.setName(rs.getString("NAME"));
				templete.setCategory(rs.getString("CATEGORY"));
				templete.setTitle(rs.getString("TITLE"));
				templete.setRCA(rs.getString("RCA"));
				templete.setReProduceSteps(rs.getString("REPRODUCESTEPS"));
				templete.setPriority(rs.getInt("PRIORITY"));
				templete.setTimestamp(getTimeStamp(rs.getDate("TIMESTAMP")));
				templete.setIgnore(rs.getBoolean("IGNORE"));
				
				templetes.add(templete);
			}
		} catch (SQLException e) {
			LOG.error("" + e);
		} finally {
			free(rs, null, connection);
		}
		return templetes;
	}
	
	public List<LoggerFile> getLoggerFiles() {
		Connection connection = getConnection();
		List<LoggerFile> loggerFiles = new ArrayList<LoggerFile>();
		ResultSet rs = null;
		
		try {
			rs = query(connection, "SELECT FILENAME FROM LOGGERFILE");
			while (rs.next()) {
				loggerFiles.add(new LoggerFile(rs.getString("FILENAME")));
			}
		} catch (SQLException e) {
			LOG.error("" + e);
		}finally {
			free(rs, null, connection);
		}
		return loggerFiles;
	}
	
	public boolean save(Templete templete) {
		Connection connection = getConnection();
		String sql = "INSERT INTO TEMPLETE(ID, NAME, CATEGORY, TITLE, RCA, REPRODUCESTEPS, PRIORITY, TIMESTAMP, IGNORE) VALUES(?,?,?,?,?,?,?,?,?)";
		try {
			update(connection, sql, 
					IDGenerator.generateId(), templete.getName(), templete.getCategory(), templete.getTitle(),
					templete.getRCA(), templete.getReProduceSteps(), templete.getPriority(), 
					getTimeStamp(templete.getTimestamp()), templete.isIgnore());
			
		} catch (SQLException e) {
			LOG.error(" " + e);
			return false;
		} finally {
			close(connection);
		}
		return true;
	}
	
	public boolean addTempleteRule(Templete templete, StringRule rule) {
		Connection connection = getConnection();
		TransactionMgr.beginTansction(connection);
		String sql = "INSERT INTO TEMPLETE(ID, NAME, CATEGORY, TITLE, RCA, REPRODUCESTEPS, PRIORITY, TIMESTAMP, IGNORE) VALUES(?,?,?,?,?,?,?,?,?)";
		String ruleSql = "INSERT INTO STRINGRULE(ID, TEMPLETEID, FEATURE) VALUES(?,?,?)";
		String id = IDGenerator.generateId();
		try {
			update(connection, sql, 
					id, templete.getName(), templete.getCategory(), templete.getTitle(),
					templete.getRCA(), templete.getReProduceSteps(), templete.getPriority(), 
					getTimeStamp(templete.getTimestamp()), templete.isIgnore());
			
			update(connection, ruleSql, 
					IDGenerator.generateId(), id, rule.getFeature());
		} catch (SQLException e) {
			LOG.error("" + e);
			TransactionMgr.rollbackAndClose(connection);
			return false;
		}
		TransactionMgr.commitAndClose(connection);
		return true;
	}

	private void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new DataAccessException(e);
			}
		}
	}

	public List<ExceptionFragment> getRetryAnalysizedFragments(Job job) {
		Connection connection = getConnection();
		String sql = "SELECT  TOP "+Constants.DB_RESULTSET_SIZE+" E.ID, E.LOGFILEID, E.CONTEXT, E.DETAILMESSAGES, E.DATE FROM EXCEPTIONFRAGMENT E LEFT JOIN JOBSTATUS JS ON E.ID = JS.FRAGMENTID WHERE E.ISMATCHED = TRUE AND E.ANALYSISCOMPLETED = FALSE AND JS.JOBID IS NULL";
		ResultSet rs = null;
		try {
			rs = query(connection, sql);
			while (rs.next()) {
				update(connection, "INSERT INTO JOBSTATUS(FRAGMENTID, JOBID, TIMES ) VALUES(?,?,?)", rs.getString("ID"), job.getId(), job.getCurrentTimes());
			}
		} catch (SQLException e) {
			LOG.error("" + e);
		}finally {
			free(rs, null, connection);
		}
		return getFragments("SELECT TOP "+Constants.DB_RESULTSET_SIZE+" E.ID, E.LOGFILEID, E.CONTEXT, E.DETAILMESSAGES, E.DATE FROM EXCEPTIONFRAGMENT E INNER JOIN JOBSTATUS JS ON E.ID = JS.FRAGMENTID INNER JOIN JOB J ON JS.JOBID = J.ID WHERE JS.TIMES <= J.CURRENTTIMES AND JS.STATUS = 0");
	}

	public Job nextRetry(Job job) {
		Connection connection = getConnection();
		String sql = "UPDATE JOB SET CURRENTTIMES = CURRENTTIMES+1 WHERE ID =?";
		try {
			update(connection, sql, job.getId());
		} catch (SQLException e) {
			LOG.error(" " + e);
		} finally {
			close(connection);
		}
		job.setCurrentTimes(job.getCurrentTimes() + 1);
		return job;
	}

	public Job getRetryJob(String jobName) {
		Connection connection = getConnection();
		ResultSet rs = null;
		Job job = null;
		try {
			rs = query(connection, "SELECT ID, JOBNAME, CURRENTTIMES FROM JOB WHERE JOBNAME = ?", jobName);
			while (rs.next()) {
				job = new Job();
				job.setId(rs.getString("ID"));
				job.setName(rs.getString("JOBNAME"));
				job.setCurrentTimes(rs.getInt("CURRENTTIMES"));
			}
		} catch (SQLException e) {
			LOG.error("" + e);
		}finally {
			free(rs, null, connection);
		}
		return job;
	}
	
	public boolean save(String jobName) {
		Connection connection = getConnection();
		ResultSet rs = null;
		try {
			update(connection, "INSERT INTO JOB(ID, JOBNAME, CURRENTTIMES) VALUES(?,?,?)", IDGenerator.generateId(), jobName, 0);
		} catch (SQLException e) {
			LOG.error("" + e);
			return false;
		}finally {
			free(rs, null, connection);
		}
		return true;
	}

	public Job initRestryJob(String jobName) {
		if(save(jobName)){
			return getRetryJob(jobName);
		}
		return null;
	}

	public boolean completeAnalysis(Templete templete,
			ExceptionFragment fragment, Job job) {
		
		if(job == null) return completeAnalysis(templete, fragment) ;
		
		if(job.getName().equalsIgnoreCase(RetryAnalyster.class.getSimpleName())){
			fragment.setAnalysisCompleted(true);
			fragment.setMatched(true);
			return retryAnalysize(templete, fragment, job);
		}
		
		return false;
	}
	
	private boolean retryAnalysize(Templete templete, ExceptionFragment fragment, Job job) {
		Connection connection = getConnection();
		TransactionMgr.beginTansction(connection);

		String relationSql = "INSERT INTO TEMPLATEEXCEPTIONFRAGMENT(TEMPID, FRAGMENTID) VALUES(?,?)";
		String fragmentSql = "UPDATE EXCEPTIONFRAGMENT SET ISMATCHED = ?, ANALYSISCOMPLETED = ? WHERE ID = ?";
		String jobStatusSql = "UPDATE JOBSTATUS SET STATUS = 1 WHERE JOBID = ? AND FRAGMENTID = ?";

		try {
			update(connection, relationSql, templete.getId(), fragment.getId());
			update(connection, fragmentSql, fragment.isMatched(),
					fragment.isAnalysisCompleted(), fragment.getId());
			
			update(connection, jobStatusSql, job.getId(), fragment.getId());
		} catch (SQLException e) {
			LOG.error("" + e);
			TransactionMgr.rollbackAndClose(connection);
			return false;
		}
		TransactionMgr.commitAndClose(connection);
		return true;
	}

	public boolean skipAnalysis(ExceptionFragment fragment, Job job) {
		
		if(job == null) return skipAnalysis(fragment);
		
		if(job.getName().equalsIgnoreCase(RetryAnalyster.class.getSimpleName())){
			return retrySkipAnalysis(fragment, job);
		}
		
		return false;
		
	}
	
	public boolean retrySkipAnalysis(ExceptionFragment fragment, Job job) {
		Connection connection = getConnection();
		
		TransactionMgr.beginTansction(connection);
		String jobSql = "UPDATE JOBSTATUS SET TIMES = TIMES  + 1 WHERE JOBID = ? AND FRAGMENTID = ?";

		try {
			update(connection, jobSql, job.getId(), fragment.getId());
		} catch (SQLException e) {
			LOG.error("" + e);
			TransactionMgr.rollbackAndClose(connection);
			return false;
		}
		TransactionMgr.commitAndClose(connection);
		return true;
	}
	
}
