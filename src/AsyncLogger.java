import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class AsyncLogger {

	static long currPos = 0;
	static String logPath =  System.getProperty("user.dir")+"/LOGGER.txt";
	
	static class AttachmentObject{
		
		AsynchronousFileChannel channel;
		ByteBuffer buffer;
		List<byte[]> list;
		public AttachmentObject(AsynchronousFileChannel channel,ByteBuffer buffer,List<byte[]> list) {
			this.channel = channel;
			this.buffer = buffer;
			this.list = list;
		}
	}
	
	static List<byte[]> getByteArray(List<LogRecord> logBuffer){
		int byteswritten = 0;
		List<byte[]> list = new ArrayList<byte[]>();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		for (LogRecord elem : logBuffer) {
		try {
			String toWrite = elem.toString()+"\n";
			byteswritten += toWrite.getBytes().length;
			if(byteswritten > 3950){
				//System.out.println(baos.toByteArray().length);
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
	
	static class AsyncCompletionHandler implements CompletionHandler<Integer,AttachmentObject>{
		  int current = 0;
		  long position = currPos;
		  @Override
	      public void completed(Integer result, AttachmentObject attachment) {
			  try{
			  current+=1;
			  //System.out.println("Asynchronous File Writing......... in Thread:" + Thread.currentThread().getName());
			  //System.out.println("current:"+current);
       	      if(current<attachment.list.size()){
       		
       	    	 System.out.println("Bytes flushed to disk:"+ result + " Thread Name:" + Thread.currentThread().getName());
       	    	 attachment.channel.force(true);
				
	        /*	 try {
					System.out.println("Channel Size: "+ attachment.channel.size());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} */
	        	 
	        	  ByteBuffer buffer = ByteBuffer.wrap(attachment.list.get(current));
	        	  position+=result;
	        	  //System.out.println("Position: "+position);
	        	  attachment.channel.write( buffer,position, attachment, this);
	        	  
       	      	}	
			  }
			  catch(Exception e){
				
			  }
			 
	      }

	      @Override
	      public void failed(Throwable e, AttachmentObject attachment) {
	          System.err.println("File Write Failed Exception:");
	          e.printStackTrace();
	      }
		  
	  }
	
	static void asyncWriteToLog(List<LogRecord> logBuffer) throws IOException{
		
		System.out.println(logBuffer);
		if(logBuffer == null || logBuffer.size() == 0){
			return;
		}
		Path path = Paths.get(logPath);
		AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, 
				StandardOpenOption.CREATE,StandardOpenOption.WRITE);
		currPos = channel.size();
		//System.out.println(currPos);
		CompletionHandler<Integer, AttachmentObject> handler = new AsyncCompletionHandler();
		//System.out.println("Buffer Size: " +logBuffer.size());
		List<byte[]> list = getByteArray(logBuffer);
		ByteBuffer buffer = ByteBuffer.wrap(list.get(0));
		AttachmentObject obj = new AttachmentObject(channel, buffer,list);
		channel.write(buffer, currPos, obj, handler);
	}

}
