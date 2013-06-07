

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angelo.logging.db.DataCentre;
import com.angelo.logging.sample.ExceptionSample;
import com.angelo.logging.templete.Templete;
import com.angelo.logging.util.Constants;

public class Templetes {
	private static final Logger LOG = LoggerFactory.getLogger(Templetes.class);
	private List<Templete> templetes;
	private String templetesInfo;
	
	private static Templetes t = new Templetes();
	private DataCentre db = new DataCentre();
	
	private Templetes() {
		if(templetes == null){
			templetes = db.getTemplates();
		}
	}
	
	private Templete jDBCAuthenticationIssues() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("resources/samples/JDBCAuthenticationIssues.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("JDBC Authentication Issues");
		temp.setRCA("This JDBC driver is not configured for integrated authentication.");
		temp.setReProduceSteps("");
		temp.setPriority(17);
		return temp;
	}
	
	private Templete mailAuthenticationFailedException() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("resources/samples/MailAuthenticationFailedException.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Mail Authentication Failed Exception");
		temp.setRCA("Mail Authentication Failed Exception");
		temp.setReProduceSteps("");
		temp.setPriority(16);
		return temp;
	}
	
	private Templete couldNotOpenJDBC() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("resources/samples/CouldNotOpenJDBC.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Could Not Open JDBC");
		temp.setRCA("Could not open JDBC Connection for transaction");
		temp.setReProduceSteps("");
		temp.setPriority(3);
		return temp;
	}
	
	private Templete returnToQueuePrivilege() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("resources/samples/ReturnToQueuePrivilege.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("ReturnToQueue Privilege");
		temp.setRCA("ReturnToQueue Privilege");
		temp.setReProduceSteps("");
		temp.setPriority(15);
		return temp;
	}
	
	private Templete sessionInvalidated() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("resources/samples/SessionInvalidated.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Session Invalidated");
		temp.setRCA("Session already invalidated");
		temp.setReProduceSteps("");
		temp.setPriority(14);
		return temp;
	}
	
	private Templete nullPatientDetailState() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("resources/samples/NullPatientDetailState.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Illegal Request(NullPatientDetailState)");
		temp.setRCA("Maybe open multiple browser windows or tabs for operation");
		temp.setReProduceSteps("");
		temp.setPriority(13);
		return temp;
	}
	
	private Templete illegalRequest() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("resources/samples/IllegalRequest.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Illegal Request(Not a user.)");
		temp.setRCA("Illegal request.");
		temp.setReProduceSteps("Unknown(not a user request)");
		temp.setPriority(12);
		return temp;
	}
	
	private Templete requestWithLogout() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("resources/samples/RequestWithLogoutExceptionWorkflow.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Request with logout");
		temp.setRCA("send a request, at the same tiem, logout.");
		temp.setReProduceSteps("");
		temp.setPriority(11);
		return temp;
	}
	
	private Templete mailParsingMessageError() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("resources/samples/MailParsingMessageError.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Mail Parsing Message Error");
		temp.setRCA("Mail Parsing Message Error");
		temp.setReProduceSteps("");
		temp.setPriority(2);
		return temp;
	}
	
	private Templete failureSendingEmail() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("resources/samples/FailureSendingEmail.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Failure Sending Email");
		temp.setRCA("Invalid Addresses, 550 A valid address is required");
		temp.setReProduceSteps("");
		temp.setPriority(4);
		return temp;
	}

	private Templete dBConnectionReset() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("resources/samples/DBConnectionReset.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Connection reset by peer");
		temp.setRCA("Connection reset by peer");
		temp.setReProduceSteps("");
		temp.setPriority(10);
		return temp;
	}
	
	private Templete patientDetailExceptionWorkflow() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("resources/samples/PatientDetailExceptionWorkflow.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Exception Workflow");
		temp.setRCA("Exception Work flow");
		temp.setReProduceSteps("" +
				"1.	Click into patient profile page." + Constants.LINE_SEPRATOR+
				"2.	At the same time, logout." + Constants.LINE_SEPRATOR+
				"");
		temp.setPriority(9);
		return temp;
	}
	
	private Templete dBConnectionClosed() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("resources/samples/DBConnectionClosed.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("DB Connection Closed");
		temp.setRCA("The database connection is closed");
		temp.setReProduceSteps("");
		temp.setPriority(8);
		return temp;
	}
	
	private Templete databasePerformanceIssues() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("resources/samples/DatabasePerformanceIssues.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Database Performance Issues");
		temp.setRCA("The query has timed out");
		temp.setReProduceSteps("");
		temp.setPriority(7);
		return temp;
	}
	
	private Templete mailConnectionRefused() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("resources/samples/MailConnectionRefused.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Mail Server Refuse to connect");
		temp.setRCA("Mail Server Refuse to connect.");
		temp.setReProduceSteps("");
		temp.setPriority(1);
		return temp;
	}
	
	private Templete deadLockTemplete() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("resources/samples/DeadLock.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Dead Lock");
		temp.setRCA("Failure requests for database produce a dead lock.");
		temp.setReProduceSteps("");
		temp.setPriority(5);
		return temp;
	}
	
	private Templete duplicateToDBTemplete() {
		ExceptionSample sample = new ExceptionSample(new File(Thread
				.currentThread().getContextClassLoader()
				.getResource("resources/samples/InsertDuplicateRecords.sample")
				.getPath()));
		Templete temp = sample.getTemplete();
		temp.setTitle("Duplicate records to DB");
		temp.setRCA("Duplicate records to DB");
		temp.setReProduceSteps("");
		temp.setPriority(6);
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
	
	public void importDB(){
		List<Templete> templetes = new ArrayList<Templete>();
		templetes.add(mailConnectionRefused());
		templetes.add(mailParsingMessageError());
		templetes.add(couldNotOpenJDBC());
		templetes.add(failureSendingEmail());
		templetes.add(deadLockTemplete());
		templetes.add(duplicateToDBTemplete());
		templetes.add(databasePerformanceIssues());
		templetes.add(dBConnectionClosed());
		templetes.add(patientDetailExceptionWorkflow());
		templetes.add(dBConnectionReset());
////		
		templetes.add(requestWithLogout());
		templetes.add(illegalRequest());
		templetes.add(nullPatientDetailState());
		templetes.add(sessionInvalidated());
		templetes.add(returnToQueuePrivilege());
		templetes.add(mailAuthenticationFailedException());
		templetes.add(jDBCAuthenticationIssues());
		
		Collections.sort(templetes, new Comparator<Templete>() {

			public int compare(Templete o1, Templete o2) {
				return o1.getPriority() - o2.getPriority();
			}
			
		});
		
		for (Templete templete : templetes) {
			db.save(templete);
		}
	
	}
}
