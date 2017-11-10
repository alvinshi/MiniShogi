package minishogi.game;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
	private int count = 0;
	private static final int NON_SPECIAL_CASES = 53;
	
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
		baos = new ByteArrayOutputStream();
	    ps = new PrintStream(baos);
	    System.setOut(ps);
	}
	/**
	 * Test Basic Check
	 * @throws Exception : invalid file path
	 */
	@Test
	public void test() throws Exception {
		List<String> specialCases = new ArrayList<>();
		for (int i = 0; i < testCases.size(); i++) {
			String testCase = testCases.get(i);
			Main.runFileMode(testCase);
			String reference = testCase.substring(0, testCase.length() - 2) + "out";
			String fileString = new String(Files.readAllBytes(Paths.get(reference)), StandardCharsets.UTF_8);
			if (fileString.equals(baos.toString())) {
				count++;
			}
			else {
				specialCases.add(testCase);
			}
			baos.reset();
		}
	    System.setOut(System.out);
		assertTrue(count == NON_SPECIAL_CASES);
	}
}
