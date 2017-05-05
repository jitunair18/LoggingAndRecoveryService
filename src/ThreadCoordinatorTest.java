
public class ThreadCoordinatorTest {

   public static void main(String args[]) {
	   
	   // aSYNCHRONOUS LOGGING = 1, SYNCHRONOUS LOGGING = 0
	   int loggingmode = 0;
     
      //Kick off transaction thread T1
      TransactionThread1 T1 = new TransactionThread1("DUMMYTHREAD1",loggingmode);
      T1.start();
      
      // Kick off transaction Thread T2
      TransactionThread2 T2 = new TransactionThread2("DUMMYTHREAD2",loggingmode);
      T2.start();
      
      // Kick off transaction Thread T3
      TransactionThread3 T3 = new TransactionThread3("DUMMYTHREAD3",loggingmode);
      T3.start();
      
      
   } 
   
}