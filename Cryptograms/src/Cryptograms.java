import java.util.Scanner;
/** 
* Author: Fernando Ruiz
* FILE: Cryptograms.java
* PURPOSE: The following  program is a textual interface of the game Cryptograms. The user is given
*          a random phrase/quote to decrypt from quote.txt. The user is prompted to enter two characters,
*          one they want to replace, and its replacement. The characters are displayed above the encryption to 
*          display the user dycryption progress. The  game is over when the user's decryption matches
*          the correct decryption of the encrypted string. Note if the user enters numbers or more than
*          one character on the line they will be ignored, only the first character will be processed
*          (if alphabetic). The program is a view that utilizes the class CryptogramController to manipulate
*          the class CryptogramModel. See other classes for more information.
* 
* @author Fernando Ruiz
* @see CryptogramController
*/

public class Cryptograms {
	
	public static void main(String[] args) {
		CryptogramController game = new CryptogramController();
		
		Scanner userInput = new Scanner(System.in);
		
		while(!game.isGameOver()) {
			//print display
			System.out.println(game.printCryptogram());
			
			//command prompt
			System.out.println("--------------------------------------------");
			System.out.println("Enter a command (type help to see commands):");
			System.out.println("--------------------------------------------");
			
			//process input
			String getCommandInfo = userInput.nextLine();
			String[] commandInfo = getCommandInfo.toLowerCase().split(" ");
			String command = commandInfo[0];
			System.out.println();
			
			//commands
			if(command.equals("replace") || commandInfo.length >=3) {
				System.out.print(game.replaceCommand(commandInfo));
			} else if(command.equals("freq")) {
				System.out.println(game.getFreq());
			} else if(command.equals("hint")) {
				System.out.println(game.getHint());
			} else if (command.equals("exit")) {
				System.out.println("Game Ended Early!");
				break;
			} else if (command.equals("help")) {
				System.out.println(game.printCommands());
			} else {
				System.out.println("Enter valid command!");
			}
			System.out.println();
		}
		
		//exited loop check if game is over 
		//because user could have used exit command
		if(game.isGameOver()) {
			System.out.println(game.printCryptogram());
			System.out.print("You got it!");
		}
		
		userInput.close();
	}
}