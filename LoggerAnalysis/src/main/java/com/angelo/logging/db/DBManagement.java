package com.angelo.logging.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.angelo.logging.io.TextFileReader;
import com.angelo.logging.logger.ExceptionFragment;
import com.angelo.logging.logger.LoggerFile;
import com.angelo.logging.templete.Rule;
import com.angelo.logging.templete.StringRule;
import com.angelo.logging.templete.Templete;
import com.angelo.logging.util.Constants;

public class DBManagement {
	private static final String INSERT_EXCEPTIONFRAGMENT_SQL = "insert into ExceptionFragment" +
			"(title, rca, reproduceSteps, rootException, context, detailMessages, date, analysisCompleted)" +
			"values(?,?,?,?,?,?,?,?)";
	
	private static final String SELECT_EXCEPTIONFRAGMENT_SQL = "SELECT TOP 100 id, title, rca, reproduceSteps, rootException, context, detailMessages, date, analysisCompleted FROM EXCEPTIONFRAGMENT AS F WHERE F.ISMATCHED=false";
	
	private static final String UPDATE_EXCEPTIONFRAGMENT_SQL = "update exceptionfragment set title = ?, rca = ?, reproduceSteps = ?, rootException=?, analysisCompleted=?, ISMATCHED = ?, ignore = ?  where id = ?";
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("org.h2.Driver");
			// jdbc:h2:tcp://localhost/~/h2/data
			// jdbc:h2:~/h2/data
			conn = DriverManager.getConnection(Constants.getInstance().getH2url(), "sa", "");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}

	public void createTable() {
		Connection connection = getConnection();
		Statement st = null;
		try {
			st = connection.createStatement();
			st.execute(new TextFileReader(
					"resources/scripts/ExceptionFragmentTable.sql").read());
			st.execute(new TextFileReader("resources/scripts/TemplateTable.sql")
					.read());

			st.execute(new TextFileReader(
					"resources/scripts/Template_ExceptionFragmentTable.sql")
					.read());

			st.execute(new TextFileReader(
					"resources/scripts/LoggerFileTable.sql").read());
			st.execute(new TextFileReader(
					"resources/scripts/StringRuleTable.sql").read());
			st.execute(new TextFileReader(
					"resources/scripts/TempleteMatchStatusTable.sql").read());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	// public void insert(String insertSql, Object ...args){
	//
	// }

	public void insert(ExceptionFragment fragment) {
		Connection connection = getConnection();
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(INSERT_EXCEPTIONFRAGMENT_SQL);

//			pst.setInt(1, fragment.getId());
			pst.setString(1, fragment.getTitle());
			pst.setString(2, fragment.getRCA());
			pst.setString(3, fragment.getReproduceSteps());
			pst.setString(4, fragment.getRootException());
			pst.setString(5, fragment.getContext());
			pst.setString(6, fragment.getDetailMessages());
			
			pst.setTimestamp(7, fragment.getDate() == null? 
					null : new java.sql.Timestamp(fragment.getDate().getTime()));
		
			pst.setBoolean(8, fragment.isAnalysisCompleted());
			
			pst.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	
	public List<ExceptionFragment> select() {
		return select(SELECT_EXCEPTIONFRAGMENT_SQL);
	}
	
	public List<ExceptionFragment> select(String sql) {
		Connection connection = getConnection();
		List<ExceptionFragment> fragments = new ArrayList<ExceptionFragment>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = connection.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				ExceptionFragment frag = new ExceptionFragment();
				frag.setId(rs.getInt("id"));
				frag.setTitle(rs.getString("title"));
				frag.setRCA(rs.getString("rca"));
				frag.setReproduceSteps(rs.getString("reproduceSteps"));
				frag.setRootException(rs.getString("rootException"));
				frag.setContext(rs.getString("context"));
				frag.setDetailMessages(rs.getString("detailMessages"));
				frag.setAnalysisCompleted(rs.getBoolean("analysisCompleted"));
				
				fragments.add(frag);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return fragments;
	}

	public void update(ExceptionFragment fragment) {
		Connection connection = getConnection();
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(UPDATE_EXCEPTIONFRAGMENT_SQL);

			pst.setString(1, fragment.getTitle());
			pst.setString(2, fragment.getRCA());
			pst.setString(3, fragment.getReproduceSteps());
			pst.setString(4, fragment.getRootException());
			pst.setBoolean(5, fragment.isAnalysisCompleted());
			pst.setBoolean(6, fragment.isMatched());
			pst.setBoolean(7, fragment.isIgnore());
			pst.setInt(8, fragment.getId());
			
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void analysize(Templete templete, ExceptionFragment fragment) {
		Connection connection = getConnection();
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement("insert into Template_ExceptionFragment(TEMP_ID, FRAGMENT_ID) values(?,?)");

			pst.setInt(1, templete.getId());
			pst.setInt(2, fragment.getId());
			
			pst.execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void insert(Templete templete) {
		Connection connection = getConnection();
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement("insert into templete (category ,title, rca, reproducesteps, templete, priority) values(?,?,?,?,?,?)");

			pst.setString(1, templete.getCategory());
			pst.setString(2, templete.getTitle());
			pst.setString(3, templete.getRCA());
			pst.setString(4, templete.getReProduceSteps());
			pst.setString(5, templete.getTemplete());
			pst.setInt(6, templete.getPriority());
			
			pst.execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void insert(Templete templete, StringRule rule) {
		Connection connection = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = connection.prepareStatement("SELECT MAX(ID)+1 FROM TEMPLETE");
			rs = pst.executeQuery();
			rs.next();
			int id = rs.getInt(1);
			
			pst = connection.prepareStatement("insert into templete (category ,title, rca, reproducesteps, templete, priority, id, ignore) values(?,?,?,?,?,?,?,?)");
			
			pst.setString(1, templete.getCategory());
			pst.setString(2, templete.getTitle());
			pst.setString(3, templete.getRCA());
			pst.setString(4, templete.getReProduceSteps());
			pst.setString(5, templete.getTemplete());
			pst.setInt(6, templete.getPriority());
			pst.setInt(7, id);
			pst.setBoolean(8, templete.isIgnore());
			
			pst.execute();
			
			pst = connection.prepareStatement("INSERT INTO STRINGRULE (TEMPLETEID , FEATURE ) VALUES(?,?)");
			
			pst.setInt(1, id);
			pst.setString(2, rule.getFeature());
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public List<Templete> getTemplates() {
		List<Templete> templetes = getTemplates("select id, category ,title, rca, reproducesteps, templete from templete ");
		List<Rule> rules = null;
		for (Templete templete : templetes) {
			rules = getRules(templete);
			templete.setRules(rules);
		}
		return templetes;
	}

	private List<Rule> getRules(Templete templete) {
		Connection connection = getConnection();
		List<Rule> rules = new ArrayList<Rule>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = connection.prepareStatement("SELECT ID, TEMPLETEID , FEATURE FROM STRINGRULE S  WHERE S.TEMPLETEID = " + templete.getId());
			rs = pst.executeQuery();
			while(rs.next()){
				StringRule rule = new StringRule();
				rule.setId(rs.getInt(1));
				rule.setTempleteId(rs.getInt(2));
				rule.setFeature(rs.getString(3));
				rules.add(rule);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return rules;
	}

	public List<Templete> getTemplates(String sql) {
		Connection connection = getConnection();
		List<Templete> templetes = new ArrayList<Templete>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = connection.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				Templete templete = new Templete(rs.getString("templete"));
				templete.setId(rs.getInt("id"));
				templete.setCategory(rs.getString("category"));
				templete.setTitle(rs.getString("title"));
				templete.setRCA(rs.getString("rca"));
				templete.setReProduceSteps(rs.getString("reproducesteps"));
				
				templetes.add(templete);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return templetes;
	}

	public void insert(LoggerFile loggerFile) {
		Connection connection = getConnection();
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement("insert into loggerfile (filename , whichday , importday ) values(?,?,?)");

			pst.setString(1, loggerFile.getFileName());
			pst.setTimestamp(2, loggerFile.getWhichDay() == null? 
					null : new java.sql.Timestamp(loggerFile.getWhichDay().getTime()));
			pst.setTimestamp(3, loggerFile.getImportDay() == null? 
					null : new java.sql.Timestamp(loggerFile.getImportDay().getTime()));
			
			pst.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public List<LoggerFile> getLoggerFiles() {
		Connection connection = getConnection();
		List<LoggerFile> loggerFiles = new ArrayList<LoggerFile>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = connection.prepareStatement("SELECT FILENAME FROM LOGGERFILE");
			rs = pst.executeQuery();
			while(rs.next()){
				loggerFiles.add(new LoggerFile(rs.getString("FILENAME")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return loggerFiles;
	}

	public List<ExceptionFragment> selectNotMatchAndIgnoreEx() {
		return select("SELECT TOP 100 id, title, rca, reproduceSteps, rootException, context, detailMessages, date, analysisCompleted FROM EXCEPTIONFRAGMENT AS F WHERE F.ISMATCHED = TRUE AND F.ANALYSISCOMPLETED = FALSE AND F.IGNORE = FALSE");
	}

	public List<Templete> getNewTempletes(ExceptionFragment fragment) {
		List<Templete> templetes = getTemplates("SELECT T.ID, CATEGORY, TITLE, RCA, REPRODUCESTEPS, TEMPLETE FROM TEMPLETE T INNER JOIN TEMPLETEMATCHSTATUS S ON T.ID = S.FRAGMENTID WHERE S.FRAGMENTID = " + fragment.getId());
		return setTempleteRules(templetes);
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
		List<Templete> templetes = getTemplates("SELECT T.ID, CATEGORY, TITLE, RCA, REPRODUCESTEPS, TEMPLETE FROM TEMPLETE T WHERE T.IGNORE = TRUE");
		return setTempleteRules(templetes);
	}
	
	

	// public void select(String sql, ){
	//
	// }
	
	
}
