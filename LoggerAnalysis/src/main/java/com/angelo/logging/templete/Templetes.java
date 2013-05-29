package com.angelo.logging.templete;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.angelo.logging.sample.ExceptionSample;

public class Templetes {

	private List<Templete> templetes;
	
	private static final String ANY = ".*";
	private static Templetes t = new Templetes();
	
	private Templetes() {
		templetes = new ArrayList<Templete>();
//		templetes.add(deadLockTemplete());
		templetes.add(deadLockTemplete1());
//		templetes.add(duplicateToDBTemplete());
		templetes.add(duplicateToDBTemplete1());
//		templetes.add(refuseConnectingMailServerTemplete());
	}

	private Templete refuseConnectingMailServerTemplete() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("SamplesLib/MailRefuseConnection.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Mail Server Refuse to connect");
		temp.setRCA("Mail Server Refuse to connect.");
		temp.setReProduceSteps("");
		return temp;
	}
	
	private Templete deadLockTemplete1() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("SamplesLib/DeadLock.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Dead Lock");
		temp.setRCA("Failure requests for database produce a dead lock.");
		temp.setReProduceSteps("");
		return temp;
	}
	
	private Templete duplicateToDBTemplete1() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("SamplesLib/InsertDuplicateRecords.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Duplicate records to DB");
		temp.setRCA("Duplicate records to DB");
		temp.setReProduceSteps("");
		return temp;
	}

	private Templete duplicateToDBTemplete() {
		String title = "Duplicate records to DB";
		String rCA = "Duplicate records to DB";
		String reProduceSteps = "";

		String regex = "" +ANY+
				"javax.mail.MessagingException: Connect failed;" +ANY+
				"nested exception is:" +ANY+
				"java.net.ConnectException: Connection refused: connect" +ANY+
				"at com.sun.mail.pop3.POP3Store.protocolConnect" +ANY+
				"at javax.mail.Service.connect" +ANY+
				"at javax.mail.Service.connect" +ANY+
				"at javax.mail.Service.connect" +ANY+
				"at com.singulex.cvmedhome.tasks.notification.NotificationReceiver.getAllMessage" +ANY+
				"at com.singulex.cvmedhome.tasks.notification.NotificationReceiver.checkBouncedMail" +ANY+
				"at sun.reflect.GeneratedMethodAccessor42.invoke" +ANY+
				"at sun.reflect.DelegatingMethodAccessorImpl.invoke" +ANY+
				"at java.lang.reflect.Method.invoke" +ANY+
				"at org.springframework.util.MethodInvoker.invoke" +ANY+
				"at org.springframework.scheduling.support.MethodInvokingRunnable.run" +ANY+
				"at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run" +ANY+
				"at java.util.concurrent.Executors$RunnableAdapter.call" +ANY+
				"at java.util.concurrent.FutureTask$Sync.innerRunAndReset" +ANY+
				"at java.util.concurrent.FutureTask.runAndReset" +ANY+
				"at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$101" +ANY+
				"at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.runPeriodic" +ANY+
				"at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run" +ANY+
				"at java.util.concurrent.ThreadPoolExecutor$Worker.runTask" +ANY+
				"at java.util.concurrent.ThreadPoolExecutor$Worker.run" +ANY+
				"at java.lang.Thread.run" +ANY+
				ANY;
		return new Templete(title, rCA, reProduceSteps, regex.toString());
	}

	private Templete deadLockTemplete() {
		String title = "Dead Lock";
		String rCA = "Failure requests for database produce a dead lock.";
		String reProduceSteps = "";

		// () $ 
		String regex = "" +ANY+
		"Caused by: com.microsoft.sqlserver.jdbc.SQLServerException: Transaction "+ANY+" was deadlocked on lock resources with another process and has been chosen as the deadlock victim."+ANY+ 
		"at com.microsoft.sqlserver.jdbc.SQLServerException.makeFromDatabaseError" +ANY+ 
		"at com.microsoft.sqlserver.jdbc.SQLServerStatement.getNextResult" +ANY+ 
		"at com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement.doExecutePreparedStatement" +ANY+ 
		"at com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement\\$PrepStmtExecCmd.doExecute" +ANY+ 
		"at com.microsoft.sqlserver.jdbc.TDSCommand.execute" +ANY+ 
		"at com.microsoft.sqlserver.jdbc.SQLServerConnection.executeCommand" +ANY+ 
		"at com.microsoft.sqlserver.jdbc.SQLServerStatement.executeCommand" +ANY+ 
		"at com.microsoft.sqlserver.jdbc.SQLServerStatement.executeStatement" +ANY+ 
		"at com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement.execute" +ANY+ 
		"at org.apache.commons.dbcp.DelegatingPreparedStatement.execute" +ANY+ 
		"at org.apache.commons.dbcp.DelegatingPreparedStatement.execute" +ANY+ 
		"at com.ibatis.sqlmap.engine.execution.SqlExecutor.executeQueryProcedure" +ANY+ 
		"at com.ibatis.sqlmap.engine.mapping.statement.ProcedureStatement.sqlExecuteQuery" +ANY+ 
		"at com.ibatis.sqlmap.engine.mapping.statement.MappedStatement.executeQueryWithCallback"+ANY+ 
		ANY;
		return new Templete(title, rCA, reProduceSteps, regex.toString());
	}

	public static Templetes getInstance(){
		return t;
	}
	
	public List<Templete> getTempletes() {
		return templetes;
	}
}
