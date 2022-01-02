package application.gui;

import application.Globals;
import application.SearchThreadFactory;
import application.debugger.DebuggerUI;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class GuiLayout {

	public static ComboBox<String> problemSelect = new ComboBox<>();
	public static ComboBox<String> algoSelect = new ComboBox<>();

	public static Node createGuiBasicStructure() {
		BorderPane root = new BorderPane();

		// HBox menue = new HBox();

		SplitPane aiVisualisation = new SplitPane();
		// Pane aiTreeVisualisation = new Pane();

		Pane aiTreeVisualisation = new Pane();
		Globals.treeRepresentationGraphicsContext = aiTreeVisualisation;

		Pane aiStateVisualisation = new Pane();
		Globals.stateRepresentationGraphicsContext = aiStateVisualisation;

		HBox.setHgrow(aiTreeVisualisation, Priority.ALWAYS);
		HBox.setHgrow(aiStateVisualisation, Priority.ALWAYS);

		ZoomableScrollPane left = new ZoomableScrollPane(aiTreeVisualisation);

		ZoomableScrollPane rigth = new ZoomableScrollPane(aiStateVisualisation);

		aiVisualisation.getItems().addAll(left, rigth);

		root.setCenter(aiVisualisation);

		HBox topMenue = new HBox();

		Button startButton = new Button("START");
		startButton.setOnAction(e -> {
			SearchThreadFactory.startSearchIfReady();
		});
		topMenue.getChildren().add(startButton);

		problemSelect.getItems().addAll(SearchThreadFactory.getProblemNames());
		topMenue.getChildren().add(problemSelect);
		algoSelect.getItems().addAll(SearchThreadFactory.getSearchAlgoritmNames());
		topMenue.getChildren().add(algoSelect);

		var debugg = new DebuggerUI();
		topMenue.getChildren().add(debugg.getUiElements());

		root.setTop(topMenue);

		// console layout

		var cons = debugg.getConsole();
		cons.maxHeight(300);
		cons.minHeight(100);

		root.setBottom(cons);

		return root;
	}
}
