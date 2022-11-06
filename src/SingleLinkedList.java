
/**
 * represents a singly linked list that stores objects of type E
 * @author Cindy Wu
 * @version 1.0 - 11/06/2020
 * @param <E> 
 */
class SingleLinkedList<E> {
	
	/**
	 * Node<T>
	 * represents a Node that stores objects of type T and the next node it is linked to
	 * @author Cindy Wu
	 * @param <T> 
	 */
	private class Node <T> {
		private T item;
		private Node<T> next;
		
		/**
		 * constructs a Node given an object to store
		 * @param item - object to store in node 
		 */
		public Node(T item) {
			this.item = item;
			this.next = null;
		}
		
		/**
		 * constructs a Node given an object to store and next Node
		 * @param item - object to store in node
		 * @param next - next node in list
		 */
		@SuppressWarnings("unused")
		public Node (T item, Node<T> next) {
			this.item = item;
			this.next = next;
		}
		
		/**
		 * returns the item store in the node
		 * @return item - object stored in node
		 */
		public T getItem() {
			return this.item;
		}
		
		/**
		 *	returns the next Node in the list from current Node
		 * @return next Node
		 */
		public Node<T> getNext(){
			return this.next;
		}
		
		/**
		 * sets the current Node's next Node to the given Node
		 * @param next
		 */
		public void setNext(Node<T> next) {
			this.next = next;
		}
	}
	
	/**
	 * First Node of the list
	 */
	private Node<E> head;
	
	/**
	 * adds an item to the back of the list
	 * @param item
	 */
	public void add(E item) {
		Node<E> tempNode = head;
		
		if (head==null) {
			head = new Node<E>(item);
			return;
		}
		
		while(tempNode.getNext()!=null) {
			tempNode = tempNode.getNext();
		}
		
		tempNode.setNext(new Node<E>(item));
		return;
	}
	
	/**
	 * finds and returns the item at the specified index
	 * @param index
	 * @return item at specified index
	 */
	public E get(int index) {
		int cnt = 0;
		Node<E> node = head;
		
		while(cnt!=index && node.getNext()!=null) {
			node = node.getNext();
			cnt++;
		}
		
		return node.getItem();
	}
	
	/**
	 * finds and returns the index of the specified index or returns -1 if no such item exists
	 * @param item
	 * @return index of item in list
	 */
	public int indexOf(E item) {
		int index = 0;
		Node<E> cur = head;
		
		while(cur!=null) {
			if (cur.getItem().equals(item)) {
				return index;
			}
			cur = cur.getNext();
			index++;
		}
		return -1;
	}
	
	/**
	 * removes the Node at the specified index and returns its item
	 * @param index
	 * @return item in the removed node
	 */
	public E remove(int index) {
		int cnt = 0;
		Node<E> prev = head;
		Node<E> removed = head;
		
		while(cnt!=index) {
			prev = removed;
			removed = removed.getNext();
			cnt++;
		}
		
		if (cnt==0) {
			this.head = removed.getNext();
		} else {
			prev.setNext(removed.getNext());
		}
		
		return removed.getItem();
	}
	
	/**
	 * removes the specified item from the list 
	 * @param item
	 * @return true if removed, false if object does not exist in the list/unable to remove
	 */
	public boolean remove(E item) {
		if (head==null) {
			return false;
			
		} else if (head.getNext()==null) {
			head=null;
			return true;
		}
		
		Node<E> prev = head;
		Node<E> removed = head;
		
		while(removed.getNext()!=null) {
			prev = removed;
			removed = removed.getNext();
			
			if (removed.getItem().equals(item)) {
				prev.setNext(removed.getNext());
				return true;
			}
		}

		return false;
	}
	
	/**
	 * clears the entire list
	 */
	public void clear() {
		this.head = null;
	}
	
	/**
	 * returns the number of Nodes in the list
	 * @return the size of the list
	 */
	public int size() {
		
		if (head==null) {
			return 0;
		}
		
		int size = 1;
		Node<E> tempNode = head;
		
		while(tempNode.getNext()!=null) {
			tempNode = tempNode.getNext();
			size++;
		}
		
		return size;
	}
}

