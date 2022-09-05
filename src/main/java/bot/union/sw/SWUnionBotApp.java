package bot.union.sw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@EnableAutoConfiguration
@PropertySource("classpath:secret.properties")
public class SWUnionBotApp {

    public static void main(String[] args) {
        SpringApplication.run(SWUnionBotApp.class);



    }

}
