
/* Authors: Shreejit Nair, Devesh Kumar Singh  */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoggingOperations {

	static ArrayList<LogRecord> logBuffer = new ArrayList<LogRecord>();
	private static final SpinLock lock = new SpinLock();

	public static void synchronousWriteToLog() throws IOException {

		try {
			SyncLogger.syncWriteToLog(logBuffer);
		} finally {

		}
	}

	public static void asynchronousWriteToLog() throws IOException {

		try {
			AsyncLogger.asyncWriteToLog(logBuffer);
		} finally {

		}

	}

	public static void log(List<LogRecord> logRecordList, int mode) throws IOException, InterruptedException {

		if (mode == 1) {
			try {
				lock.lock();
				AsyncLogger.asyncWriteToLog(logRecordList);
			} finally {
				lock.unlock();
			}

		} else {
			try {
				lock.lock();
				SyncLogger.syncWriteToLog(logRecordList);
			} finally {
				lock.unlock();

			}
		}

	}

	/*
	 * Synchronized access to log buffer to append content to in memory Log
	 * Buffer
	 */
	public static synchronized int appendLogBuffer(LogRecord logRecord) {

		logBuffer.add(logRecord);
		return 1;
	}

	/* Flush log entries to disk */
	public static int writeToDisk() {

		return 1;
	}

	/* clear log buffer after writing to disk */
	public static int clearLogBuffer() {
		logBuffer.clear();
		return 1;
	}

	/* Synchronous blocking write exposed to Transaction Layer */
	public static synchronized int blockingWrite(ArrayList<LogRecord> logRecordsList) {

		// 1. check eligible for flushing to disk
		// 2. clear Log Record buffer if flushed
		synchronized (logRecordsList) {
			// Do THREAD SAFE OPERATIONS on LOG BUFFER NOW
		}
		return 1;
	}

	/* Non synchronous blocking write exposed to Transaction Layer */
	public static int nonblockingWrite(ArrayList<LogRecord> logRecordsList) {
		// 1. spawn new thread to execute
		// 2. check eligible for flushing to disk
		// 3. clear Log Record buffer
		synchronized (logRecordsList) {
			// DO THREAD SAFE OPERATIONS ON LOG BUFFER NOW
		}
		return 1;
	}

}
