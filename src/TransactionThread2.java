import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TransactionThread2 implements Runnable {
	private Thread t;
	private String threadName;
	private int logmode;

	TransactionThread2(String name, int mode) {
		threadName = name;
		logmode = mode;
		
	}

	public void run() {
		System.out.println("Running " + threadName);

		// 1. Create a synchronized array list
		List<LogRecord> logentry = Collections.synchronizedList(new ArrayList<LogRecord>());

		// 2. create an array of log records
		LogRecord logrec1tx2 = new LogRecord();
		logrec1tx2.transactionId = 2;
		logrec1tx2.operationType = "delete";
		logrec1tx2.inputParameters = "ALPHA";
		logrec1tx2.newValue = "BETA";
		logrec1tx2.commitStatus = 1;
		logrec1tx2.participantType = 1;
		logrec1tx2.votingStatus = 0;
		logrec1tx2.transactionSequenceList = new ArrayList<String>(Arrays.asList("get{X}", "put{Y}", "delete{Z}"));
		logentry.add(logrec1tx2);

		LogRecord logrec2tx2 = new LogRecord();
		logrec2tx2.transactionId = 2;
		logrec2tx2.operationType = "put";
		logrec2tx2.inputParameters = "A";
		logrec2tx2.newValue = "B";
		logrec2tx2.commitStatus = 1;
		logrec2tx2.participantType = 1;
		logrec2tx2.votingStatus = 0;
		logentry.add(logrec2tx2);

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