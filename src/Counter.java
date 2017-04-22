public class Counter{

  private static final SpinLock lock = new SpinLock();
  private static int count = 0;

  
  //
  public static int increment() throws InterruptedException{
   
        lock.lock();
    	for(int i = 0; i < 100; i++){
    	count++;
    	}
    	lock.unlock();
    
    return 1;
  }
  
  public static int getcounter(){
	  
	  System.out.println(count);
	  return count;
  }
  
  public static void main(String[] args) throws InterruptedException{
	  Counter c1 = new Counter();
	  c1.increment();
	  Counter c2 = new Counter();
	  c2.increment();
	  Counter.getcounter();
  }
}