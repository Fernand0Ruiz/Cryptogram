import java.util.List;

/**
* AUTHOR: Fernando Ruiz
* FILE: CryptogramController.java 
* PURPOSE: The following class constructs a CryptogramModel object, that represents
* 		   the Cryptogram game. This class provides functions for accessing and modifying
* 		   the game by calling CryptogramModel methods to control the game. This is for purpose
* 		   of being used by a view (Cryptograms.java). For more information on the modeling of 
* 		   the game Cryptograms see the class CryptogramModel. 
*
* @author Fernando Ruiz
* @see CryptogramModel
*/

public class CryptogramController {
	
	private CryptogramModel cryptogram;
	
	public CryptogramController() {
		this.cryptogram = new CryptogramModel();
	}
	
	/**
	 * The overloaded constructor is for CryptogramTest file
	 * to pass in a quote with a known answer. 
	 * 
	 * @param test is a passed in quote.
	 */
	public CryptogramController(String test) {
		this.cryptogram = new CryptogramModel(test);
	}
	
    /**
     * The following method returns a boolean which is determined by the
     * CryptogramModel game status. 
     * 
     * It calls getDecryptedString(); to
     * get the user's decryption progress in a string and compares
     * (.equals) it to the answer/quote (returned by getAnswer();)
     * of the pre-encrypted string to check
     * if the user has completed the game.
     * 
     * @return is a boolean that represents if the game is over.
     * @see CryptogramModel
     */
	public boolean isGameOver() {
		String ans = cryptogram.getAnswer();
		String userDecryptStr = cryptogram.getDecryptedString();
		if(ans.equals(userDecryptStr)) {
			return true;
		}
		return false;
	}
	
    /**
     * The following method makes a replacement to the users decryptStr and userMap.
     * 
     * 
     * The func. checks that the params are alphabetic. If so the
     * method calls the function setReplacement(); from CryptogramModel, 
     * which takes the passed in chars and uses them to replace the specified 
     * replacmentLetter char with the letterToReplace char in the user's guess 
     * decryption map and string.
     * 
     * @param letterToReplace is the encrypted char the user wants to replace.
     * 
     * @param replacementLetter is a char to replace the encryption char.
     * @see CryptogramModel
     */
	public void makeReplacement(char letterToReplace, char replacementLetter) {
		boolean checkChar1 = Character.isAlphabetic(letterToReplace);
		boolean checkChar2 = Character.isAlphabetic(replacementLetter);
		
		if(checkChar1 && checkChar2) {
			cryptogram.setReplacement(letterToReplace, replacementLetter);
		}
	}
	
    /**
     * The following method calls the function wordWrap() with the
     * CryptogramsModel function getEncryptedString(); to return a string
     * that represents the quote/answer encrypted.
     * 
     * wordWrap add newlines to the str to wrap at 80 characters.
     * All letters of the str are capitalized.
     * 
     * @return is a String that represents that encrypted string quote.
     * @see CryptogramModel
     */
	public String getEncryptedQuote() {
		return wordWrap(cryptogram.getEncryptedString(), "N/A");
	}
	

    /**
     * The following method calls the function wordWrap() with the 
     * CryptogramsModel function getDecryptedString(); to return a string
     * that represents the user's decryption progress.
     * 
     * wordWrap add newlines to the str to wrap at 80 characters.
     * All letters of the str are capitalized.
     * 
     * @return is a String the represents the user's decryption progress.
     * @see CryptogramModel
     */
	public String getUsersProgress() {
		return wordWrap(cryptogram.getDecryptedString(), "replace");
	}
	
	
    /**
     * The following method calls takes in a string and adds new lines
     * in respect to word boundaries. 
     * 
     * A newline is added at >= to 80 characters or when a word exceeds it.
     * The function use a loop and counters to determine when to add a newline, 
     * the function is used by getEncryptedQuote and getUsersProgress.If the command
     * replace is passed in then the string is a decryption and needs its unknown mappings
     * to be replaced with whitespace.
     * 
     * @return is a String that has been wrapped if >= to 80.
     */
	private String wordWrap(String cryption, String command) {
		String cryptStr = cryption;
		int lineSize = 0;
		String str = "";
		String[] cryptArr = cryptStr.split(" ");
		for(int i = 0; i < cryptArr.length; i++) {
			String word = cryptArr[i];
			int size = word.length()+1;
			if((lineSize+size)>=80) {
				str += "split";
				lineSize = 0;
				str += word+" ";
			}else {
				str += word+" ";
			}
			lineSize +=size;
		}
		
		if(command.equals("replace")) {
			str = str.replace('?', ' ');
		}
		return str;
	}
	
    /**
     * The following method returns a hint/mapping from the encryptMap
     * in CryptogramModel. 
     * 
     * @return is a String that represents a mapping from the encryption key.
     * @see CryptogramModel
     */
	public String getHint(){
		return cryptogram.getMapping();
	}
	
    /**
     * The following method returns the frequency of letters in the
     * encryptStr from the CryptogramModel. 
     * 
     * The string is 7 letters per line (4 total) in the format Letter: count.  
     * 
     * @return is a String that represents the frequency of letters in the encryption string.
     * @see CryptogramModel
     */
	public String getFreq(){
		return cryptogram.getFrequency();
	}
	
    /**
     * The following function returns a string that represents the Cryptogram display 
     * with the decryption string on top of the encryption string separated with a newline
     * if exceeding more >= 80 characters.
     * 
     * @return cryptogram is a String that represents the Cryptogram.
     * 
     */
	public String printCryptogram() {
		String progress = getUsersProgress();
		String quote = getEncryptedQuote();
		String[] progressArr = progress.split("split");
		String[] quoteArr = quote.split("split");
		int i = 0;
		String cryptogram = "";
		for(String str: progressArr) {
			cryptogram += str + "\n";
			cryptogram += quoteArr[i] + "\n";
			i++;
		}
		return cryptogram;
	}
	
    /**
     * The following method takes in a String array that consist of a command to perform
     * on the userMap in the CryptogramModel. 
     * 
     * The function call the function set replacement to perform the action. The 
     * function checks that the command format is either X = Y or replace X by Y.
     * If the command doesin't match a string indicating a invalid command is returned.
     * If the command does match the replacement is performed and empty string is returned.
     * 
     * @param commandInfo is String[] that represents a command for a char replacement.
     * 
     * @return is a string that is empty or indicates a invalid command.
     * @see CryptogramModel
     */
	public String replaceCommand(String[] commandInfo) {
		List<String> alphabet = cryptogram.getAlphabet();
		if(commandInfo.length == 4 && commandInfo[2].equals("by") &&
				alphabet.contains(commandInfo[1].toUpperCase()) && alphabet.contains(commandInfo[3].toUpperCase())){
		    makeReplacement(commandInfo[1].charAt(0), commandInfo[3].charAt(0));
		} else if(commandInfo.length == 3 && commandInfo[1].equals("=") 
				&& alphabet.contains(commandInfo[0].toUpperCase()) && alphabet.contains(commandInfo[2].toUpperCase())) {
			    makeReplacement(commandInfo[0].charAt(0), commandInfo[2].charAt(0));
		} else {
			return "Enter valid command!\n";
		}
		return "";
	}
	
    /**
     * The following method returns a string that displays a list of commands
     * for the command "help".
     * 
     * @return str is a string that displays the commands.
     */
	public String printCommands() {
		String str = "";
		str += ("|---------------------------------------------------------------------------|\n");
		str += ("|Commands:        Description:                                              |\n");
		str += ("|---------------------------------------------------------------------------|\n");
		str += ("|replace X by Y – replace letter X by letter Y in our attempted solution.   |\n");
		str += ("|X = Y          – replace letter X by letter Y in our attempted solution.   |\n");
		str += ("|freq           – Display the letter frequencies in the encrypted quotation.|\n");
		str += ("|hint           – display one correct mapping that has not yet been guessed.|\n");
		str += ("|exit           – Ends the game early.                                      |\n");
		str += ("|---------------------------------------------------------------------------|");
		return str;
	}
}
