package com.linker.tower;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.core.env.Environment;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import utils.DefaultProfileUtil;

import java.net.InetAddress;

@Slf4j
@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories(basePackages = {"com.linker.tower.repository","**.repository"})
@EnableAutoConfiguration(exclude={DruidDataSourceAutoConfigure.class, DataSourceAutoConfiguration.class})
public class LinkerTowerApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(LinkerTowerApplication.class);

		DefaultProfileUtil.addDefaultProfile(app);
		Environment env = app.run(args).getEnvironment();
		String protocol = "http";
		String hostAddress = "localhost";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			log.warn("The host name could not be determined, using `localhost` as fallback");
		}
		log.info("\n----------------------------------------------------------\n\t" +
						"Application '{}' is running! Access URLs:\n\t" +
						"Local: \t\t{}://localhost:{}\n\t" +
						"External: \t{}://{}:{}\n\t" +
						"Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				protocol,
				env.getProperty("server.port"),
				protocol,
				hostAddress,
				env.getProperty("server.port"),
				env.getActiveProfiles());
	}
}
