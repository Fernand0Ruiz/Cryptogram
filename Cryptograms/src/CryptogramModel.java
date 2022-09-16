/**
* AUTHOR: Fernando Ruiz
* FILE: CryptogramModel.java
* ASSIGNMENT: Programming Assignment 4 - Cryptograms
* COURSE: CSc 335; Fall 2020;
* PURPOSE: The following class represents the Cryptogram game, it uses the file quotes.txt
* 		   to select a random quote for the user to decrypt (answer). It uses a HashMap
* 		   of the Alphabet mapped to shuffled letters to decrypt the quote/answer (encryptMap).
* 		   The users progress to decrypt the encrypted quote is stored in a HashMap (userMap). Both the
* 		   encryption and user decryption hash maps are processed into string for the user to see (encrytStr
* 		   and decryptStr). This class serves as a model for the game,it is controlled by the class CryptogramController.
* 
* @author Fernando Ruiz
* @see CryptogramController
* @see CryptogramModel
* 
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

public class CryptogramModel extends Observable {

	private String answer;
	private ArrayMap<String, String> userMap;
	private ArrayMap<String, String> encryptMap;
	private String encryptStr;
	private String decryptStr;
	private String encryptFreq;
	
	/**
	 * Default constructor.
	 */
	public CryptogramModel() {
		this.answer     = chooseQuote();
		this.userMap    = new ArrayMap<>();
		this.encryptMap = createEncryptMap();
		this.encryptStr = makeEncryptStr();
		this.decryptStr = makeDecryptStr();
		this.encryptFreq = createFreq();
	}
	
	/**
	 * The following func. checks that the params are alphabetic. 
	 * 
	 * If so the func. adds the encrypted char as the key and the replacement char as the value. 
	 * If the key exists the value is replaced, if not it is added. 
	 * 
	 * @param encryptedChar the encrypted char that represents the char to replace.
	 * @param replacementChar the char to replace the encryptedChar with.
	 */
	public void setReplacement(char encryptedChar, char replacementChar) {
		boolean checkChar1 = Character.isAlphabetic(encryptedChar);
		boolean checkChar2 = Character.isAlphabetic(replacementChar);
		if(checkChar1 && checkChar2) {
			this.userMap.put((encryptedChar+"").toUpperCase(), (replacementChar+"").toUpperCase());
		}
		decryptStr = makeDecryptStr();
		setChanged();
		notifyObservers();
	}
	
	
	/**
	 * The following function returns a string that represents the
	 * encrypted version of the quote/answer.
	 * 
	 * @return encryptStr a string that represents the quote encrypted.
	 */
	public String getEncryptedString() {
		return encryptStr;
	}
	
	/**
	 * The following function returns a string that represents the
	 * user's decryption progress in a string.
	 * 
	 * @return decryptStr a string that represents the user's decryption.
	 */
	public String getDecryptedString() {
		return decryptStr;
	}
	
	/**
	 * The following function returns a string that is the randomly chosen quote,
	 * therefore the answer to the Cryptogram.
	 * 
	 * @return answer a string that represents a quote from quotes.txt.
	 */
	public String getAnswer() {
		return answer;
	}
	
	/**
	 * The following function returns a string that represents the
	 * frequency of letters in the encryption string (encryptStr).
	 * 
	 * @return encryptFreq a string that represents encryption string frequency.
	 */
	public String getFrequency() {
		return this.encryptFreq;
	}
	
	/**
	 * The following function returns a string that is a quote from the file quotes.txt
	 * to use as the instance of the answer for the game. 
	 * 
	 * The func. opens the file, adds the lines to an array list, and uses in built func.
	 * math.random to generate a random number to select a random quote from the array.
	 * The quote is returned capitalized.
	 *  
	 * @throws FileNotFoundException if file not found.
	 * @return quote is a string from quotes.txt. 
	 */
	private String chooseQuote() {
		//open hard-coded file
		Scanner quotesTxt = null;
		
		try {
			quotesTxt = new Scanner(new File("quotes.txt"));
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//make list of quotes
		ArrayList<String> quotesList = new ArrayList<>();
		
		int countQuotes = 0;
		while(quotesTxt.hasNextLine()) {
			String quote = quotesTxt.nextLine();
			quotesList.add(quote);
			countQuotes++;
		}
		
		//get random quote from quotesList
		int randNum = (int) (Math.random() * countQuotes) ;
		String quote = quotesList.get(randNum).toUpperCase();
		
		quotesTxt.close();
		
		return quote;
	}
	
	
	/** 
	 * The following function returns a hash map that maps the Alphabet to a random/shuffled 
	 * letter to create an encryption. 
	 * 
	 * The func. creates the Alphabet by calling getAlphabet().
	 * The func. then copys the alphabet list into another list
	 * that is used with the inbuilt function Collection.shuffle. The contents of both array
	 * lists are then mapped to each other to create the encryptMap. All letters are capitalized.
	 * 
	 * @return encryptMap is a HashMap that maps the alphabet to a shuffled alphabet (encryption).
	 * 					  
	 */
	private ArrayMap<String, String> createEncryptMap() {
		//make alphabet list
		List<String> alphabet = getAlphabet();
		
		//make shuffled ver. of alphabet
		List<String> shuffAlpha = new ArrayList<>(alphabet);
		Collections.shuffle(shuffAlpha);
		
		//create encryption map (alpha --> shuffled alpha)
		ArrayMap<String, String> encryptMap =  new ArrayMap<>();
		
		//create encryption map (alpha --> shuffled alpha)
		for(int i = 0; i < alphabet.size(); i++) {
			String str1 = alphabet.get(i);
			for(int j = 0; j < shuffAlpha.size(); j++) {
				String str2 = shuffAlpha.get(j);
				if(!str1.equals(str2)){
					encryptMap.put(str1, str2);
					shuffAlpha.remove(j);
					break;
				}
			}
		}
		
		return encryptMap;
	}
	
	/**
	 * The following func. returns the encryption
	 * string. 
	 * 
	 * It takes the chars from the answer/quote
	 * and uses them for the key to access the encrypted str
	 * in encryptMap to encrypt each char one at a time in a new str. 
	 * All special characters are ignored and everything is capitalized.
	 *  
	 * @return str is a string that represents the quote encrypted.
	 */
	private String makeEncryptStr() {
		String str= "";
		for(int i = 0; i < answer.length(); i++) {
			char c = answer.charAt(i);
			if (Character.isAlphabetic(c) ) {
				str += encryptMap.get(c + "");
			} else {
				str += c + "";
			}
		}
		return str;
	}
	
	/**
	 * The following function returns the decryption string.
	 * 
	 * It take the chars from the encryptStr and replaces them if they are a key in 
	 * the userMap.If not char remains the same. All special characters are ignored
	 * and everything is capitalized. 
	 * 
	 * @return str is a string that represents the user's decryption.
	 */
	private String makeDecryptStr() {
		String str = "";
		for(int i = 0; i < encryptStr.length(); i++) {
			char c = encryptStr.charAt(i);
			String value = userMap.get(c + "");
			if (!Character.isAlphabetic(c)) {
				str += c + "";
			} else if (value != null) {
				str += value;
			} else {
				str += "?";
			}
		}
		return str;
	}
	
	/**
	 * The following function returns a correct key mapping of the encryption map.
	 * 
	 * It loops through the answer, encryptMap and userMap to return a mapping
	 * that is either not in the userMap or is mapped incorrectly.
	 * 
	 * @return hint[] is a string array of a correct mapping.
	 */
	public String[] getMapping() {
		String[] answer = this.answer.split("");
		String hint[] = new String[2];
		for(int i = 0; i < answer.length; i++) {
			String letter = answer[i];
			if(Character.isLetter(letter.charAt(0))){
				String ans = encryptMap.get(letter);
				if(userMap.containsKey(ans)) {
					String guess = userMap.get(ans);
					if(!guess.equals(letter)) {
						hint[0] = ans;
						hint[1] = letter;
						break;
					}
				} else {
					hint[0] = ans;
					hint[1] = letter;
					break;
				}
			}
		}
		return hint;
	}
	
	/**
	 * The following function creates a string representation of the frequency
	 * of letters in the encryption string. 
	 * 
	 * The string is 7 letters per line (4 total) in the format Letter: count. The 
	 * function first counts occurrences and adds the letter and its count to a 
	 * HashMap (freq), which is used to make string in the format mentioned above.
	 * 
	 * @return str is a string that represents the frequency of letters in encryptStr.
	 */
	private String createFreq() {
		//count occurrences
		List<String> alphabet = getAlphabet();
		String quote = this.encryptStr;
		ArrayMap<String, Integer> freq =  new ArrayMap<>();
		for(char c: quote.toCharArray()) {
			String letter = (c +"");
			if(freq.containsKey(letter)) {
				int count = freq.get(letter);
				freq.put(letter, count+1);
			} else {
				freq.put(letter, 1);
			}
		}
		
		//create frequency string
		String str = "";
		int n = 1;
		for(int i = 0; i < 26; i++) {
			String letter = alphabet.get(i);
			if(freq.containsKey(letter)) {
				int count = freq.get(letter);
				str += letter+": "+ count + " ";
			} else {
				str += letter+": "+ "0" + " ";
			}
			//add newline to get 7 or less letters per line
			if(n%7==0) {
				str +="\n";
				n = 0;
			}
			n++;
		}
		return str;
	}
	
	/**
	 * The following function creates the Alphabet using unicode,
	 *storing each letter in an arraylist. Letters are capitalized.
	 * 
	 * @return alphabet is a ArrayList of all the 26 letters in the alphabet capitalized.
	 */
	public List<String> getAlphabet(){
		List<String> alphabet = new ArrayList<>();
		for(int i = 0; i < 26; i++){
			char letter = (char) ('A' + i);
			alphabet.add(letter + "");
		}
		return alphabet;
	}
	
}