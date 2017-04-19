import org.testng.Assert;
import org.testng.annotations.Test;

/*  Test templates for logging mechanism */
public class LoggingTest {
	
	
	/* Spawn a single thread to check WAL implementation  */
	@Test
	public void singleTransactionLogTest() {
		
       
	}
	
	/* Spawn multiple threads to ensure write to logging in synchronous fashion  */
	@Test
	public void multiTransactionLogTest() {
		
       
	}
	
	/* Synchronous technique to flush WAL contents to disk*/
	@Test
	public void synchronousWriteAheadLogging() {
		
       
	}
	
	/* Non blocking technique to flush WAL contents to disk without transaction thread blocking  */
	@Test
	public void asynchronousWriteAheadLogging() {
		
       
	}
	
	/* ensure atomic updates to WAL file  */
	@Test
	public void atomicLoggerImplementation(){
		

	
	}
	
	/*  log file switch if file size increases 1 MB   */
	@Test
	public void logfileSwitch(){
		
		
	}
	
	
	

}
