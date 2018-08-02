package kr.steelheart.site.webapp;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;


@Slf4j
public class PropertySourcesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(final ConfigurableApplicationContext applicationContext) {
		ConfigurableEnvironment env = applicationContext.getEnvironment();

		String profile = env.getProperty("spring.profiles.active");

		if (StringUtils.isBlank(profile)) {
			profile = "local";
		}


		log.info("========> Active Profile = {} <========", profile);

		// PropertiesPropertySource common = new PropertiesPropertySource("common", loadProperties("/config/application.common.properties.xml"));
		// PropertiesPropertySource app = new PropertiesPropertySource("app", loadProperties("/config/application." + profile + ".properties.xml"));
		//
		// env.getPropertySources().addLast(common);
		// env.getPropertySources().addLast(app);


		Properties global = new Properties();
		InputStream p = null;

		try {
			p = new FileInputStream(new File("/tomcat/conf/application.config.properties.xml"));
			global.loadFromXML(p);
		}
		catch (IOException e) {
			log.debug("설정파일 로딩 실패: {}", e);
		}
		finally {
			IOUtils.closeQuietly(p);
		}

		env.getPropertySources().addLast(new PropertiesPropertySource("global", global));
	}


	// private Properties loadProperties(final String path) {
	// Properties properties = new Properties();
	//
	// try {
	// log.debug("설정파일 로드중: {}", path);
	// properties.loadFromXML(new ClassPathResource(path).getInputStream());
	// }
	// catch (InvalidPropertiesFormatException e) {
	// log.error("속성설정파일 포맷이 아님: {}, {}", path, e);
	// }
	// catch (IOException e) {
	// log.error("설정파일 불러오기 실패: {}, {}", path, e);
	// }
	//
	// return properties;
	// }

}
