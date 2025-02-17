package io.github.saifalhaider.nahrain.nahrain_central_api.common.util.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.web.util.WebUtils;

public class CookieUtilImpl implements CookieUtil {
    private ResponseCookie buildCookie(String name, String value, String path, long maxAge, boolean httpOnly, boolean secure) {
        return ResponseCookie.from(name, value)
                .path(path)
                .maxAge(maxAge)
                .httpOnly(httpOnly)
                .secure(secure)
                .sameSite("Lax")
                .build();
    }

    @Override
    public ResponseCookie createRegularCookie(String name, String value, String path, long maxAgeInSeconds) {
        return buildCookie(name, value, path, maxAgeInSeconds, false, false);
    }

    @Override
    public ResponseCookie createHttpOnlyCookie(String name, String value, String path, long maxAgeInSeconds) {
        return buildCookie(name, value, path, maxAgeInSeconds, true, true);
    }

    @Override
    public String getCookieByName(HttpServletRequest request, String cookieName) {
        Cookie cookie = WebUtils.getCookie(request, cookieName);
        return (cookie != null) ? cookie.getValue() : null;
    }
}
