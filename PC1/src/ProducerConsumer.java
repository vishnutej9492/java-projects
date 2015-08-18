/**
 * 
 * @author Vinayak Pawar
 * @author Vishnutej M
 * 
 * This class simulates producer consumer problem 
 * with wait () and notify ()
 * 
 * producer will start first and puts object in storage
 * consumer then consumes existing product, and it will run forever
 * 
 */
public class ProducerConsumer {
	//product id for storage
	int productId = 0;
	//counter to maintain product id
	int count = 0;
	//object on which synch lock will be established
	Object lock;
	/**
	 * Constructor which sets synch lock	
	 * @param synchLoc
	 */
	ProducerConsumer ( Object synchLoc ) {
		this.lock = synchLoc;
	}
	/**
	 * produce function which produces a product and displays 
	 */
	public void produce () {
		while ( true ) {					
			synchronized ( lock ) {
				//producer will wait till storage is not empty
				while ( productId > 0 ) {
					try {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						lock.wait ();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
				//next product is put in storage
				count++;
				productId = count;
				System.out.print ( "P/" + productId + " " );
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//notifying other thread
				lock.notify ();
			}
		}
	}
	/**
	 * consumer consumes product from storage
	 */
	public void consume () {				
		while ( true ) {			
			synchronized ( lock ) {
				//thread will wait until storage is empty
				while ( productId == 0 ) {
					try {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						lock.wait ();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}				
				//consumer consumes product from storage
				System.out.print ( "C/" + productId + " " );
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//product id will be set to 0
				productId = 0;
				//notifying other thread
				lock.notify();
			}			
		}
	}
	/**
	 * Main function
	 * 
	 * @param args
	 */
	public static void main ( String args [] ) {
		//ProducerConsumer Object
		final ProducerConsumer pcObject = new ProducerConsumer ( new Object () );
		//Thread which runs produce
		Thread producer = new Thread ( new java.lang.Runnable () {
			@Override
			public void run() {
				pcObject.produce();				
			}			
		});
		//thread which runs consume
		Thread consumer = new Thread ( new java.lang.Runnable () {
			@Override
			public void run() {
				pcObject.consume();				
			}			
		});
		
		producer.start();
		//producer will start first and it will 
		//be checked whether it is alive or not
		//and accordingly consumer will start
		while ( !producer.isAlive() );

		consumer.start();		
	}	
}