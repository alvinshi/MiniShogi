package minishogi.game;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * Test for the MiniShogiImplementation
 * @author alvinshi
 *
 */
public class MiniShogiImplTest {
	private List<String> testCases = new LinkedList<>();
	private List<String> references = new LinkedList<>();
	private ByteArrayOutputStream baos;
	private PrintStream ps;
	private PrintStream old;
	
	/**
	 * Load the all path for test cases
	 */
	public void setup() {
		testCases.add("resources/test_cases/basicCheck.in");
		references.add("resources/test_cases/basicCheck.out");
		baos = new ByteArrayOutputStream();
	    ps = new PrintStream(baos);
	    old = System.out;
	    System.setOut(ps);
	}
	/**
	 * Test Basic Check
	 * @throws Exception : invalid file path
	 */
	public void test() throws Exception {
		for (int i = 0; i < testCases.size(); i++) {
			Main.runFileMode(testCases.get(i));
			String fileString = new String(Files.readAllBytes(Paths.get(references.get(i))), StandardCharsets.UTF_8);
			System.setOut(old);
			System.out.println(fileString);
			System.out.println(baos.toString());
			assertTrue(baos.toString().equals(fileString));
			baos.flush();
		}
	}
}
