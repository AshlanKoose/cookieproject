import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.*;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.lang.*;
/**
 * JUnit Tests for Final2
 */
public class Final2Test {
	private InputStream originalSystemIn = System.in;
	private ByteArrayInputStream testIn;
	/**
	 * tests "yes" keyboard input
	 */
	@Test 
	public void KeyboardInput1(){
		String simulatedInput = "Yes";
		provideInput(simulatedInput);
		Scanner scanner = new Scanner(System.in);
		String userInput = scanner.nextLine();
		scanner.close();
		assertEquals("Yes", userInput, "Yes is valid input");
	}
	/**
	 * tests "no" keyboard input
	 */
	@Test 
	public void KeyboardInput2(){
		String simulatedInput = "No";
		provideInput(simulatedInput);
		Scanner scanner = new Scanner(System.in);
		String userInput = scanner.nextLine();
		scanner.close();
		assertEquals("No", userInput, "No is valid input");
	}
	/**
	 * tests other keyboard input
	 */
	@Test 
	public void KeyboardInput3(){
		String simulatedInput = "sdfgh";
		provideInput(simulatedInput);
		Scanner scanner = new Scanner(System.in);
		String userInput = scanner.nextLine();
		scanner.close();
		assertNotEquals("Yes/no", userInput, "Input should be yes or no");
	}
	//private helper method for test cases
	private void provideInput(String data){
		testIn = new ByteArrayInputStream(data.getBytes());
		System.setIn(testIn);
	}
}