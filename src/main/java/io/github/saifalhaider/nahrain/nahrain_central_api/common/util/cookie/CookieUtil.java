package io.github.saifalhaider.nahrain.nahrain_central_api.common.util.cookie;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

public interface CookieUtil {
    ResponseCookie createRegularCookie(String name, String value, String path, long maxAgeInSeconds);

    ResponseCookie createHttpOnlyCookie(String name, String value, String path, long maxAgeInSeconds);

    String getCookieByName(HttpServletRequest request, String cookieName);
}
