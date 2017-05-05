import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TransactionThread1 implements Runnable {
	private Thread t;
	private String threadName;
	private int logmode;

	TransactionThread1(String name, int mode) {
		threadName = name;
		logmode = mode;
		

	}

	public void run() {
		System.out.println("Running " + threadName);

		// 1. Create a synchronized array list
		List<LogRecord> logentry = Collections.synchronizedList(new ArrayList<LogRecord>());

		// 2. create an array of log records
		LogRecord logrec1txt1 = new LogRecord();
		logrec1txt1.transactionId = 1;
		logrec1txt1.operationType = "get";
		logrec1txt1.inputParameters = "X";
		logrec1txt1.newValue = "Y";
		logrec1txt1.commitStatus = 1;
		logrec1txt1.participantType = 1;
		logrec1txt1.votingStatus = 0;
		logrec1txt1.transactionSequenceList = new ArrayList<String>(Arrays.asList("a", "b", "c"));
		logentry.add(logrec1txt1);

		LogRecord logrec2tx1 = new LogRecord();
		logrec2tx1.transactionId = 1;
		logrec2tx1.operationType = "put";
		logrec2tx1.inputParameters = "A";
		logrec2tx1.newValue = "B";
		logrec2tx1.commitStatus = 1;
		logrec2tx1.participantType = 1;
		logrec2tx1.votingStatus = 0;
		logentry.add(logrec2tx1);
		Random rand = new Random();
		for(int i=0;i<1000;i++){
			LogRecord logrec = new LogRecord();
			logrec.transactionId = i;
			logrec.operationType = "get";
			logrec.inputParameters = String.valueOf(rand.nextInt(100));
			logrec.newValue = String.valueOf(rand.nextInt(100));
			logrec.commitStatus = 1;
			logrec.participantType = 1;
			logrec.votingStatus = 1;
			logrec.transactionSequenceList = new ArrayList<String>(Arrays.asList("a","b","c"));
			logentry.add(logrec);
		}

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
		
		System.out.println("Exiting " + threadName);
	}

	public void start() {
		
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();

		}
	}
}