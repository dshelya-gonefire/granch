package granch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import granch.configuration.JpaConfiguration;


@Import(JpaConfiguration.class)
@EnableScheduling
@SpringBootApplication(scanBasePackages={"granch", "grunch.configuration"})
public class GrunchApp {

	public static void main(String[] args) {
		SpringApplication.run(GrunchApp.class, args);
	}
}
