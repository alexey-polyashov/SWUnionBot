package bot.union.sw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableAutoConfiguration
@PropertySource("classpath:secret.properties")
public class WeatherBotApp {

    public static void main(String[] args) {
        SpringApplication.run(WeatherBotApp.class);
    }

}
