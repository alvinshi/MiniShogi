package minishogi.core;

import org.junit.Test;

import minishogi.utils.Utils;
import minishogi.utils.Utils.TestCase;

/**
 * Test for the MiniShogiImplementation
 * @author alvinshi
 *
 */
public class MiniShogiImplTest {
	/**
	 * Test Basic Check
	 * @throws Exception : invalid file path
	 */
	@Test
	public void testBasicCheck() throws Exception {
		TestCase tc = Utils.parseTestCase("resources/test_cases/basicCheck.in");
		MiniShogiImpl game = new MiniShogiImpl(tc);
	}
}
