/** 
* Author: Fernando Ruiz
* FILE: CryptogramGUIView.java
* ASSIGNMENT: Programming Assignment 4 - Cryptograms
* COURSE: CSc 335; Fall 2020;
* PURPOSE: The following  program is a graphical interface of the game Cryptograms. The user is given
* a random phrase/quote to decrypt from quote.txt, to complete the game the user must enter the 
* correct decryption letter into to the text field. Once the user has completed the puzzle they
* promted with an alert notifying them that the decryption of the encrypted quote is correct. On
* the right side of the board are the following tools : "New Puzzle" button that launches a new game,
* a "Hint" Button that performs a correct encryption mapping, a "Show Hints" check box that shows and
* un-shows the frequencies of the Cryptogram decryption letters. The program is a view that utilizes 
* the class CryptogramController to manipulate the class CryptogramModel. See other classes for more 
* information.
* 
* @author Fernando Ruiz
* @see CryptogramController
* @see CryptogramModel
*/
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CryptogramGUIView extends Application implements Observer {
	
	private CryptogramModel model = new CryptogramModel();
	private CryptogramController game = new CryptogramController(model);
	public static final BorderPane window = new BorderPane();

	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * The following starts the graphical interface 
	 * for Cryptograms.
	 * 
	 * @param stage is a Stage object.
	 */
	@Override
	public void start(Stage stage){ 
		model.addObserver(this); 
		stage.setTitle("Cryptograms");
		Scene scene = new Scene(window, 900, 400);
        GridPane cryptogram = getCryptogram(false);
		GridPane toolPane = toolPane();
        window.setCenter(cryptogram);
        window.setRight(toolPane);
        stage.setScene(scene);
        stage.show();
	}
	
	/**
	 * Returns tool GUI section with the buttons "New Puzzle", "Hint" and
	 * a "Show Hints" check box.
	 * 
	 * The following function creates a GUI tool pane for the
	 * Cryptogram game. The tool pane has the following: "New Puzzle" button
	 * that launches a new game, a "Hint" Button that performs a correct encryption
	 * mapping, a "Show Hints" check box that shows and un-shows the frequencies
	 * of the Cryptogram decryption letters. 
	 * 
	 * @returns a GridPane that represents a tool Gui section. 
	 */
	public GridPane toolPane() {
		GridPane toolPane = new GridPane();
        Button puzzleButt = new Button("New Puzzle");
        puzzleButt.setOnAction((event) -> {
        	newPuzzle();
        	});
        Button HintButt = new Button("Hint");
        HintButt.setOnAction((event) -> { 
        	addHint();
        });
        CheckBox checkBox = new CheckBox("Show Hints");
        GridPane freqPane = makeFreq();
        freqPane.setVisible(false);
        checkBox.setOnAction(event -> { 
        	freqPane.setVisible(checkBox.isSelected());
        	});
        toolPane.add(puzzleButt,0,0);
        toolPane.add(HintButt,0,1);
        toolPane.add(checkBox,0,2);
        toolPane.add(freqPane,0,3);
		return toolPane;
	}
	
	/**
	 * Creates a new puzzle, by setting the global model and
	 * game to new class instances. 
	 * 
	 * The following function creates a new puzzle by setting the 
	 * global CyrptogramModel model to a new instance and passing it
	 * to a new instance of a CryptogramController to be controlled
	 * in this view class. The new model instance is added as an observer 
	 * and the GUI Cryptogram and tool section are reset.
	 */
	public void newPuzzle() {
    	model = new CryptogramModel();
    	game = new CryptogramController(model);
    	model.addObserver(this); 
        GridPane newCryptogram = getCryptogram(false);
    	GridPane newToolPane = toolPane();
        window.setCenter(newCryptogram);
        window.setRight(newToolPane);
	}
	
	/**
	 * Adds a correct encryption mapping to the Cryptogram model using the
	 * controller. 
	 * 
	 * The following function uses the CryptogramController object game to
	 * get a correct decryption mapping from the CryptogramModel model. Using
	 * the correct mapping array values passed to the CryptogramController
	 * function .makeReplacement(); to place a correct mapping in the model.
	 */
	public void addHint() {
    	String[] hint = game.getHint();
    	if(hint[0] != null && hint[1] != null) {
        	game.makeReplacement(hint[0].charAt(0), hint[1].charAt(0));
    	}
	}
	
	/**
	 * The following function creates a GridPane object with the frequency of the
	 * encryption letters set as labels in VBoxes. 
	 * 
	 * The following function creates a GridPane object with the frequency of the
	 * encryption letters set as labels in VBoxes. The letter are put into two columns
	 * with the letters going in descending order.
	 * 
	 * @returns a GridPane object that has the letter frequencies as labels in VBoxes. 
	 */
	public GridPane makeFreq() {
		GridPane freqPane = new GridPane();
        String[] freq = game.getFreq().replaceAll("\\n", "")
        		.replaceAll(":", "").split(" ");
        int count = 0;
        for(int i = 0; i < 2; i++) {
        	for(int j = 0; j < 13; j++) {
        		VBox vbox = new VBox();
        		String offSet = "";
        		if (i == 0) {
        			offSet += "     ";
        		}
        		Label label = new Label(freq[count] +"  "+ freq[count+1] + offSet);
	        	vbox.getChildren().add(label);
	        	freqPane.add(vbox,i,j);
            	count += 2;
        	}
        }
        return freqPane;
	}
	
	
	/**
	 * The following function creates the Cryptogram GUI board as a GridPane object.
	 * 
	 * The following function creates the Cryptogram GUI board using the encryption
	 * string from the Cyrptogram Controller setting each letter as label. Using the
	 * decryption string created by the user the letter are set in the textfields. 
	 * When a textfield is keyPressed the a replacement is performed using the 
	 * CryptogramController to add or alter  a decryption mapping to the model. If
	 * the parameter status is set to True the game is over and all the textfields are
	 * set to disabled. 
	 * 
	 * @param status is a boolean that represents the status of the game.
	 * @return GridPane which represents the GUI cryptogram board.
	 */
	public GridPane getCryptogram(boolean status) { 
        GridPane gridPane  = new GridPane();
        String[][] encrypt = game.guiStr("encrypt");
        String[][] decrypt = game.guiStr("decrypt");
        for(int i = 0; i < encrypt.length; i++) {
        	String[] line  = encrypt[i][0].split("");
        	String[] line2 = decrypt[i][0].split("");
        	int count = 0;
	        for (int j = 0; j < 30; j++) {
    			TextField text = new TextField();
    			//formats to only allow one letter
    			text.setTextFormatter(new TextFormatter<String>((Change change) -> {
    			    if (change.getControlNewText().length() > 1) {
    			        return null ;
    			    }
    			    return change;
    			}));
    			VBox vbox = new VBox(text);
    			Label label = new Label();
	        	if(count < line.length) {
	        		if(Character.isLetter(line[j].charAt(0))){
	        			label = new Label(line[j]);
	        			//sets textfield to users decryption guess
	        			if (!line2[j].equals("?")){
	        				text.setText(line2[j]);
	        			}
	        		} else if (line[j].equals(" ")) {
	        			//makes whitespace at the end invisible
	        			if(count == line.length-1) {
	        				text.setVisible(false);
	        			} else {
	        				//disables whitespace between characters
	        				text.setDisable(true);
	        			}
	        		} else {
	        			//disables special chars
	        			text.setText(line[j]);
				        label = new Label(line[j]);
				        text.setDisable(true);
	        		}
	        	} else {
	        		//disables trailing whitespace 
	        		text.setVisible(false);
	        	}
	        	//game is over disable text fields
	        	if(status) {
	        		text.setDisable(true);
	        	}
	        	vbox.getChildren().add(label);
	        	//when pressed perform decryption replacment
	        	vbox.setOnKeyPressed(event ->{
	        		performReplace(vbox, text);
	        		});
	        	text.setPrefWidth(30);
	        	text.setPrefColumnCount(1);
	        	vbox.setAlignment(Pos.CENTER);
	        	text.setAlignment(Pos.CENTER);
	            gridPane.add(vbox, j, i);
	            count++;
	        }
        }
        return gridPane;
	}
	
	/**
	 * The following function performs a letter replacement on the Cryptogram model 
	 * using the CryptogramController. 
	 * 
	 * The following function uses the passed in VBox and text to perform the replacement
	 * mapping on the Cryptogram model using the CryptogramController. The text is used as
	 * the replacementLetter and the label is used as the letterToReplace. 
	 */
	public void performReplace(VBox vbox, TextField text) {
		if(0 != text.getText().length() && Character.isLetter(text.getText().charAt(0))) {
			char replacementLetter = text.getText().charAt(0);
			char letterToReplace  = ((Labeled) vbox.getChildren().get(1)).getText().charAt(0);
    		game.makeReplacement(letterToReplace, replacementLetter);
		}
	}
	
	/**
	 * The following function updates the view when the model is changed.
	 * 
	 * The following function updates the view when the model is changed.
	 * Every time a change is made the function .isGameOver() checks the status of the game.
	 * If the game is over true is passed to the getCryptogram function to disable the text fields
	 * and prompt the user that they have won with an Alert. If the user has not completed the game
	 * then the getCryptogram is still called but with false to reset the view and display the new mapping
	 * that was the product of the change to the model.
	 * 
	 * @param arg0 is a Observable object.
	 * @param arg1 is a object. 
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if(game.isGameOver()) {
			//disable text fields by sending true
	        GridPane cryptogram = getCryptogram(true);
	        window.setCenter(cryptogram);
	        //alert the user that they have won
			Alert a = new Alert(Alert.AlertType.INFORMATION);
			a.setTitle("Message");
			a.setContentText("You won!");
			a.setHeaderText("Message");
			a.showAndWait();
		} else {
			//update because new mapping was added
	        GridPane cryptogram = getCryptogram(false);
	        window.setCenter(cryptogram);
		}
	}
}