package com.angelo.logging.templete;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angelo.logging.Constants;
import com.angelo.logging.sample.ExceptionSample;

public class Templetes {
	private static final Logger LOG = LoggerFactory.getLogger(Templetes.class);
	private List<Templete> templetes;
	private String templetesInfo;
	
	private static Templetes t = new Templetes();
	
	private Templetes() {
		templetes = new ArrayList<Templete>();
		templetes.add(mailConnectionRefused());
		templetes.add(mailParsingMessageError());
		templetes.add(failureSendingEmail());
		templetes.add(deadLockTemplete());
		templetes.add(duplicateToDBTemplete());
		templetes.add(databasePerformanceIssues());
		templetes.add(dBConnectionClosed());
		templetes.add(patientDetailExceptionWorkflow());
		templetes.add(dBConnectionReset());
		templetes.add(requestWithLogout());
		
		templetes.add(illegalRequest());
		templetes.add(nullPatientDetailState());
	}
	
	
	private Templete nullPatientDetailState() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("SamplesLib/NullPatientDetailState.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Illegal Request(NullPatientDetailState)");
		temp.setRCA("Maybe open multiple browser windows or tabs for operation");
		temp.setReProduceSteps("");
		return temp;
	}
	
	private Templete illegalRequest() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("SamplesLib/IllegalRequest.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Illegal Request(Not a user.)");
		temp.setRCA("Illegal request.");
		temp.setReProduceSteps("Unknown(not a user request)");
		return temp;
	}
	
	private Templete requestWithLogout() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("SamplesLib/RequestWithLogoutExceptionWorkflow.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Request with logout");
		temp.setRCA("send a request, at the same tiem, logout.");
		temp.setReProduceSteps("");
		return temp;
	}
	
	private Templete mailParsingMessageError() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("SamplesLib/MailParsingMessageError.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Mail Parsing Message Error");
		temp.setRCA("Mail Parsing Message Error");
		temp.setReProduceSteps("");
		return temp;
	}
	
	private Templete failureSendingEmail() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("SamplesLib/FailureSendingEmail.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Failure Sending Email");
		temp.setRCA("Invalid Addresses, 550 A valid address is required");
		temp.setReProduceSteps("");
		return temp;
	}

	private Templete dBConnectionReset() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("SamplesLib/DBConnectionReset.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Connection reset by peer");
		temp.setRCA("Connection reset by peer");
		temp.setReProduceSteps("");
		return temp;
	}
	
	private Templete patientDetailExceptionWorkflow() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("SamplesLib/PatientDetailExceptionWorkflow.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Exception Workflow");
		temp.setRCA("Exception Work flow");
		temp.setReProduceSteps("" +
				"1.	Click into patient profile page." + Constants.LINE_SEPRATOR+
				"2.	At the same time, logout." + Constants.LINE_SEPRATOR+
				"");
		return temp;
	}
	
	private Templete dBConnectionClosed() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("SamplesLib/DBConnectionClosed.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("DB Connection Closed");
		temp.setRCA("The database connection is closed");
		temp.setReProduceSteps("");
		return temp;
	}
	
	private Templete databasePerformanceIssues() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("SamplesLib/DatabasePerformanceIssues.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Database Performance Issues");
		temp.setRCA("The query has timed out");
		temp.setReProduceSteps("");
		return temp;
	}
	
	private Templete mailConnectionRefused() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("SamplesLib/MailConnectionRefused.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Mail Server Refuse to connect");
		temp.setRCA("Mail Server Refuse to connect.");
		temp.setReProduceSteps("");
		return temp;
	}
	
	private Templete deadLockTemplete() {
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
	
	private Templete duplicateToDBTemplete() {
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

	public static Templetes getInstance(){
		return t;
	}
	
	public List<Templete> getTempletes() {
		LOG.info(getTempletesInfo());
		return templetes;
	}
	
	public String getTempletesInfo(){
		if(templetesInfo != null){
			return templetesInfo;
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append("Avaibale templetes:");
		for (int i = 0; i < templetes.size(); i++) {
			builder.append(Constants.LINE_SEPRATOR);
			builder.append(i + "   "+templetes.get(i).getTitle());
		}
		
		templetesInfo = builder.toString();
		return templetesInfo;
	}
}
