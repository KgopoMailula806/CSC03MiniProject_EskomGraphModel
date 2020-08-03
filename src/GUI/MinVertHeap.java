package	GUI;

import java.util.Arrays;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

public class MinVertHeap<T extends Comparable<T>> {
	private int capacity = 10;
	private int size = 0;
	
	Object[] eskomItems ;//= new Object[capacity];
	
    public MinVertHeap(int capacity) 
    {
		eskomItems = new Object[capacity];
		this.capacity = capacity;
	}

    public MinVertHeap() 
    {
		eskomItems = new Object[capacity];
    }
    
	@SuppressWarnings("unused")
	private int getLeftChildIndex(int parentIndex) {return 2*parentIndex +1;}
	@SuppressWarnings("unused")
	private int getRightChildIndex(int parentIndex) {return 2*parentIndex;}
	@SuppressWarnings("unused")
	private int getParrentIndext(int ChildIndex) {return (ChildIndex - 1) / 2;}
	
	@SuppressWarnings("unused")
	private boolean hasLeftChiled(int index) {return getLeftChildIndex(index) < size;}
	@SuppressWarnings("unused")
	private boolean hasRightChiled(int index) {return getRightChildIndex(index) < size;}
	@SuppressWarnings("unused")
	private boolean hasParent(int index) {return getParrentIndext(index) >= 0;}
	
	@SuppressWarnings("unused")
	private Graph.Vertex<T> leftChild(int index) {return (Vertex<T>) eskomItems[getLeftChildIndex(index)];}
	@SuppressWarnings("unused")
	private Graph.Vertex<T> rightChild(int index) {return (Vertex<T>) eskomItems[getRightChildIndex(index)];}
	@SuppressWarnings("unused")
	private Graph.Vertex<T> parent(int index) {return (Vertex<T>) eskomItems[getParrentIndext(index)];}

	@SuppressWarnings("unused")
	private void swap(int indexOne, int indexTwo) {
		Graph.Vertex<T> temp = (Vertex<T>) eskomItems[indexOne];
		eskomItems[indexOne] = eskomItems[indexTwo];
		eskomItems[indexTwo]= temp;
	}
	
	@SuppressWarnings("unused")
	private void ensureExtraCapcity() {
		if(size == capacity) {
			eskomItems = Arrays.copyOf(eskomItems, capacity * 2);
			capacity*= 2;
		}
	}
	
	@SuppressWarnings("unused")
	public Graph.Vertex<T> peek () {
		if (size == 0) throw new IllegalStateException();
		return (Vertex<T>) eskomItems[0];
	}
	
	/*
	 * @return the minimum element
	 */
	@SuppressWarnings("unused")
	public Graph.Vertex<T> pull() {
		if (size == 0) throw new IllegalStateException();
		Graph.Vertex<T> item = (Vertex<T>) eskomItems[0];
		size--;
		heapifyDown();
		return item;
	}
	
	/**
	 * 
	 * @param item
	 * @return
	 */
	@SuppressWarnings("unused")
	public Vertex<T> add(Graph.Vertex<T> item) {
		ensureExtraCapcity();
		eskomItems[size] = item;
		size--;
		heapifyUp();
		return item;
	}
	
	
	/**
	 * Heapify methods
	 */	
	private void heapifyDown() {
		//Check if there is no left child, if so then there is certainly no right child
		int index = 0;
		while(hasLeftChiled(index)){
			int smallerChildIndex = getLeftChildIndex(index);
			
			if(hasRightChiled(index) && rightChild(index).compareTo(leftChild(index)) < 0) {
				
				//Get the smaller child
				smallerChildIndex = getRightChildIndex(index);
				
			}
			
			// if the parent is smaller than the smallest child end this loop
			if(((Vertex<T>) eskomItems[index]).compareTo((Vertex<T>) eskomItems[smallerChildIndex]) < 0) { //compare the weights
				index = smallerChildIndex; // move downwards
				break;
			}else { //if the parent is larger than the smallest child
				swap(index,smallerChildIndex);
			}
			
		}
		
	}
	
	/**
	 * 
	 */
	private void heapifyUp() {
		//stars at the last item added 
		int index = size - 1;
		while(hasParent(index) && ((Vertex<T>) parent(index)).compareTo((Vertex<T>) eskomItems[index]) >0) {
			swap(getParrentIndext(index), index);
			index = getParrentIndext(index);			
		}
		
	}
}
