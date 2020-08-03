package GUI;

import java.util.ArrayList;
import java.util.List;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

import EskomClasses.EskomInfrastructureEntity;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
/**
 * 
 * @author 218004702 Kgopo Mailula
 *
 */
public class DisplayPane extends StackPane{

	private GridPane gridDisplay = new GridPane();
	//private Accordion actionsAccordion;	
	private HBox mainHBox;
	private int hight;
	private int width;
	
	private TextField facilityNameTXTFld;
	private Label facilityNamelbl;
	private ChoiceBox<String> facilityTypeCHBox;
	private Button AddVertexBTN;
	private VBox addFacilityHbox;
	
	private GraphInteractions<Double> GraphInteractions;
	private Label stationCapacityNamelbl;
	private TextField stationCapacityTXTFld;
	private Label facilityTypelbl;
	
	private Double stationCapacity = 0.0;
	private VBox removeFacilityHbox;
	
	private VBox componentVBox;
	private TextArea textArea;
	private ArrayList<String> pathVertices = new ArrayList<String>(); 
	private Canvas GraphPlane = new Canvas();
	private boolean alreadyaddedForPathFinding = false;
	private boolean alreadyaddedForConnecting = false;
	private ChoiceBox<String> verticesToRemove = new ChoiceBox<String>();
	private VBox connectFacility = new VBox(10);
	private Button establishLinkVertexBTN = new Button("Nodes");
	private VBox tepBox = new VBox(10);
	private Button findBTN = new Button("Get nodes");
	/**
	 * 
	 */	
	public DisplayPane(int width, int hight) {
		//Set up GUI
		
		//SetUp graph interactions class 
		this.GraphInteractions = new GraphInteractions<Double>(new Graph<Double>(Graph.TYPE.UNDIRECTED));
				
		this.hight = hight;
		this.width = width;
		
		this.setHeight(hight);
		this.setWidth(width);		
		this.setPadding(new Insets(10,10,10,10));
		
		mainHBox = new HBox(10);
		
		// VBox For controls
		
		//Adding vertices------------------------------------------------------------------------------------------------------------------
		Label addDivisionlbl = new Label("Add New Facility (Vertex)");
		addDivisionlbl.setFont(Font.font ("Verdana", 20));
		this.facilityNamelbl = new Label("Facility Name");
		this.facilityNameTXTFld = new TextField();
		this.stationCapacityNamelbl = new Label("Station Voltage Capacity");
		this.stationCapacityTXTFld = new TextField();
		
		//Choice Box (facility Type)
		this.facilityTypelbl = new Label("Facility Type");
		this.facilityTypeCHBox = new ChoiceBox<String>();
		//set choices 
		facilityTypeCHBox.getItems().add("SUBSTATION");
		facilityTypeCHBox.getItems().add("ZONE DISTRIBUTION MAIN");
		facilityTypeCHBox.getItems().add("STREET DISTRIBUTION MAIN");
		facilityTypeCHBox.getItems().add("HQ");
		facilityTypeCHBox.getSelectionModel().select(0);
			
		Separator addDivisionSep = new Separator(Orientation.HORIZONTAL);
		this.AddVertexBTN = new Button("add");	
		this.addFacilityHbox = new VBox(10);
		this.addFacilityHbox.getChildren().addAll(addDivisionlbl,facilityNamelbl,facilityNameTXTFld,stationCapacityNamelbl,stationCapacityTXTFld,facilityTypelbl,facilityTypeCHBox,AddVertexBTN,addDivisionSep);
				
		
		this.gridDisplay.setPrefSize(hight, width);
		textArea = new TextArea();
		
		AddVertexBTN.setOnAction(e ->{
			//make the vertex 
			String stationType = facilityTypeCHBox.getValue();
			String StationName = facilityNameTXTFld.getText();
			String testText = stationCapacityTXTFld.getText();					
			
			try//value
			{
				stationCapacity = Double.parseDouble(testText);
				//Create a EskomInfrastructureEntity : vertex<Double>
				EskomInfrastructureEntity<Double> infrastructure = new EskomInfrastructureEntity<>(StationName,stationType,stationCapacity);
				//Insert into the Graph
				GraphInteractions.insertNewInfratructure(infrastructure);
				
				// create a Confirmation alert 		
		        Alert a = new Alert(AlertType.CONFIRMATION);
		        a.setContentText("Infrastructure Component recorded");
		        a.show();
		        
		        verticesToRemove.getItems().add(StationName);
		        print(verticesToRemove,null);
		        connectaction(establishLinkVertexBTN,connectFacility);
				
			}		
			catch(Exception exception) 
			{
				// create an error alert 
		        Alert a = new Alert(AlertType.ERROR);
		        a.setContentText("please enter a number for volatage field");
		        a.show();
			}finally {
				stationCapacity = 0.0;
			}
			
		});
		
		
		//connect components ------------------------------------------------------------------------------------------------------------------
		Label connectNodesDivisionlbl = new Label("Connect Facilities");
		connectNodesDivisionlbl.setFont(Font.font ("Verdana", 20));
		Separator connectNodesDivisionSep = new Separator(Orientation.HORIZONTAL);
		
		
		connectFacility.getChildren().addAll(connectNodesDivisionlbl,establishLinkVertexBTN);
				
		connectaction(establishLinkVertexBTN,connectFacility);
		pathFinding(findBTN,tepBox);
		
		
		// removing a facility------------------------------------------------------------------------------------------------------------
		Label removeDivisionlbl = new Label("remove Facility (Vertex)");
		removeDivisionlbl.setFont(Font.font ("Verdana", 20));
		Separator removeDivisionSep = new Separator(Orientation.HORIZONTAL);
						
		//Get the facility (Vertex) 						
		print(verticesToRemove,null);
				
		Button removeVertex = new Button("Remove") ;
		
		//specify action 
		removeVertex.setOnAction(e->{
			
			//get the vertex to remove identify			
			EskomInfrastructureEntity<Double> chosenVertex = (EskomInfrastructureEntity<Double>) this.GraphInteractions.getVertexByName(verticesToRemove.getValue());
			if(chosenVertex == null) {
				Alert a = new Alert(AlertType.ERROR);
		        a.setContentText("No vertex has been chosen");
		        a.show();
			}else if(this.GraphInteractions.removeVertex(chosenVertex)) {
				Alert a = new Alert(AlertType.CONFIRMATION);
		        a.setContentText("Facility removed");
		        a.show();
			}
			
			//reload everything 
			print(verticesToRemove,null);
		});
		
		this.removeFacilityHbox = new VBox(10);
		this.removeFacilityHbox.getChildren().addAll(removeDivisionSep,removeDivisionlbl,verticesToRemove,removeVertex);
		
		
		
		//Path finding------------------------------------------------------------------------------------------------------------------------
		Label pathFindingDivisionlbl = new Label("Path finding");
		pathFindingDivisionlbl.setFont(Font.font ("Verdana", 20));
		
		tepBox.getChildren().addAll(pathFindingDivisionlbl,findBTN);
		Separator pathFindingDivisionSep = new Separator(Orientation.HORIZONTAL);
		
		componentVBox = new VBox(10);
		componentVBox.getChildren().addAll(addFacilityHbox,connectFacility,tepBox,removeFacilityHbox);
		
		VBox rigthBox = new VBox(10);
		this.mainHBox.getChildren().addAll(componentVBox,textArea);
				
		pathFinding(findBTN,tepBox);
		
		/*
		
		//GraphInteractions.asstablishPathToFrom(from, to, 121); 
		ArrayList<Vertex<Double>> pathTaken = GraphInteractions.DijkstrasAlgorithm(((EskomInfrastructureEntity<Double>) GraphInteractions.getGraph().getVertices().get(0)), 
											((EskomInfrastructureEntity<Double>) GraphInteractions.getGraph().getVertices().get(5)));
				
		print(null,pathTaken);
		
		GraphInteractions.printGraph();
		*/
		this.getChildren().addAll(mainHBox);
	}
	
	private void pathFinding(Button findBTN, VBox tepBox) {
		findBTN.setOnAction(e -> {
			
			HBox choiceBoxes = new HBox(10);
			ChoiceBox<String> startChoices = new ChoiceBox<>();					
			ChoiceBox<String> goalChoices = new ChoiceBox<>();		
			Button Search = new Button("Search");

			//get all the graphs vertices(EskomInfrastructureEntity)
			List<Vertex<Double>> vertices = GraphInteractions.getGraph().getVertices();
			
			ArrayList<Vertex<Double>> potentialStarts = new ArrayList<>();
			ArrayList<Vertex<Double>> potentialGoals = new ArrayList<>();
			
			int i = 0;
			boolean switchUp = true;
			for(Vertex<Double> node : vertices) {
				if(((EskomInfrastructureEntity<Double>)node).getInfrastructuterType().equals("HQ")) {
					startChoices.getItems().add(((EskomInfrastructureEntity<Double>)node).getInfrastructureName());
					((EskomInfrastructureEntity<Double>)node).Index = i;
					potentialStarts.add(node);
					pathVertices.add(((EskomInfrastructureEntity<Double>)node).getInfrastructureName());
					i++;
					switchUp =false;
				}else{
					goalChoices.getItems().add(((EskomInfrastructureEntity<Double>)node).getInfrastructureName());
					((EskomInfrastructureEntity<Double>)node).Index = i;
					potentialStarts.add(node);
					pathVertices.add(((EskomInfrastructureEntity<Double>)node).getInfrastructureName());
					i++;
					switchUp = true;
				}
			}
			
			//commence path finding
			Search.setOnAction(searchAction->{
				//get the celected vertices
				
				//Get the selected nodes respective indices		
				if(startChoices.getValue() != null && goalChoices.getValue() != null) {
					
					
					ArrayList<Vertex<Double>> pathTaken = GraphInteractions.DijkstrasAlgorithm(((EskomInfrastructureEntity<Double>) GraphInteractions.getVertexByName(startChoices.getValue())), 
							((EskomInfrastructureEntity<Double>) GraphInteractions.getVertexByName(goalChoices.getValue())));
					print(null,pathTaken);
					try {
						
					}catch (NullPointerException exNull) {
						Alert a = new Alert(AlertType.ERROR);
				        a.setContentText("There is no link between the facilities");
				        a.show();
					}
					
				}else{
					Alert a = new Alert(AlertType.ERROR);
			        a.setContentText("Please schoose start and goal for traversal");
			        a.show();
				}
				
			});
						
			if(!alreadyaddedForPathFinding) {
				choiceBoxes.getChildren().addAll(startChoices,goalChoices);
				
				tepBox.getChildren().addAll(choiceBoxes,Search);
				alreadyaddedForPathFinding = true;
			}
			
		});
		
	}

	private void connectaction(Button establishLinkVertexBTN, VBox connectFacility) {
			establishLinkVertexBTN .setOnAction(e -> {
			
			HBox choiceBoxes = new HBox(10);
			ChoiceBox<String> startChoices = new ChoiceBox<>();					
			ChoiceBox<String> goalChoices = new ChoiceBox<>();		
			Label distanceLbl = new Label("Distance");
			TextField distanceTXTFld = new TextField();
			Button connect = new Button("connect");
			
			
			//get all the graphs vertices(EskomInfrastructureEntity)
			List<Vertex<Double>> vertices = GraphInteractions.getGraph().getVertices();
			
			ArrayList<Vertex<Double>> potentialStarts = new ArrayList<>();
			ArrayList<Vertex<Double>> potentialGoals = new ArrayList<>();
			
			int i = 0;
			boolean switchUp = true;
			for(Vertex<Double> node : vertices) {
					startChoices.getItems().add(((EskomInfrastructureEntity<Double>)node).getInfrastructureName());
					((EskomInfrastructureEntity<Double>)node).Index = i;
					potentialStarts.add(node);
					pathVertices.add(((EskomInfrastructureEntity<Double>)node).getInfrastructureName());				
				
					goalChoices.getItems().add(((EskomInfrastructureEntity<Double>)node).getInfrastructureName());
					i++;
					((EskomInfrastructureEntity<Double>)node).Index = i;
					potentialStarts.add(node);
					pathVertices.add(((EskomInfrastructureEntity<Double>)node).getInfrastructureName());
				
			}
			
			//commence path finding
			connect.setOnAction(searchAction->{
				//get the celected vertices
				
				//Get the selected nodes respective indices		
				try//value
				{
				
					//get the 
					int cost = Integer.parseInt((distanceTXTFld.getText()));
					
					if(startChoices.getValue() != null && goalChoices.getValue() != null ) {
						//establish the path
						GraphInteractions.asstablishPathToFrom((((EskomInfrastructureEntity<Double>) GraphInteractions.getVertexByName(startChoices.getValue()))), 
								((EskomInfrastructureEntity<Double>) GraphInteractions.getVertexByName(goalChoices.getValue())),cost);
						
						// create an Confirmation alert 
				        Alert a = new Alert(AlertType.CONFIRMATION);
				        a.setContentText("Facilities hav been linked");
				        a.show();
					}else{
						Alert a = new Alert(AlertType.ERROR);
				        a.setContentText("Please choose start and goal for traversal");
				        a.show();
					}
				}		
				catch(Exception exception) 
				{
					// create an error alert 
			        Alert a = new Alert(AlertType.ERROR);
			        a.setContentText("please enter the distance");
			        a.show();
			     
				}finally {
					stationCapacity = 0.0;
				}				
				
			});
						
			if(!alreadyaddedForConnecting) {
				choiceBoxes.getChildren().addAll(startChoices,goalChoices);
				
				connectFacility.getChildren().addAll(choiceBoxes,distanceLbl,distanceTXTFld,connect);
				alreadyaddedForConnecting = true;
			}

		});

		
	}

	/**
	 * @param verticesToRemove
	 * @param pathTaken
	 */
	public void print(ChoiceBox<String> verticesToRemove, List<Vertex<Double>> pathTaken) {
		
		for(Vertex<Double> EskomInfrastructure : this.GraphInteractions.getGraph().getVertices()) {
			System.out.println(((EskomInfrastructureEntity<Double>)EskomInfrastructure).toString());
		}
		//print the path
		if(pathTaken != null) {
			textArea.setText("");
			for(Vertex<Double> node : pathTaken) {				
				textArea.appendText(((EskomInfrastructureEntity<Double>) node).toString());
			}
		}
		
		//
		if(verticesToRemove != null) {
			int index = 0;
			verticesToRemove.getItems().clear();
			for(Vertex<Double> vertex: GraphInteractions.getGraph().getVertices()) {
							
				verticesToRemove.getItems().add(((EskomInfrastructureEntity<Double>)vertex).getInfrastructureName());
				//set Index
				((EskomInfrastructureEntity<Double>)vertex).Index = index;
				index++;
				
			}
		}
		
	}
	
}
