import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SyncLogger {

	static long currPos = 0;
	static String logPath =  System.getProperty("user.dir")+"/LOGGER.txt";
	
	static List<byte[]> getByteArray(List<LogRecord> logBuffer){
		int byteswritten = 0;
		List<byte[]> list = new ArrayList<byte[]>();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		for (LogRecord elem : logBuffer) {
		try {
			String toWrite = elem.toString()+"\n";
			byteswritten += toWrite.getBytes().length;
			if(byteswritten > 4096){
				list.add(baos.toByteArray());
				baos.reset();
				byteswritten = 0;
				out.flush();
			}
			out.writeUTF(elem.toString()+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		list.add(baos.toByteArray());
		logBuffer.clear();
		try {
			baos.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	static void syncWriteToLog(List<LogRecord> logBuffer) throws IOException{
		
		System.out.println("Calling Sync Logger");
		if(logBuffer == null || logBuffer.size() == 0){
			return;
		}
		List<byte[]> list = getByteArray(logBuffer);
		FileOutputStream fos = new FileOutputStream(logPath,true);

		for(byte[] barr:list){
			fos.write(barr);
			fos.flush();
		}
		fos.close();
		System.out.println("Synchronous File Writing......... in Thread:" + Thread.currentThread().getName());
	}

}
