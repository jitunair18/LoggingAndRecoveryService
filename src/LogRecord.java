import java.util.ArrayList;

/* Instance of a log record to be passed down by the transaction layer team  */
public class LogRecord {


	public long transactionId;   /*   track Transaction ID for Logging             */
	public String operationType;    /* track Operation Type of each transaction  */
	public String inputParameters;   /* log input parameters to stored procedure operation   */
	public String newValue;          /* log new value of {k:v} updated by the transaction  */
	public int commitStatus;         /* update commit status for a transaction for recovery purposes: 1 COMMITED 2 ONGOING 3 ABORTED */
	public int participantType;     /*  participant type logging in the operation for the transaction    */
	public int votingStatus;        /*  voting states for 2PC multi partition transactions  */
	public ArrayList<String> transactionSequenceList;  /* transaction sequence order   */ 
	
}
