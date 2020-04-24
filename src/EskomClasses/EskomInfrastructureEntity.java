package EskomClasses;

import com.jwetherell.algorithms.data_structures.Graph;

public class EskomInfrastructureEntity<T extends Comparable<T>> extends Graph.Vertex<T>{

	protected int xCoordinate;
	protected int yCoordinate;
	
	public EskomInfrastructureEntity(Comparable<T> value) {
		super((T) value);
		

	}
	
	/**
	 * 	constructor to initialise the value and weight of the 
	 * @param value
	 * @param weight
	 */
	public EskomInfrastructureEntity(T value, int weight) {

		super(value, weight);
	}
	
	public int getXCoordinate() {
		return this.xCoordinate;
	}
	public int getYCoordinate() {
		return yCoordinate;
	}
	public void setXCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public void setYCoordinate(int yCoordinate) {
		
	}

}
