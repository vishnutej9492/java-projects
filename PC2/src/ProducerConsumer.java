/**
 * 
 * @author Vinayak Pawar
 * @author Vishnutej M
 * 
 * This class is to simulate behaviour of producer and consumer
 * two threads starts one after other and then access produce and
 * consume functions and displays output
 * 
 */
public class ProducerConsumer implements java.lang.Runnable {
	//product id which is being produced
	int productId = 0;
	//couter to keep track of product Id
	int count = 0;
	//An object on which synchronized lock will be established
	Object lock;
	
	/**
	 * 	constructor which sets synch lock
	 * 
	 * @param synchLoc
	 */
	
	ProducerConsumer ( Object synchLoc ) {
		this.lock = synchLoc;
	}

	/**
	 * produce function which produces a product and displays 
	 */
	
	public void produce () {					
		synchronized ( lock ) {				
			if ( productId == 0 ) {
				count++;
				productId = count;
				//if there is not product in storage new product will
				//be produced
				System.out.print ( "P/" + productId + " " );
			}
			else {
				//If product is already exists, nothing will be produced
				System.out.print ( "P/" + "nothing " );
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * function to consume product
	 * if no product exists output will be C/nothing
	 */
	
	public void consume () {									
		synchronized ( lock ) {
			if ( productId != 0 ) {
				//if product exists in storage, it will be consumed
				System.out.print ( "C/" + productId + " " );					
			}
			else {
				//if no product is in storage, nothing will be printed
				System.out.print ( "C/" + "nothing " );
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			productId = 0;
		}					
	}
	
	/**
	 * two threads starts one after other and goes in running state
	 * as per scheduler 
	 */
	
	@Override
	public void run() {
		//two threads will execute produce() and consume() functions
		//simultaneously
		while ( true ) {
			produce ();
			consume ();
		}
	}
	/**
	 * main function
	 * @param args
	 */
	public static void main ( String args [] ) {
		//object of ProducerConsumer Class
		final ProducerConsumer pcObject = new ProducerConsumer ( new Object () );
		
		//t1 and t2 are two threads 
		
		Thread t1 = new Thread ( pcObject );		
		Thread t2 = new Thread ( pcObject );
		
		t1.start();
		t2.start();
	}	
}