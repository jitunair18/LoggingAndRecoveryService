/* Authors: Shreejit Nair, Devesh Kumar Singh  */
import java.util.ArrayList;

public class LoggingOperations {
	
	static ArrayList<LogRecord> logBuffer = new ArrayList<LogRecord>();
	static final int TRANSACTIONGROUPCOMMIT = 2;
	
	/* Synchronized access to log buffer to append content to in memory Log Buffer  */
	public static synchronized int appendLogBuffer(LogRecord logRecord){
		
		logBuffer.add(logRecord);
		return 1;
	}
	
	/* API to check transactions are eligible for group commit - flush to disk   */
	public static int groupCommitEligible(){
		/* If Log buffer has more than 2 transactions in COMMITTED status flush to disk       */
		int eligiblecount = 0;
		for(LogRecord lr : logBuffer){
			if(lr.commitStatus == 1){
				eligiblecount += 1;
			}
		}
		if(eligiblecount >= TRANSACTIONGROUPCOMMIT){
			int output = writeToDisk();
			if(output == 1){
				clearLogBuffer();
			}
		}
			
		return 1;
	}
	
	/* Flush log entries to disk   */
    public static int writeToDisk(){
    	 
    	return 1;
    }
    
    /* clear log buffer after writing to disk */
    public static int clearLogBuffer(){
    	logBuffer.clear();
    	return 1;
    }
    
    /*  Synchronous blocking write exposed to Transaction Layer  */
    public static synchronized int blockingWrite(ArrayList<LogRecord> logRecordsList){
    	
    	//1. check eligible for flushing to disk   
    	//2. clear Log Record buffer if flushed
    	synchronized(logRecordsList){
    		// Do THREAD SAFE OPERATIONS on LOG BUFFER NOW
    	}
    	return 1;
    }
    
    
    /*  Non synchronous blocking write exposed to Transaction Layer   */
    public static int nonblockingWrite(ArrayList<LogRecord> logRecordsList){
    	//1. spawn new thread to execute
    	//2. check eligible for flushing to disk
    	//3. clear Log Record buffer
    	synchronized(logRecordsList){
    		// DO THREAD SAFE OPERATIONS ON LOG BUFFER NOW
    	}
    	return 1;
    }
	
	
	
}
