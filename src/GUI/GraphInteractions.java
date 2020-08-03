package GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

import EskomClasses.EskomInfrastructureEntity;

/**
 * 
 * @author 218004702 KBK Mailula
 *
 * @param <T>
 */
public class GraphInteractions<T extends Comparable<T>> {

	private Graph<T> EskomLowerDistributionChain;	
	private ArrayList<Vertex<T>> visitedNodes;
	public static int CostLimit = 5000000;
	public GraphInteractions(Graph<T> EskomLowerDistributionChain) {
		this.EskomLowerDistributionChain = EskomLowerDistributionChain;
		generateEskomEntity();
		//generateEdges();
	}
	 
	
	/**
	 *  uses the Graph: EskomLowerDistributionChain find the shortest path 
	 *  for the give nodes 
	 * @return list of visited nodes where the last entry is the goal node and the first entry is the start node
	 */
	public ArrayList<Graph.Vertex<T>> DijkstrasAlgorithm(EskomInfrastructureEntity<T> start, EskomInfrastructureEntity<T> goal) {
		
		this.visitedNodes = new ArrayList<Vertex<T>>() ;
		
		List<Edge<T>> edges = EskomLowerDistributionChain.getEdges();
		int numberOfEdges = EskomLowerDistributionChain.getEdges().size();  
		
		// loop through all the nodes and give them a distance of infinity except for the start 		
		for(int i = 0;i<EskomLowerDistributionChain.getEdges().size();i++)
		{
			if(edges.get(i).getFromVertex().equals(start)) {
				//start.setDistanceFromStart(0D);
				((EskomInfrastructureEntity<T>) edges.get(i).getFromVertex()).setDistanceFromStart(0D); 
			}else {
				((EskomInfrastructureEntity<T>) edges.get(i).getFromVertex()).setDistanceFromStart(Double.POSITIVE_INFINITY);	
			}			
		}
				
		
		//in case 		
		start.setDistanceFromStart(0D);
		goal.setDistanceFromStart(Double.POSITIVE_INFINITY);
		
		EskomInfrastructureEntity<T> currentVertex = start;
		EskomInfrastructureEntity<T> previoustVertex = start;
		Double distance = 0D ;
		boolean attheGoal = false;
		while(currentVertex != goal) {
			
			//for all the edges in the current node
			Double shortestsPath = 0D ; 			
			Double[] nums = new Double[currentVertex.getEdges().size()];
			int i = 0;
			for( Edge<T> edge: currentVertex.getEdges()) {			
				if(!IsVisited(edge)) {
					nums[i] = (double) edge.getCost();
					//set the distance of the next vertices relative to the current node
					((EskomInfrastructureEntity<T>)edge.getToVertex()).setDistanceFromStart(distance + nums[i]);
					i++;
					//System.err.println(((EskomInfrastructureEntity<T>)edge.getToVertex()).getInfrastructureName());
				}else {
					//but is its not the latest visited node 
					if(!previoustVertex.equals((EskomInfrastructureEntity<T>)edge.getToVertex())) {
						nums[i] = (double) edge.getCost();
					}else
					nums[i] = Double.POSITIVE_INFINITY;
					i++;
				}
				
				
			}
			//get the min cost vertex		
			Arrays.sort(nums);
			EskomInfrastructureEntity<T> minVertex = null;
			
			for( Edge<T> edge: currentVertex.getEdges()) {
				//if the to vertex from the above edges has already been visited then ignore it
				if(!IsVisited(edge)) {
					
					if(nums[0].equals((double)edge.getCost())) {
						//get the edge the min belongs to
						minVertex = (EskomInfrastructureEntity<T>) edge.getToVertex();		
						if(minVertex == null) {
							System.err.println("null");
						}
						//update distance
						distance += (double)edge.getCost();
						start.setDistanceFromStart(distance);
					}	
				}
				
				System.err.println(((EskomInfrastructureEntity<T>)edge.getToVertex()).getInfrastructureName());
			}
			
			//insert the old current in the visited list 
			visitedNodes.add(currentVertex);
			previoustVertex = currentVertex;
			// move the mins edge as the new current  			
			currentVertex = minVertex;
			//System.err.println(((EskomInfrastructureEntity<T>)currentVertex).getInfrastructureName());
			/*if(currentVertex.equals(goal)) {
				attheGoal =true;
				continue;
			}*/
			//look back at the previously inspected nodes to see if whether they offer a better path		
			if(currentVertex == null)				
				break;
			else
			{
				for(Edge<T> edge :currentVertex.getEdges()) {
				
				for(Vertex<T> visitedVertex :  visitedNodes) {
					// if either one of the edges from the current vertex link up with a visited node and is not from the most recently visited 
					// vertex
					if(edge.getToVertex().equals(visitedVertex) && visitedVertex != visitedNodes.get(visitedNodes.size()-1)) {
						//compare the cumulative distance
						//if the distance achieved is longer than the one 
						if(distance > ((EskomInfrastructureEntity<T>) edge.getFromVertex()).getDistanceFromStart() + (Double.parseDouble(""+edge.getCost()))) {
							//
							currentVertex.setDistanceFromStart(Double.parseDouble(""+edge.getCost()));
							distance = currentVertex.getDistanceFromStart();
							//TODO: changing the path taken
							visitedNodes.remove(visitedNodes.size()-1);
							visitedNodes.add(edge.getFromVertex());
						}else {
							
						}
					}
				}
			}
				
				
			}
			if(currentVertex.equals(goal)){
				
			}
			System.err.println(distance);
		}
		
		return visitedNodes;
	}
	
	private boolean IsVisited(Edge<T> edge) {
		
		boolean isVisited = false;
		for(Vertex<T> potentiallyVisitednode : this.visitedNodes) {
			if(edge.getToVertex().equals(potentiallyVisitednode)) {
				return true;
			}
		}
		
		return isVisited;
	}

	/**
	 * TODO:Potential Debugging
	 *  for the relative vertices set the path and its cost 
	 * @param node2
	 * @param node1
	 * @param cost
	 */
	public void asstablishPathToFrom(Object node1, Object node2,int cost) {
	
		Graph.Edge<Double> fromsEdge = new Graph.Edge<Double>();
		((EskomInfrastructureEntity<Double>) node1).addEdge(fromsEdge);
		
		Graph.Edge<Double> tosEdge = new Graph.Edge<Double>();
		((EskomInfrastructureEntity<Double>) node2).addEdge(tosEdge);		
		
		fromsEdge.setFrom(node1);
		fromsEdge.setTo(node2);
		fromsEdge.setCost(cost);
		
		tosEdge.setFrom(node2);
		tosEdge.setTo(node1);
		tosEdge.setCost(cost);
		
		
		//((Vertex<Double> )node1).getEdges().add(fromsEdge);
		//((Vertex<Double> )node2).getEdges().add(tosEdge);
		
		EskomLowerDistributionChain.getEdges().add((Edge<T>) tosEdge);
		EskomLowerDistributionChain.getEdges().add((Edge<T>) fromsEdge);
	}
	
	/**
	 * 
	 * @param infrastructure
	 */
	public void insertNewInfratructure(EskomInfrastructureEntity<T> infrastructure) {
		
		if(EskomLowerDistributionChain != null)
		{
			EskomLowerDistributionChain.getVertices().add(infrastructure);
		}		
		else System.err.println("EskomLowerDistributionChain in null method : insertNewInfratructure ");
	}
	
	/**
	 *  prints the graph
	 */
	
	public void printGraph() {
		
		System.out.println(this.EskomLowerDistributionChain.toString());
		
	}	
	
	/**
	 * Get the graph  
	 * @return
	 */
	public Graph<T> getGraph(){
		return this.EskomLowerDistributionChain;
	}
	
	/**
	 * Basically test data
	 */
	private void  generateEskomEntity() {		
			
		Random rand = new Random(0);
		
		//Double s = 27500000.0;
		//Vertices
		EskomInfrastructureEntity<Double> infrastructureA = new EskomInfrastructureEntity<>("KRUISPUNT SUBSTATION","SUBSTATION",27500000.0);
		EskomLowerDistributionChain.getVertices().add((Vertex<T>) infrastructureA);
		EskomInfrastructureEntity<Double> infrastructureB = new EskomInfrastructureEntity<>("SOWETO STATION","SUBSTATION",27500000.0);
		EskomLowerDistributionChain.getVertices().add((Vertex<T>) infrastructureB);
			
		asstablishPathToFrom(infrastructureA,infrastructureB,Math.abs(rand.nextInt(CostLimit)));
		
		EskomInfrastructureEntity<Double> infrastructureC = new EskomInfrastructureEntity<>("12123 DISTRIBUTION MAIN","ZONE DISTRBUTION MAIN",27500000.0);
		EskomLowerDistributionChain.getVertices().add((Vertex<T>) infrastructureC);
		
		asstablishPathToFrom(infrastructureC,infrastructureB,Math.abs(rand.nextInt(CostLimit)));
		
		EskomInfrastructureEntity<Double> infrastructureD = new EskomInfrastructureEntity<>("THEMBISA STATION","SUBSTATION",27500000.0);
		EskomLowerDistributionChain.getVertices().add((Vertex<T>) infrastructureD);
		
		asstablishPathToFrom(infrastructureC,infrastructureD,Math.abs(rand.nextInt(CostLimit)));		
		//asstablishPathToFrom(infrastructureD,infrastructureB,Math.abs(rand.nextInt()));
		
		EskomInfrastructureEntity<Double> infrastructureE = new EskomInfrastructureEntity<>("JOHANNESBURG CDB STATION","SUBSTATION",27500000.0);
		EskomLowerDistributionChain.getVertices().add((Vertex<T>) infrastructureE);
		
		asstablishPathToFrom(infrastructureD,infrastructureE,Math.abs(rand.nextInt(CostLimit)));
		
		EskomInfrastructureEntity<Double> infrastructureF = new EskomInfrastructureEntity<>("BOTLOKWA STATION","ZONE DISTRBUTION MAIN",27500000.0);
		EskomLowerDistributionChain.getVertices().add((Vertex<T>) infrastructureF);
				
		asstablishPathToFrom(infrastructureE,infrastructureF,Math.abs(rand.nextInt(CostLimit)));
		
		EskomInfrastructureEntity<Double> infrastructureG = new EskomInfrastructureEntity<>("POLOKWANE STATION","SUBSTATION",27500000.0);
		EskomLowerDistributionChain.getVertices().add((Vertex<T>) infrastructureG);
		
		
		asstablishPathToFrom(infrastructureG,infrastructureA,Math.abs(rand.nextInt(CostLimit)));
		
		
		EskomInfrastructureEntity<Double> infrastructureH = new EskomInfrastructureEntity<>("JOHANNESBURG HQ","HQ",27500000.0);
		EskomLowerDistributionChain.getVertices().add((Vertex<T>) infrastructureH);
		
		
		EskomInfrastructureEntity<Double> infrastructureI = new EskomInfrastructureEntity<>("POLOKWANE HQ","HQ",27500000.0);
		EskomLowerDistributionChain.getVertices().add((Vertex<T>) infrastructureI);
		
		
		asstablishPathToFrom(infrastructureI,infrastructureA,Math.abs(rand.nextInt(CostLimit)));
		asstablishPathToFrom(infrastructureH,infrastructureC,Math.abs(rand.nextInt(CostLimit)));
		//asstablishPathToFrom(infrastructureF,infrastructureG,Math.abs(rand.nextInt()));
		
		
	}
	
	/**
	 * Basically test data
	 
	private void  generateEdges() {
		
		EskomInfrastructureEntity<Double> node1 = new EskomInfrastructureEntity<>("KRUISPUNT SUBSTATION","SUBSTATION",27500000.0);
		EskomLowerDistributionChain.getVertices().add((Vertex<T>) node1);
		
		EskomInfrastructureEntity<Double> node2 = new EskomInfrastructureEntity<>("Stellenbosch SUBSTATION","SUBSTATION",27500000.0);
		EskomLowerDistributionChain.getVertices().add((Vertex<T>) node2);
		
		int cost = 100;
		
		Graph.Edge<Double> edge = new Graph.Edge<>();		
		edge.setFrom(node1);
		edge.setTo(node2);
		edge.setCost(cost);
		
		node1.addEdge(edge);
		//add the new edge to the Graphs list
		EskomLowerDistributionChain.getEdges().add((Edge<T>) edge); 
		
		Graph.Edge<Double> edge1 = new Graph.Edge<>();
		edge1.setFrom(node1);
		edge1.setTo(node2);
		edge1.setCost(cost);
		
		node1.addEdge(edge);		
		//add the new edge
		EskomLowerDistributionChain.getEdges().add((Edge<T>) edge1);
		Graph.Edge<Double> edge2 = new Graph.Edge<>();
		edge2.setFrom(node1);
		edge2.setTo(node2);
		edge2.setCost(cost);
	
		
		node1.addEdge(edge);
		//add the new edge
		EskomLowerDistributionChain.getEdges().add((Edge<T>) edge2);
		Graph.Edge<Double> edge3 = new Graph.Edge<>();
		edge3.setFrom(node1);
		edge3.setTo(node2);
		edge3.setCost(cost);
	
		node1.addEdge(edge);
		//add the new edge
		EskomLowerDistributionChain.getEdges().add((Edge<T>) edge3);
		Graph.Edge<Double> edge4 = new Graph.Edge<>();
		edge4.setFrom(node2);
		edge4.setTo(node1);
		edge4.setCost(cost);
	
		node2.addEdge(edge);
		//add the new edge
		EskomLowerDistributionChain.getEdges().add((Edge<T>) edge4);
		Graph.Edge<Double> edge5 = new Graph.Edge<>();
		edge5.setFrom(node2);
		edge5.setTo(node1);
		edge5.setCost(cost);
	
		node2.addEdge(edge);
		//add the new edge
		EskomLowerDistributionChain.getEdges().add((Edge<T>) edge5);
		Graph.Edge<Double> edge6 = new Graph.Edge<>();
		edge6.setFrom(node2);
		edge6.setTo(node1);
		edge6.setCost(cost);
		
		node2.addEdge(edge);
		EskomLowerDistributionChain.getEdges().add((Edge<T>) edge6);		
	} */
	

	/**
	 * 
	 * @param name
	 * @return
	 */
	public Vertex<T> getVertexByName(String name) {
		
		for(Vertex<T> vertex: EskomLowerDistributionChain.getVertices()) {
			if(((EskomInfrastructureEntity<T>)vertex).getInfrastructureName().equals( name)) {
				return vertex;
			}
		}
		return null;		
	}


	
	
	/**
	 * 
	 * @param chosenVertex
	 * @return
	 */
	public boolean removeVertex(Vertex<T> chosenVertex) {
		
		for(Vertex<T> vertex: EskomLowerDistributionChain.getVertices()) {
			if(vertex.equals(chosenVertex)) {
				//get Its connections
				//remove its connections
				for( Edge<T> edgeToRemove : chosenVertex.getEdges()) {					
					//remove edges 
					for(Edge<T> parellelEdge: edgeToRemove.getToVertex().getEdges()) {
						
						if(parellelEdge.getToVertex().equals(chosenVertex)) {
							edgeToRemove.getToVertex().getEdges().remove(parellelEdge);
							EskomLowerDistributionChain.getEdges().remove(parellelEdge);
							break;
						}
					}
					
					chosenVertex.getEdges().remove(edgeToRemove);
					EskomLowerDistributionChain.getEdges().remove(edgeToRemove);
				}
				
				
				//remove-it
				EskomLowerDistributionChain.getVertices().remove(chosenVertex);
				return true;
			}
		}
		return false;
	}
	
}
