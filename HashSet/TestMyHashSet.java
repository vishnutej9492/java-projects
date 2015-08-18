import java.util.*;
/**
 * Class to test HpHashSet
 * 
 * @author Vinayak Pawar
 * @author Vishnutej M
 * 
 */
public class TestMyHashSet {
	/**
	 * main method
	 * 
	 * @param args
	 */
	public static void main ( String args[] ) {
		HashSet<Object> defaultHashSet = new HashSet<Object>();
		HpHashSet myHashSet = new HpHashSet();
		String choice;
		Scanner input = new Scanner ( System.in );
		do {
			System.out.println ( "Opearations - " );
			System.out.println ( "1. Add" );
			System.out.println ( "2. Clear" );
			System.out.println ( "3. Contains" );
			System.out.println ( "4. IsEmpty" );
			System.out.println ( "5. Remove" );
			System.out.println ( "6. Print" );
			System.out.println ( "7. Size" );
			System.out.println ( "8. Exit" );
			System.out.print ( "Enter operation number to perform : " );
			choice = input.next();
			
			switch ( choice ) {
			case "1" : System.out.println( "Enter Value to add : " );
					   String item = input.next();
					   System.out.println( "HashSet Output   : " + defaultHashSet.add(item));
					   System.out.println( "HpHashSet Output : " + myHashSet.add(item));
					   break;
					   
			case "2" : System.out.println( "Clearing Hash Sets : " );
			   		   defaultHashSet.clear();
			           myHashSet.clear();
			           System.out.println( "HashSet Contents   : " + defaultHashSet );
					   System.out.println( "HpHashSet Contents : " + myHashSet );
			           break;
			           
			case "3" : System.out.println( "Enter item to check in Hash Set : " );
					   item = input.next();
			   		   System.out.println( "HashSet Output   : " + defaultHashSet.contains(item));
			   		   System.out.println( "HpHashSet Output : " + myHashSet.contains(item));
			   		   break;
			   		   
			case "4" : System.out.println( "IsEmpty Function : " );
					   System.out.println( "HashSet Output   : " + defaultHashSet.isEmpty());
			           System.out.println( "HpHashSet Output : " + myHashSet.isEmpty());
			           System.out.println( "HashSet Contents   : " + defaultHashSet );
					   System.out.println( "HpHashSet Contents : " + myHashSet );
			           break;
			           
			case "5" : System.out.println( "Enter Value to remove : " );
			   		   item = input.next();
			           System.out.println( "HashSet Output   : " + defaultHashSet.remove(item));
			           System.out.println( "HpHashSet Output : " + myHashSet.remove(item));
			           System.out.println( "HashSet Contents   : " + defaultHashSet );
					   System.out.println( "HpHashSet Contents : " + myHashSet );
			           break;
			           
			case "6" : System.out.println( "Contents of Hash Set : " );
	   		   		   System.out.println( "HashSet Contents   : " + defaultHashSet );
			           System.out.println( "HpHashSet Contents : " + myHashSet );
	                   break;
	                   
			case "7" : System.out.println( "Size : " );
					   
					   System.out.println( "HashSet Output   : " + defaultHashSet.size());
					   System.out.println( "HpHashSet Output : " + myHashSet.size());
					   break;
			}
			
		} while ( !choice.equals ( "8") );
		input.close();
		
		System.out.println ( "Printing HpHashSet Elements with Iterator : " );
		HpHashSet.MyIterator newIterator = myHashSet.iterator();
		System.out.print("[");
		while ( newIterator.hasNext()) {
			System.out.print(newIterator.next().toString() + " ");
		}
		System.out.print("]");
	}
}
