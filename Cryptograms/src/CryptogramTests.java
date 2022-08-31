
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** 
* Author: Fernando Ruiz
* FILE: CryptogramTests.java
* PURPOSE: The following test the CryptogramController and CryptogramModel.
* 
* @author Fernando Ruiz
* @see CryptogramController
* @see CryptogramModel
* 
*/
class CryptogramTests {

    /**
     * Tests if game is not over, should be since game is empty.
     */
	@Test
	void testGameOver() {
		CryptogramController game = new CryptogramController();
		//game empty should not be over
		assertFalse(game.isGameOver());
	}
	
    /**
     * Tests if after the encryption and decryption string are working
     * by making correctly placement and checking if they equal 
     * each other. Calls overloaded constructor to test.
     * @see CryptogramController(String test)
     */
	@Test
	void testEncryptAndDecryptStr() {
		CryptogramController game = new CryptogramController("A . ");
		String encryption = game.getEncryptedQuote();
		game.makeReplacement(encryption.charAt(0),'a');
		assertTrue(game.getUsersProgress().equals("A . "));
	}
	
    /**
     * Tests hint function on two conditions before and after an incorrect placement.
     * Also tests makeReplacement with different letter casing. Calls overloaded
     * constructor to test.
     * @see CryptogramController(String test)
     */
	@Test
	void testHint() {
		CryptogramController game = new CryptogramController("A-TEST");
		String encryption = game.getEncryptedQuote();
		//check if hint is first mapping
		assertTrue(game.getHint().equals("Hint: "+encryption.charAt(0)+ " = " + "A"));
		//replace with incorrect mapping
		game.makeReplacement(encryption.charAt(0),'z');
		//check if hint remains the same on incorrect mapping
		assertTrue(game.getHint().equals("Hint: "+encryption.charAt(0)+ " = " + "A"));
		//correct makeReplacements, test letter casing
		game.makeReplacement(encryption.charAt(0),'a');
		game.makeReplacement(encryption.charAt(2),'T');
		game.makeReplacement(encryption.charAt(3),'e');
		game.makeReplacement(encryption.charAt(4),'S');
		game.makeReplacement(encryption.charAt(5),'t');
		//game must be over.
		assertTrue(game.isGameOver());
	}
	
    /**
     * Tests replaceCommand() given commands to finish the game. Calls overloaded
     * constructor to test.
     * @see CryptogramController(String test)
     */
	@Test
	void testReplace() {
		CryptogramController game = new CryptogramController("FUN");
		String[] encryption = game.getEncryptedQuote().split("");
		
		//check replace short cut command
		String[] commandInfo = {"?","=","F"};
		commandInfo[0] = encryption[0];
		game.replaceCommand(commandInfo);
		
		//check replace command
		String[] commandInfo2 = {"replace","?","by","u"};
		commandInfo2[1] = encryption[1];
		game.replaceCommand(commandInfo2);
		
		//no command replacement
		game.makeReplacement(encryption[2].charAt(0),'n');
		
		//check invalid command
		String[] commandInfo3 = {"replace","x","=","a"};
		game.replaceCommand(commandInfo3);
		
		//game is over if replacements worked
		assertTrue(game.isGameOver());
	}
	
    /**
     * Tests that the print functions are being populated. Also tests that a long
     * Quote can be processed.
     * @see CryptogramController(String test)
     */
	@Test
	void testPrints() {
		CryptogramController game = new CryptogramController("Always code as if the guy who ends up maintaining your"
				+ " code will be a violent psychopath who knows where you live - John Woods");
		//check that strings have been populated.
		assertFalse(game.printCommands().equals(""));
		//does not equal empty so quote was wrapped.
		assertFalse(game.printCryptogram().equals(""));
		//freq
		assertFalse(game.getFreq().equals(""));

	}

}
