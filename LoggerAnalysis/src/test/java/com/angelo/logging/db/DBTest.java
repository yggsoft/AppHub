package com.angelo.logging.db;

import org.junit.Test;

import com.angelo.logging.templete.StringRule;
import com.angelo.logging.templete.Templete;

public class DBTest {

//	 @Test
	public void runTest() {
		DBManagement dBManagement = new DBManagement();
		dBManagement.getConnection();
	}

//	 @Test
	public void createTable() {
		DBManagement dBManagement = new DBManagement();
		dBManagement.createTable();
	}

	@Test
	public void insertTest() {
//		bulidTemplete("Mail Server Refuse to connect", "Mail Server Refuse to connect", "", "", false, "java.net.ConnectException: Connection refused: connect");
//		bulidTemplete("Mail Parsing Message Error", "Mail Parsing Message Error", "", "", false, "javax.mail.MessagingException: Exception getting text content.");
//		bulidTemplete("Could Not Open JDBC", "Could not open JDBC Connection for transaction", "", "", false, "Could not open JDBC Connection for transaction");
//		bulidTemplete("Failure Sending Email", "Invalid Addresses, 550 A valid address is required", "", "", false, "com.sun.mail.smtp.SMTPAddressFailedException: 550 A valid address is required.");
//		bulidTemplete("Dead Lock", "Failure requests for database produce a dead lock.", "", "", false, "Transaction;deadlocked");
//		bulidTemplete("Duplicate records to DB", "Duplicate records to DB", "", "", false, "Cannot insert duplicate key row in object");
//		bulidTemplete("Database Performance Issues", "The query has timed out", "", "", false, "SQLServerException: The query has timed out");
//		bulidTemplete("DB Connection Closed", "The database connection is closed", "", "", false, "com.microsoft.sqlserver.jdbc.SQLServerException: The connection is closed");
//		bulidTemplete("Exception Workflow", "Exception Work flow", "", "", false, "Caused by: java.lang.NullPointerException;com.singulex.cvmedhome.presentation.pages.PatientDetail.getPatientId");
//		bulidTemplete("Connection reset by peer", "Connection reset by peer", "", "", false, "com.microsoft.sqlserver.jdbc.SQLServerException: Connection reset by peer: socket write error");
//		
//		bulidTemplete("Request with logout", "send a request, at the same tiem, logout.", "", "", false, "SQLServerException: Assert_ID;callingUserID: value is null");
//		bulidTemplete("Session Invalidated", "Session already invalidated", "", "", false, "Session already invalidated");
//		bulidTemplete("ReturnToQueue Privilege", "ReturnToQueue Privilege", "", "", false, "Check the PatientProfile.returnToQueueParams;SQLServerException: Security_AssertPrimaryCHE");
//		bulidTemplete("Mail Authentication Failed Exception", "Mail Authentication Failed Exception", "", "", false, "javax.mail.AuthenticationFailedException: EOF on socket;com.sun.mail.pop3.POP3Store.protocolConnect");
//		bulidTemplete("JDBC Authentication Issues", "This JDBC driver is not configured for integrated authentication.", "", "", false, "Could not open JDBC Connection for transaction;SQLServerException: This driver is not configured for integrated authentication");
//		bulidTemplete("Exception Workflow", "MedicalHistoryState.getCategoryComplete", "", "", false, "NullPointerException;MedicalHistoryState.getCategoryComplete");
//		bulidTemplete("Exception Workflow", "Metrics.getIsEditable", "", "", false, "NullPointerException;Metrics.getIsEditable");
//		bulidTemplete("User is not an Administrator", "User is not an Administrator", "", "", false, "Security_AssertRoleAdmin() - @userID: User is not an Administrator.");
//		bulidTemplete("t:formdata query parameter no value", "t:formdata query parameter no value", "", "", false, "Forms require that the request method be POST and that the t:formdata query parameter have values");
//		bulidTemplete("/index.loginform request", "/index.loginform request", "", "", false, "java.io.EOFException;java.util.zip.GZIPInputStream.readUByte(GZIPInputStream.java:207);/index.loginform");
//		bulidTemplete("User account may be deleted or some issues", "User account may be deleted", "", "", false, "SQLServerException: Assert_ID() - @callingUserID;is not found in UserAccount");
//		bulidTemplete("SQLServerException insufficient memory", "SQLServerException insufficient memory", "", "", false, "SQLServerException;insufficient memory available in the buffer pool");
//		bulidTemplete("SQLServerException", "SQLServerException;TCP Provider: Timeout error", "", "", false, "SQLServerException;TCP Provider: Timeout error");
//		bulidTemplete("SQLServerException", "Could not roll back JDBC transaction; nested exception is com.microsoft.sqlserver.jdbc.SQLServerException: SHUTDOWN is in progress.", "", "", false, "SQLServerException;SHUTDOWN is in progress");
//		bulidTemplete("javax.mail.AuthenticationFailedException: Invalid user name or password. Please use full email address as user name.", "javax.mail.AuthenticationFailedException: Invalid user name or password. Please use full email address as user name.", "", "", false, "javax.mail.AuthenticationFailedException: Invalid user name or password. Please use full email address as user name.");
//		bulidTemplete("Read mail", "NullPointerException ", "", "", false, "NullPointerException;NotificationReceiver.getAllMessage");
//		bulidTemplete("SQLServerException insufficient system memory", "SQLServerException ", "", "", false, "SQLServerException;insufficient system memory");
//		bulidTemplete("Error sending email", "Error sending email", "", "", false, "javax.mail.AuthenticationFailedException: 535 Authentication failed");
//		bulidTemplete("User is not a CHE + Admin;Security_AssertRoleCHEAdmin", "User is not a CHE + Admin", "", "", false, "User is not a CHE + Admin;Security_AssertRoleCHEAdmin");
//		bulidTemplete("java.lang.NullPointerException: Property 'patientDetailState'", "java.lang.NullPointerException: Property 'patientDetailState'", "", "", false, "java.lang.NullPointerException: Property 'patientDetailState'");
//		bulidTemplete("IndexOutOfBoundsException;AbstractGridPagingDataSource.getRowValue", "IndexOutOfBoundsException;AbstractGridPagingDataSource.getRowValue", "", "", false, "IndexOutOfBoundsException;AbstractGridPagingDataSource.getRowValue");
//		bulidTemplete("saving Medication and SideEffects error", "saving Medication and SideEffects error", "", "", false, "saving Medication and SideEffects error;SQLServerException;not found in PatientContact");
//		bulidTemplete("Error sending email", "Error sending email", "", "", false, "MailParseException;Could not parse mail");
//		bulidTemplete("Error saving medications", "Error saving medications", "", "", false, "Error saving medications;NullPointerException;MedicationPanel.modify(MedicationPanel.java:323)");
//		bulidTemplete("Parsing Message error", "Parsing Message error", "", "", false, "javax.mail.MessagingException: Exception getting text content;");
	}

	private void bulidTemplete(String title, String rCA,
			String reProduceSteps, String templete, boolean ignore, String stringRule) {
		DBManagement db = new DBManagement();
		db.insert(new Templete(title, rCA, reProduceSteps, templete, ignore), new StringRule(stringRule));
	}
	
	@Test
	public void ingoreTemplete(){
//		bulidTemplete("Ignore templete 1", "Component Index does not contain embedded component 'php'", "", "", true, "embedded component 'php'");
//		bulidTemplete("Ignore templete 2", "Component Index does not contain embedded component 'jsp'", "", "", true, "embedded component 'jsp'");
//		bulidTemplete("Ignore templete 3", "Component Index does not contain embedded component 'html'", "", "", true, "embedded component 'html'");
//		bulidTemplete("Ignore templete 4", "Input string is not valid", "", "", true, "Input string;is not valid");
//		bulidTemplete("Ignore templete 5", "Component Index does not contain embedded component 'pl'", "", "", true, "embedded component 'pl'");
//		bulidTemplete("Ignore templete 6", "PatientList.setupRender(PatientList.java:115)", "", "", true, "PatientList.setupRender(PatientList.java:115)");
//		bulidTemplete("Ignore templete 7", "Ignore", "", "", true, "2013-01-02 13:45:49,549 [http-443-7] ERROR org.apache.tapestry5.ioc.Registry - Operations trace");
//		bulidTemplete("Ignore templete 8", "Ignore", "", "", true, "2013-01-02 13:45:49,549 [http-443-7] ERROR org.apache.tapestry5.services.TapestryModule.RequestExceptionHandler");
//		bulidTemplete("Ignore templete 9", "exceptionreport.layout.logout ", "", "", true, "/exceptionreport.layout.logout");
//		bulidTemplete("Ignore templete 10", "UserList.setupRender(UserList.java:67)", "", "", true, "UserList.setupRender(UserList.java:67)");
//		bulidTemplete("Ignore templete 11", "No service implements the interface", "", "", true, "No service implements the interface int");
//		bulidTemplete("Ignore templete 12", "/assets/0.5.6-RELEASE/tapestry/", "", "", true, "/assets/0.5.6-RELEASE/tapestry/;java.io.FilterInputStream.close(FilterInputStream.java:155)");
//		bulidTemplete("Ignore templete 13", "The identity of the active page for this request has not yet been established", "", "", true, "The identity of the active page for this request has not yet been established");
//		bulidTemplete("Ignore templete 14", "Ignore", "", "", true, "2013-01-15 09:52:59,167 [http-443-17] INFO  com.singulex.cvmedhome.presentation.services.AppModule.TimingFilter - Request time: 0 ms - /intakecandidatelist.candidate/1416840 ");
//		bulidTemplete("Ignore templete 15", "Ignore", "", "", true, "2013-01-15 09:52:59,167 [http-443-17] ERROR org.apache.tapestry5.ioc.Registry - org.apache.tapestry5.runtime.ComponentEventException");
//		bulidTemplete("Ignore templete 16", "Ignore", "", "", true, "2013-01;NullPointerException;PhysicianPatientList.setupRender(PhysicianPatientList.java:52)");
//		bulidTemplete("Ignore templete 17", "Ignore", "", "", true, "- /_errors/");
//		bulidTemplete("Ignore templete 18", "Component Index does not contain embedded component 'cgi'", "", "", true, "embedded component 'cgi'.");
//		bulidTemplete("Ignore templete 19", "Ignore", "", "", true, "- /error/");
//		bulidTemplete("Ignore templete 20", "Ignore", "", "", true, " - /errors/");
//		bulidTemplete("Ignore templete 21", "Component Index does not contain embedded component 'htm'. ", "", "", true, "embedded component 'htm'");
//		bulidTemplete("Ignore templete 22", "Component Index does not contain embedded component 'hsp'", "", "", true, "embedded component 'hsp'");
//		bulidTemplete("Ignore templete 23", "java.io.IOException: Not in GZIP format", "", "", true, "java.io.IOException: Not in GZIP format");
//		bulidTemplete("Ignore templete 24", "Ignore - NullPointerException", "", "", true, "2013-01-23 12:09:29,724 [http-443-1] ERROR");
//		bulidTemplete("Ignore templete 25", "Ignore - NullPointerException", "", "", true, "2013-01;NullPointerException;PatientDetail.loadProfile(PatientDetail.java:166)");
//		bulidTemplete("Ignore templete 26", "Ignore - NullPointerException", "", "", true, "2013-01;NullPointerException;EditProfile.onSuccessFromEditProfileForm");
//		bulidTemplete("Ignore templete 27", "Ignore - NullPointerException", "", "", true, "2013-01-15 09:52:59,167 [http-443-17] ERROR;NullPointerException");
//		bulidTemplete("Ignore templete 28", "Ignore - NullPointerException", "", "", true, "2013-01;NullPointerException;IntakeCandidateList.setupRender(IntakeCandidateList.java:79)");
//		bulidTemplete("Ignore templete 29", "Ignore - NullPointerException", "", "", true, "2013-01;NullPointerException;ActionPlan.onPrepare(ActionPlan.java:76)");
//		bulidTemplete("Ignore templete 30", "Ignore - NullPointerException", "", "", true, "2013-02;NullPointerException;IntakeCandidateList.setupRender(IntakeCandidateList.java:79)");
//		bulidTemplete("Ignore templete 31", "Ignore - NullPointerException", "", "", true, "2013-02;NullPointerException;ActionPlan.onPrepare(ActionPlan.java:76)");
//		bulidTemplete("Ignore templete 32", "Ignore - NullPointerException", "", "", true, "2013-02;NullPointerException;PhysicianPatientList.setupRender(PhysicianPatientList.java:52)");
//		bulidTemplete("Ignore templete 33", "Ignore - NullPointerException", "", "", true, "2013-02;NullPointerException;PatientDetail.loadProfile(PatientDetail.java:166)");
//		bulidTemplete("Ignore templete 34", "Ignore - NullPointerException", "", "", true, "2013-02;NullPointerException;ChartNotes.getIsEditable(ChartNotes.java:96)");
//		bulidTemplete("Ignore templete 35", "Ignore - GZIPInputStream", "", "", true, "2013-02;EOFException;GZIPInputStream.readUByte(GZIPInputStream.java:207)");
//		bulidTemplete("Ignore templete 36", "Ignore - PatientDetail:medicalhistory.historycategories]: Index: 0, Size: 0", "", "", true, "PatientDetail:medicalhistory.historycategories]: Index: 0, Size: 0");
//		bulidTemplete("Ignore templete 37", "Ignore - NullPointerException", "", "", true, "2013-02;NullPointerException;MedicationPanel.getIsEditable(MedicationPanel.java:65)");
//		bulidTemplete("Ignore templete 38", "Ignore - NullPointerException", "", "", true, "2013-02;NullPointerException;ActionPlan.getIsEditable(ActionPlan.java:69)");
//		bulidTemplete("Ignore templete 39", "Ignore - NullPointerException", "", "", true, "2013-02;NullPointerException;Metrics.onPrepareFromMetricsForm(Metrics.java:97)");
//		bulidTemplete("Ignore templete 40", "Ignore - NullPointerException", "", "", true, "2013-02;NullPointerException;ChangePassword.getIsAdmin(ChangePassword.java:113)");
//		bulidTemplete("Ignore templete 41", "Ignore - NullPointerException", "", "", true, "2013-02;NullPointerException;PatientList.setupRender(PatientList.java:113)");
//		bulidTemplete("Ignore templete 42", "Ignore - NullPointerException", "", "", true, "2013-02;NullPointerException;HistoryQuestions.onPrepare(HistoryQuestions.java:71)");
//		bulidTemplete("Ignore templete 43", "Ignore - NullPointerException", "", "", true, "2013-02;NullPointerException;MedicationPanel.onPrepare(MedicationPanel.java:91)");
//		bulidTemplete("Ignore templete 44", "Ignore - IllegalArgumentException", "", "", true, "2013-02-28;IllegalArgumentException;has no counterpart in this collection");
//		bulidTemplete("Ignore templete 45", "Ignore - NullPointerException", "", "", true, "2013-02-28;NullPointerException;Metrics.initcountMap(Metrics.java:105)");
		
		
//		bulidTemplete("Ignore templete 46", "Ignore - NullPointerException", "", "", true, "2013-03;NullPointerException;PatientDetail.loadProfile(PatientDetail.java:166)");
//		bulidTemplete("Ignore templete 47", "Ignore - NullPointerException", "", "", true, "2013-03;NullPointerException;EditProfile.onSuccessFromEditProfileForm");
//		bulidTemplete("Ignore templete 48", "Ignore - NullPointerException", "", "", true, "2013-03;NullPointerException;IntakeCandidateList.setupRender(IntakeCandidateList.java:79)");
//		bulidTemplete("Ignore templete 49", "Ignore - NullPointerException", "", "", true, "2013-03;NullPointerException;ActionPlan.onPrepare(ActionPlan.java:76)");
//		bulidTemplete("Ignore templete 50", "Ignore - NullPointerException", "", "", true, "2013-03;NullPointerException;IntakeCandidateList.setupRender(IntakeCandidateList.java:79)");
//		bulidTemplete("Ignore templete 51", "Ignore - NullPointerException", "", "", true, "2013-03;NullPointerException;ActionPlan.onPrepare(ActionPlan.java:76)");
//		bulidTemplete("Ignore templete 52", "Ignore - NullPointerException", "", "", true, "2013-03;NullPointerException;PhysicianPatientList.setupRender(PhysicianPatientList.java:52)");
//		bulidTemplete("Ignore templete 53", "Ignore - NullPointerException", "", "", true, "2013-03;NullPointerException;PatientDetail.loadProfile(PatientDetail.java:166)");
//		bulidTemplete("Ignore templete 54", "Ignore - NullPointerException", "", "", true, "2013-03;NullPointerException;ChartNotes.getIsEditable(ChartNotes.java:96)");
//		bulidTemplete("Ignore templete 55", "Ignore - GZIPInputStream", "", "", true, "2013-03;EOFException;GZIPInputStream.readUByte(GZIPInputStream.java:207)");
//		bulidTemplete("Ignore templete 57", "Ignore - NullPointerException", "", "", true, "2013-03;NullPointerException;MedicationPanel.getIsEditable(MedicationPanel.java:65)");
//		bulidTemplete("Ignore templete 58", "Ignore - NullPointerException", "", "", true, "2013-03;NullPointerException;ActionPlan.getIsEditable(ActionPlan.java:69)");
//		bulidTemplete("Ignore templete 59", "Ignore - NullPointerException", "", "", true, "2013-03;NullPointerException;Metrics.onPrepareFromMetricsForm(Metrics.java:97)");
//		bulidTemplete("Ignore templete 60", "Ignore - NullPointerException", "", "", true, "2013-03;NullPointerException;ChangePassword.getIsAdmin(ChangePassword.java:113)");
//		bulidTemplete("Ignore templete 61", "Ignore - NullPointerException", "", "", true, "2013-03;NullPointerException;PatientList.setupRender(PatientList.java:113)");
//		bulidTemplete("Ignore templete 62", "Ignore - NullPointerException", "", "", true, "2013-03;NullPointerException;HistoryQuestions.onPrepare(HistoryQuestions.java:71)");
//		bulidTemplete("Ignore templete 63", "Ignore - NullPointerException", "", "", true, "2013-03;NullPointerException;MedicationPanel.onPrepare(MedicationPanel.java:91)");
//		bulidTemplete("Ignore templete 64", "Ignore", "", "", true, "2013-03-13 16:59:55,367 [http-443-7] ERROR");
//		bulidTemplete("Ignore templete 65", "Ignore", "", "", true, "2013-03-13 17:20:20,733 [http-443-7] ERROR");
//		bulidTemplete("Ignore templete 66", "Ignore", "", "", true, "NullPointerException;PatientChartsList.isCompleteAllDocVisit(PatientChartsList.java:89)");
//		bulidTemplete("Ignore templete 67", "Ignore", "", "", true, "NullPointerException;AccessesManagement.isPrimaryChe(AccessesManagement.java:38)");
//		bulidTemplete("Ignore templete 68", "Ignore", "", "", true, "NullPointerException;NonSgxTestResult.onActionFromGoFirst");
//		bulidTemplete("Ignore templete 69", "Ignore", "", "", true, "NullPointerException;TestResults.getShowDecisionForm");
//		bulidTemplete("Ignore templete 70", "Ignore", "", "", true, "- /assets/2.18.0.DM.RELEASE/app/");
//		bulidTemplete("Ignore templete 71", "Ignore", "", "", true, "- /cgi-bin/index.php");
//		bulidTemplete("Ignore templete 72", "Ignore", "", "", true, "- /scripts/index.php");
//		bulidTemplete("Ignore templete 73", "Ignore", "", "", true, "- /centreon/index.php");
//		bulidTemplete("Ignore templete 74", "Ignore", "", "", true, "- /PHPBazar/index.php");
//		bulidTemplete("Ignore templete 75", "Ignore", "", "", true, "- /assets/2.18.0.DM.RELEASE/core/components");
//		bulidTemplete("Ignore templete 76", "Ignore - NullPointerException", "", "", true, "NullPointerException;TestResults.createModel");
//		bulidTemplete("Ignore templete 77", "Ignore", "", "", true, "- /exceptionreport.layout.logo");
//		bulidTemplete("Ignore templete 78", "Ignore - GZIPInputStream", "", "", true, "EOFException;GZIPInputStream.readUByte(GZIPInputStream.java:207)");
//		bulidTemplete("Ignore templete 79", "Ignore - NullPointerException", "", "", true, "NullPointerException;PatientChartsList.isCompleteAllDocVisit(PatientChartsList.java:111)");
//		bulidTemplete("Ignore templete 80", "Ignore - NullPointerException", "", "", true, "NullPointerException;CandidateContact.getIsMyCandidate(CandidateContact.java:147)");
//		bulidTemplete("Ignore templete 81", "Ignore", "", "", true, "- /scripts/bin/view/foswiki/WebHome ");
//		bulidTemplete("Ignore templete 82", "Ignore", "", "", true, "- /scripts/bin/view/foswiki/WebHome");
//		bulidTemplete("Ignore templete 83", "Ignore", "", "", true, "- /cgi-bin/docs/index.html");
//		bulidTemplete("Ignore templete 84", "Ignore", "", "", true, "- /docs/index.html");
//		bulidTemplete("Ignore templete 85", "Ignore", "", "", true, "- /assets/3.3.0.FFR4.RELEASE/app/");
//		bulidTemplete("Ignore templete 86", "Ignore", "", "", true, "- /assets/3.3.0.FFR4.RELEASE/tapestry/datepicker_106/css/");
//		bulidTemplete("Ignore templete 87", "Ignore", "", "", true, "- /forgotpassword.layout.logo");
//		bulidTemplete("Ignore templete 88", "Ignore", "", "", true, "2013-04-09 19:28:58,973 [http-443-3] ERROR");
//		bulidTemplete("Ignore templete 89", "Ignore", "", "", true, "- /scripts/login.php");
//		bulidTemplete("Ignore templete 90", "Ignore", "", "", true, "- /hastymail2/index.php");
//		bulidTemplete("Ignore templete 91", "Ignore", "", "", true, "- /catalog/index.php");
//		bulidTemplete("Ignore templete 92", "Ignore", "", "", true, "- /shop/index.php ");
//		bulidTemplete("Ignore templete 93", "Ignore", "", "", true, "2013-04-21 20:33:08,530 [http-443-2] ERROR");
//		bulidTemplete("Ignore templete 94", "Ignore - NullPointerException", "", "", true, "NullPointerException;AppPage.rolesChangedInterceptor(AppPage.java:139)");
//		bulidTemplete("Ignore templete 95", "Ignore - NullPointerException", "", "", true, "NullPointerException;PhysicianPatientList.setupRender(PhysicianPatientList.java:58)");
//		bulidTemplete("Ignore templete 96", "Ignore - NullPointerException", "", "", true, "NullPointerException;IntakeCandidateList$1.getAvailableRows(IntakeCandidateList.java:88)");
//		bulidTemplete("Ignore templete 97", "Ignore", "", "", true, "- /hastymail/index.php");
//		bulidTemplete("Ignore templete 98", "Ignore", "", "", true, "- /shop/index.php");
//		bulidTemplete("Ignore templete 99", "Ignore", "", "", true, "- /non-existant-277763164");
//		bulidTemplete("Ignore templete 100", "Ignore", "", "", true, "- /assets/4.6.0.FFR5.RELEASE/ctx/");
//		bulidTemplete("Ignore templete 101", "Ignore", "", "", true, "- /assets/4.6.0.FFR5.RELEASE/tapestry/datepicker_106/css/");
//		bulidTemplete("Ignore templete 102", "Ignore", "", "", true, "2013-04-24 13:49:00,737 [http-443-6] ERROR");
//		bulidTemplete("Ignore templete 103", "Ignore", "", "", true, "2013-04-24 14:03:37,895 [http-443-5] ERROR");
//		bulidTemplete("Ignore templete 104", "Ignore - NullPointerException", "", "", true, "NullPointerException;TestResults.onSuccessFromApproveForm(TestResults.java:345)");
//		bulidTemplete("Ignore templete 104", "Ignore", "", "", true, "2013-04-23 18:32:36,199 [http-443-5] ERROR");
//		bulidTemplete("Ignore templete 105", "Ignore", "", "", true, "2013-04-30 16:34:06,008 [http-80-1] ERROR");
//		bulidTemplete("Ignore templete 106", "Ignore", "", "", true, "- /cgi-bin/about");
//		bulidTemplete("Ignore templete 107", "Ignore", "", "", true, "- /about");
//		bulidTemplete("Ignore templete 108", "Ignore", "", "", true, "- /cgi-bin/welcome.do");
//		bulidTemplete("Ignore templete 109", "Ignore", "", "", true, "- /webmail/index.php");
//		bulidTemplete("Ignore templete 110", "Ignore", "", "", true, "- /atmail/index.php");
//		bulidTemplete("Ignore templete 111", "Ignore", "", "", true, "2013-05-04 05:36:14,382 [http-80-1] ERROR");
//		bulidTemplete("Ignore templete 112", "Ignore", "", "", true, "2013-05-05 07:19:47,517 [http-80-2] ERROR");
//		bulidTemplete("Ignore templete 113", "Ignore", "", "", true, "2013-05-07 18:10:43,782 [http-80-2] ERROR");
//		bulidTemplete("Ignore templete 114", "Ignore", "", "", true, "2013-05-09 15:56:47,107 [http-80-2] ERROR");
//		bulidTemplete("Ignore templete 115", "Ignore", "", "", true, "2013-05-11 06:34:14,787 [http-80-2] ERROR");
//		bulidTemplete("Ignore templete 116", "Ignore", "", "", true, "- /robots.txt");
//		bulidTemplete("Ignore templete 117", "Ignore", "", "", true, "- /application/json");
//		bulidTemplete("Ignore templete 118", "Ignore", "", "", true, "- /assets/4.6.0.FFR5.RELEASE/stack/en_US/).replace(/&amp");
//		bulidTemplete("Ignore templete 119", "Ignore", "", "", true, "2013-05-13 03:04:47,817 [http-80-1] ERROR");
//		bulidTemplete("Ignore templete 120", "Ignore", "", "", true, "- /assets/4.6.0.FFR5.RELEASE/tapestry/");
//		bulidTemplete("Ignore templete 121", "Ignore", "", "", true, "NullPointerException;AbstractMetrics.initContacts(AbstractMetrics.java:75)");
//		bulidTemplete("Ignore templete 122", "Ignore", "", "", true, "- /fcms/index.php");
//		bulidTemplete("Ignore templete 123", "Ignore", "", "", true, "- /aphpkb/index.php");
//		bulidTemplete("Ignore templete 124", "Ignore", "", "", true, "2013-05-08 00:59:34,342 [http-80-2] ERROR");
//		bulidTemplete("Ignore templete 125", "Ignore", "", "", true, "2013-05-11 12:38:42,313 [http-80-2] ERROR");
//		bulidTemplete("Ignore templete 126", "Ignore", "", "", true, "- /index.php");
//		bulidTemplete("Ignore templete 127", "Ignore", "", "", true, "- /assets/4.6.0.FFR5.RELEASE/app/");
//		bulidTemplete("Ignore templete 128", "Ignore", "", "", true, "- /mybb/index.php");
//		bulidTemplete("Ignore templete 129", "Ignore", "", "", true, "2013-05-21 21:54:29,723 [http-80-2] ERROR");
//		bulidTemplete("Ignore templete 130", "Ignore _ NullPointerException", "", "", true, "NullPointerException: Property 'intakeState'");
//		bulidTemplete("Ignore templete 131", "Ignore", "", "", true, "2013-05-24 07:31:55,596 [http-80-3] ERROR");
//		bulidTemplete("Ignore templete 132", "Ignore", "", "", true, "2013-05-28 06:47:25,788 [http-443-2] ERROR");
//		bulidTemplete("Ignore templete 133", "Ignore", "", "", true, "2013-05-28 10:21:43,081 [http-443-11] ERROR");
//		bulidTemplete("Ignore templete 134", "Ignore _ NullPointerException", "", "", true, "NullPointerException;TestResults.onActionFromGoPre(TestResults.java:130)");
//		bulidTemplete("Ignore templete 135", "Ignore", "", "", true, "- /non-existant-1006751376");
//		bulidTemplete("Ignore templete 136", "Ignore", "", "", true, "- /ldapadmin/index.php");
//		bulidTemplete("Ignore templete 137", "Ignore", "", "", true, "- /conference/roschedule.php");
//		bulidTemplete("Ignore templete 138", "Ignore", "", "", true, "- /PostNuke/docs/CHANGELOG");
//		bulidTemplete("Ignore templete 139", "Ignore", "", "", true, "2013-05-22 13:25:48,070 [http-443-3] ERROR");
		
	}

}
