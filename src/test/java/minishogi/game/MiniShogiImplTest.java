package minishogi.game;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for the MiniShogiImplementation
 * @author alvinshi
 *
 */
public class MiniShogiImplTest {
	private static final String FILE_DIRECTORY = "resources/test_cases/";
	private List<String> testCases = new LinkedList<>();
	private ByteArrayOutputStream baos;
	private PrintStream ps;
	private PrintStream old;
	
	/**
	 * Load the all path for test cases
	 */
	@Before
	public void setup() {
		File dir = new File(FILE_DIRECTORY);
		File[] files = dir.listFiles();
		for (File f : files) {
			if (f.getName().indexOf(".in") != -1) {
				testCases.add(f.getPath());
			}
		}
		//Set the system out to the actual string
		baos = new ByteArrayOutputStream();
	    ps = new PrintStream(baos);
	    old = System.out;
	    System.setOut(ps);
	}
	/**
	 * Test Basic Check
	 * @throws Exception : invalid file path
	 */
	@Test
	public void test() throws Exception {
		for (int i = 0; i < testCases.size(); i++) {
			String testCase = testCases.get(i);
			Main.runFileMode(testCase);
			String reference = testCase.substring(0, testCase.length() - 2) + "out";
			String fileString = new String(Files.readAllBytes(Paths.get(reference)), StandardCharsets.UTF_8);
			assertTrue(fileString.equals(baos.toString()));
			baos.reset();
		}
		System.setOut(old);
	}
}
