public class TransactionThread2 implements Runnable {
   private Thread t;
   private String threadName;
   
   TransactionThread2( String name) {
      threadName = name;
      System.out.println("Creating " +  threadName );
   }
   
   public void run() {
      System.out.println("Running " +  threadName );
      Counter c = null;
      try {
    	 Counter.increment();
    	 
      }catch (InterruptedException e) {
         System.out.println("Thread " +  threadName + " interrupted.");
      }
      System.out.println("Thread " +  threadName + " exiting.");
      System.out.println("Let me print counter here:" + Counter.getcounter());
      
   }
   
   public void start () {
      System.out.println("Starting " +  threadName );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
         
      }
   }
}