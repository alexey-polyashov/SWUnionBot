package bot.union.sw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@EnableAutoConfiguration
@PropertySource("classpath:secret.properties")
@EnableScheduling
public class SWUnionBotApp {

    public static void main(String[] args) {
        SpringApplication.run(SWUnionBotApp.class);
    }


}
