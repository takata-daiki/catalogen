package deng.hibernate4;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class QuickTest {
	
	@Configuration
	public static class AppConfig { 
		@Bean 
		public String mybean() {
			return "My Bean";
		}
	}
		
	@Resource
	private String myBean;

	@Test
	public void testMe() {		
		assertThat(myBean, is("My Bean"));
	}
}
