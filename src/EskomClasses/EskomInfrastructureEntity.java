package EskomClasses;

import com.jwetherell.algorithms.data_structures.Graph;

public class EskomInfrastructureEntity<T extends Comparable<T>> extends Graph.Vertex<T>{

	
	protected int xCoordinate;
	protected int yCoordinate;
	protected Double distance;
	protected String InfrastructureType;
	protected String InfracstructureName;
	public int Index; 
	
	/**
	 * 
	 * @param value
	 */
	public EskomInfrastructureEntity(String StationName,String stationType,T value) {
		this(value,0);
		InfracstructureName = StationName;
		InfrastructureType = stationType;			
	}	
		
	/**
	 * 	constructor to initialise the value and weight of the 
	 * @param value
	 * @param weight
	 */
	public EskomInfrastructureEntity(T value, int weight) {

		super(value, weight);
	}
	/**
	 * 
	 * @return
	 */
	public int getXCoordinate() {
		return this.xCoordinate;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getYCoordinate() {
		return this.yCoordinate;
	}
	
	/**
	 * 
	 * @param xCoordinate
	 */
	public void setXCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	
	/**
	 * 
	 * @param yCoordinate
	 */
	public void setYCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getInfrastructuterType() {
		return this.InfrastructureType;
	}
	
	/**
	 * 
	 * @param infrastructureType
	 */
	public void setInfrastructureType(String infrastructureType) {
		this.InfrastructureType = infrastructureType;		
	}
	
	/**
	 * 
	 * @return
	 */
	public String getInfrastructureName()
	{
		return this.InfracstructureName;
	}
	
	/**
	 * 
	 * @param infrastructureName
	 */
	public void setInfrastructureName(String infrastructureName)
	{
		this.InfracstructureName = infrastructureName;
	}
	
	/**
	 * 
	 * @param distanceFromStart
	 */
	public void setDistanceFromStart(Double distanceFromStart) {
		this.distance = distanceFromStart;
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getDistanceFromStart() {
		return this.distance;
	}
	
	public String toString() {
		
		return "Infrastructure Name: " + this.InfracstructureName + "; Type: "+ InfrastructureType + "; corrdinates: ("+xCoordinate+","+yCoordinate+") " + super.toString();
		
	}
}
