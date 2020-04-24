package main;

import java.util.List;

import com.jwetherell.algorithms.data_structures.Graph;

import GUI.MainPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppExecution extends Application{

	public static void main(String[] args) {
		
		launch(args);
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		MainPane layout = new MainPane(900,700);
		Scene scene = new Scene(layout,layout.getWidth(),layout.getHeight());
		stage.setTitle("E-PathFinder");
		stage.setScene(scene);
		stage.show();
	}

}
