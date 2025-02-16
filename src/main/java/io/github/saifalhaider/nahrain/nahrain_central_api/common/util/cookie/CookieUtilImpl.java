package io.github.saifalhaider.nahrain.nahrain_central_api.common.util.cookie;

import org.springframework.http.ResponseCookie;

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
    public ResponseCookie createRegularCookie(String name, String value, String path, long maxAge) {
        return buildCookie(name, value, path, maxAge, false, false);
    }

    @Override
    public ResponseCookie createHttpOnlyCookie(String name, String value, String path, long maxAge) {
        return buildCookie(name, value, path, maxAge, true, true);
    }
}
