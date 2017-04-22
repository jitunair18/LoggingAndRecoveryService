import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TransactionThread3 implements Runnable {
	private Thread t;
	private String threadName;
	private int logmode;

	TransactionThread3(String name, int mode) {
		threadName = name;
		logmode = mode;
		
	}

	public void run() {
		System.out.println("Running " + threadName);

		// 1. Create a synchronized array list
		List<LogRecord> logentry = Collections.synchronizedList(new ArrayList<LogRecord>());

		// 2. create an array of log records
		LogRecord logrec1tx3 = new LogRecord();
		logrec1tx3.transactionId = 3;
		logrec1tx3.operationType = "get";
		logrec1tx3.inputParameters = "X";
		logrec1tx3.newValue = "Y";
		logrec1tx3.commitStatus = 1;
		logrec1tx3.participantType = 1;
		logrec1tx3.votingStatus = 0;
		logrec1tx3.transactionSequenceList = new ArrayList<String>(Arrays.asList("delete{ALPHA}", "get{BETA}", "put{GAMMA}"));
		logentry.add(logrec1tx3);

		LogRecord logrec2tx3 = new LogRecord();
		logrec2tx3.transactionId = 3;
		logrec2tx3.operationType = "put";
		logrec2tx3.inputParameters = "A";
		logrec2tx3.newValue = "B";
		logrec2tx3.commitStatus = 1;
		logrec2tx3.participantType = 1;
		logrec2tx3.votingStatus = 0;
		logentry.add(logrec2tx3);

		// 3. call Logging API
		try {
			LoggingOperations.log(logentry, logmode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Exiting " + threadName);

	}

	public void start() {
		
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();

		}
	}
	
}