import javafx.application.Application;
/** 
* Author: Fernando Ruiz
* FILE: Cryptograms.java
* ASSIGNMENT: Programming Assignment 4 - Cryptograms
* COURSE: CSc 335; Fall 2020;
* PURPOSE: The following  program is the main class for the game Cryptograms.
* When invoked with a command line argument of "-text", it will launch the text-oriented UI.
* When invoked with a command line argument 0f "-window" it will launch the GUI view. 
* The default will be the GUI view.
* 
* @author Fernando Ruiz
* @see CryptogramTextView
* @see CryptogramGUIView
*/
public class Cryptogram {

	public static void main(String[] args) {
		if(args.length != 0) {
			//command line argument
			if(args[0].equals("-text")) {
				Application.launch(CryptogramTextView.class, args);
			} else if (args[0].equals("-window")){
				Application.launch(CryptogramGUIView.class, args);
			}else {
				//wrong arg, but launch default anyways since no specification in spec.
				Application.launch(CryptogramGUIView.class, args);
			}
		} else {
			//default
			Application.launch(CryptogramGUIView.class, args);
		}
	}

}
 