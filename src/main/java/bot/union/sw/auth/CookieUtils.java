package bot.union.sw.auth;

import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:secret.properties")
public class CookieUtils {

    @Value("${swu.cookie.expire}")
    private int cookieExpirations;
    @Value("${swu.cookie.domain}")
    private String cookieDomain;

    public HttpCookie generateJwtCookie(String jwtToken) {

        return ResponseCookie
                .from("acces_token", jwtToken)
                .maxAge(cookieExpirations)
                .sameSite(SameSiteCookies.STRICT.getValue())
                .httpOnly(true)
                .secure(true)
                .domain(cookieDomain)
                .path("/")
                .build();
    }

}
