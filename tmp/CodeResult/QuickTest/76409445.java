package deng.jdk;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class QuickTest {
	@Test
	public void testSplit() throws Exception {
		assertThat("".split(" "), arrayContaining("")); // This return 1 item!
		assertThat(" ".split(" "), arrayWithSize(0)); // This return 0 item!
	}
}
