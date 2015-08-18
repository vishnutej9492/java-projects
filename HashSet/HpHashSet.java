/**
 * Custom HashSet Class Implementation
 * 
 * @author Vinayak Pawar
 * @author Vishnutej M
 */
@SuppressWarnings("rawtypes")


public class HpHashSet extends java.util.HashSet {
	/**
	 * 	ElementList class to implement custom linked list
	 * 
	 * @author Vinayak Pawar
	 * @author Vishnutej M
	 */
	private static class ElementList {
		/**
		 * Node class for each node in linked list
		 * 
		 * @author Vinayak Pawar
		 * @author Vishnutej M
		 */
		private static class Node {
			Object element;
			Node next;
			
			/**
			 * Node constructor
			 * 
			 * @param value
			 */
			Node ( Object value ) {
				element = value;
				next = null;
			}
			
			/**
			 * Node Constructor
			 * 
			 * @param value
			 * @param reference
			 */
			Node ( Object value, Node reference ) {
				element = value;
				next = reference;			
			}		
		}
		
		Node first;
		Node last;
		
		int size;
		/**
		 * ElementList constructor
		 * 
		 */
		ElementList () {
			first = null;
			last = null;
			size = 0;		
		}
		/**
		 * addFirst method
		 *
		 * @param value
		 */
		private void addFirst ( Object value ) {
			first = new Node ( value, first);
			
			if ( last == null ) {
				last = first;
			}
		}
		/**
		 * addlast method
		 * 
		 * @param value
		 */
		private void addLast ( Object value ) {
			if ( last != null ) {
				Node newNode = new Node ( value );
				
				last.next = newNode;
				last = newNode;
				
			}
		}
		/**
		 * add method
		 * 
		 * @param value
		 * @return
		 */
		private boolean add ( Object value ) {
			if ( value == null ) {
				value = "null";
			}
			
			if ( first != null ) {
				addLast ( value );
			} 
			else {
				addFirst (value);
			}
			size++;
			return true;
		}
		/**
		 * remove method
		 * 
		 * @param value
		 * @return
		 */
		private boolean remove ( Object value ) {
			Node node = first;
			
			if ( first.element.equals( value ) ) {
				Node temp = first;
				first = first.next;
				temp = null;
				size--;
				return true;
			}
			else {
				while ( node != null ) {
					if ( node.next.element.equals( value )) {
						Node temp = node.next;
						node.next = node.next.next;
						temp = null;
						size--;
						return true;
					}
				}
			}
			return false;
		}
		/**
		 * Method to print elements
		 * 
		 * @param count
		 * @param intialCapacity
		 * @return
		 */
		private String printElements (int count, int initialCapacity ) {
			Node node = first;
			StringBuffer output = new StringBuffer();		
			while (node != null) {
				output.append (node.element.toString());						
				node = node.next;
				if ( node != null || count != ( initialCapacity -1 ) ) {
					output.append (", ");	
				}
			}		
			return output.toString();
		}
		/**
		 * Method to check existing element
		 * 
		 * @param value
		 * @return
		 */
		private int checkExisting ( Object value ) {
			Node node = first;
			while ( node != null) {
				if ( node.element.equals(value)) {
					return 1;
				}
				node = node.next;
			}
			return 0;
		}
		/**
		 * Method to clear linked list
		 * 
		 */
		private void clear () {
			size = 0;
			first = null;
			last = null;
		}
		/**
		 * method to check next node
		 * 
		 * @param currentNode
		 * @return
		 */
		private boolean checkNext ( int currentNode ) {
			int sizeOfArray = -1;
			Node node = first;
			
			while ( node != null ) {
				sizeOfArray++;
				if ( sizeOfArray == currentNode ) {
					return true;
				}
				else {
					node = node.next;
				}
			}
			return false;		
		}
		/**
		 * Method to get current element
		 * 
		 * @param currentElementLocation
		 * @return
		 */
		private Object getCurrentElement ( int currentElementLocation ) {
			int sizeOfArray = -1;
			Node node = first;
			
			while ( node != null ) {
				sizeOfArray++;
				if ( sizeOfArray == currentElementLocation ) {
					return node.element;
				}
				else {
					node = node.next;
				}
			}
			return null;
		}
		/**
		 * Method to remove element from particular location
		 * 
		 * @param currentElementLocation
		 * @return
		 */
		private int removeAtLocation ( int currentElementLocation ) {
			int sizeOfArray = -1;
			Node node = first;
			
			while ( node != null ) {
				sizeOfArray++;
				if ( sizeOfArray == currentElementLocation ) {
					if ( remove ( node.element ) == false ) {
						return 0;
					}
					else {
						
						return 1;
					}
				}
				else {
					node = node.next;
				}
			}
			return 0;
		}
	}
	
	private static final long serialVersionUID = -1871184338757398352L;
	static int initialCapacity = 10;
	ElementList[] container = new ElementList [ HpHashSet.initialCapacity ];
	int flagNull;
	/**
	 * HashSet constructor
	 */
	HpHashSet () {
		for ( int i = 0; i < HpHashSet.initialCapacity; i++ ) {
			container [ i ] = new ElementList ();
		}
		flagNull = 0;
	}
	/**
	 * HashSet constructor
	 * 
	 * @param initialCapacity
	 */
	HpHashSet ( int initialCapacity ) {
		HpHashSet.initialCapacity = initialCapacity;
		for ( int i = 0; i < HpHashSet.initialCapacity; i++ ) {
			container [ i ] = new ElementList ();
		}
		flagNull = 0;
	}
	/**
	 * HashSet Constructor 
	 * 
	 * @param initialCapacity
	 * @param loadFactor
	 */
	HpHashSet ( int initialCapacity, float loadFactor ) {
		HpHashSet.initialCapacity = initialCapacity;
		for ( int i = 0; i < HpHashSet.initialCapacity; i++ ) {
			container [ i ] = new ElementList ();
		}
		flagNull = 0;
	}
	/**
	 * function to get hash value
	 * 		
	 * @param value
	 * @return
	 */
	private int getHashValue ( Object value ) {
		return Math.abs( value.hashCode() % HpHashSet.initialCapacity );
	}
	/**
	 * Method to check existing elements
	 * 
	 * @param value
	 * @param hashCode
	 * @return
	 */
	private boolean checkExisting ( Object value, int hashCode ) {
		if ( container [ hashCode ].checkExisting ( value ) == 0 ) {
			return false;
		}	
		return true;	
	}
	/**
	 * method to add elements
	 */
	public boolean add ( Object value ) {		
		int hashCode;
		if ( value == null ) {
			if ( flagNull == 0 ) {
				flagNull = 1;
				hashCode = 0;
				value = "null";
			}
			else {
				return false;
			}
		}
		else {
			hashCode = getHashValue ( value );
		}
		if ( checkExisting ( value, hashCode ) == false ) {
			if ( container [ hashCode ].add( value ) == true ) {
				return true;
			}			
		}		
		return false;
	}
	/**
	 * Method to check content
	 */
	public boolean contains ( Object value ) {
		int hashCode = getHashValue ( value );
		return checkExisting ( value, hashCode );
	}
	/**
	 * Method to calculate size of elements
	 * 
	 */
	public int size () {
		int size = 0;
		for ( int count = 0; count < HpHashSet.initialCapacity; count++ ) {
			size = size + container [ count ].size;
		}
		return size;
	}
	/**
	 * Method to check if HashSet is empty or not
	 */
	public boolean isEmpty () {
		return size() == 0 ? true : false; 
	}
	/**
	 * Method to remove object
	 */
	public boolean remove ( Object value ) {
		int hashCode;
		if ( value == null ) {
			value = "null";
			hashCode = 0;
		}
		else {
			hashCode = getHashValue ( value );	
		}
		
		
		if ( checkExisting ( value, hashCode ) == true ) {
			if ( value == "null" && hashCode == 0 ) {
				flagNull = 0;	
			}
			
			return container [ hashCode ].remove(value);			
		}
		return false;		
	}
	/**
	 * method to clear hashset
	 */
	public void clear () {
		for ( int count = 0; count < HpHashSet.initialCapacity; count++ ) {
			container [ count ].clear ();
		}
	}
	/**
	 * Class MyIterator which implements Iterator
	 * 
	 * @author Vinayak Pawar
	 * @author Vishnutej M
	 * 
	 */
	public class MyIterator implements java.util.Iterator<Object> {
		int initialCapacity;
		int containerLocation;
		int currentElementLocation;
		int flagForNext;
		/**
		 * MyIterator function
		 */
		MyIterator () {
			 initialCapacity = HpHashSet.initialCapacity;
			 containerLocation = 0;
			 currentElementLocation = 0;
			 flagForNext = 0;
		}
		/**
		 * to check whether next element exists or not
		 */
		public boolean hasNext () {
			while ( containerLocation < initialCapacity ) {				
				if ( container [ containerLocation ].checkNext ( currentElementLocation ) == false ) {
					containerLocation++;
					currentElementLocation = 0;
				}
				else {
					currentElementLocation++;
					return true;
				}
			}
			return false;			
		}
		/**
		 * Method to traverse to next element
		 * 
		 */
		public Object next () {
			flagForNext = 1;
			return container [ containerLocation ].getCurrentElement ( currentElementLocation - 1 );
		}
		/**
		 * method to remove elements
		 * 
		 */
		public void remove () throws java.lang.IllegalStateException, java.lang.UnsupportedOperationException {
			if ( flagForNext != 1 || containerLocation >= initialCapacity ) {
				throw new java.lang.IllegalStateException ();
			}
			else {				
				if ( container [ containerLocation ].removeAtLocation ( currentElementLocation - 1 ) == 0 ) {
					throw new java.lang.UnsupportedOperationException();
				}
				else {
					
				}
			}
		}		
	}
	/**
	 * Iterator method which returns new iterator
	 * 
	 */
	public MyIterator iterator () {
		return new MyIterator ();
	}
	/**
	 * method to convert object to string
	 */
	public String toString () {
		StringBuffer output = new StringBuffer();
		output.append("[");
		for ( int count = 0; count < HpHashSet.initialCapacity; count++ ) {
			output.append( container [ count ].printElements ( count, HpHashSet.initialCapacity ) );
		}
		output.append("]");
		return output.toString();
	}
}
