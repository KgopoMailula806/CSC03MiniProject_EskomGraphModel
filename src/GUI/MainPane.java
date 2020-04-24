package GUI;


import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
/**
 * 
 * @author 218004702 Kgopo Mailula
 *
 */
public class MainPane extends StackPane{

	private GridPane gridDisplay = new GridPane();
	/**
	 * 
	 */
	public MainPane(int width, int hight) {
		this.setHeight(hight);
		this.setWidth(width);

		gridDisplay = new GridPane();
		
		this.setPadding(new Insets(10,10,10,10));
		
		
		Label newLable = new Label("Some Label");
		
		GridPane.setConstraints(newLable,1,4);
		
		gridDisplay.getChildren().add(newLable);
		this.getChildren().addAll(gridDisplay);
	}
}
