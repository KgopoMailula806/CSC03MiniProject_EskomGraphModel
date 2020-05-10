package main;

import com.jwetherell.algorithms.data_structures.Graph;

import GUI.DisplayPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppExecution extends Application{

	public static void main(String[] args) {
		
			
		
		launch(args);
		
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		/**/ DisplayPane layout = new DisplayPane(900,700);
		Scene scene = new Scene(layout,900,700);
		stage.setTitle("E-PathFinder");
		stage.setScene(scene);
		stage.show();
	}

}
