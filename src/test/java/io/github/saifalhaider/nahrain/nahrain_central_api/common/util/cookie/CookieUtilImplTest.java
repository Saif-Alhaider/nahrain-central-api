package io.github.saifalhaider.nahrain.nahrain_central_api.common.util.cookie;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.http.ResponseCookie;

import java.time.Duration;

public class CookieUtilImplTest {

    private final CookieUtilImpl cookieUtil = new CookieUtilImpl();
    private static final String NAME = "cookieName";
    private static final String VALUE = "cookieValue";
    private static final String PATH = "/";
    private static final long MAX_AGE = 3600;

    @Test
    void should_create_regular_cookie_with_http_only_disabled_and_secure_disabled() {
        ResponseCookie responseCookie = cookieUtil.createRegularCookie(NAME, VALUE, PATH, MAX_AGE);
        assertCommonCookieProperties(responseCookie, false, false);
    }

    @Test
    void should_create_http_only_cookie_with_http_only_enabled_and_secure_enabled() {
        ResponseCookie responseCookie = cookieUtil.createHttpOnlyCookie(NAME, VALUE, PATH, MAX_AGE);
        assertCommonCookieProperties(responseCookie, true, true);
    }

    private void assertCommonCookieProperties(ResponseCookie responseCookie, boolean isHttpOnly, boolean isSecure) {
        assertEquals(NAME, responseCookie.getName());
        assertEquals(VALUE, responseCookie.getValue());
        assertEquals(PATH, responseCookie.getPath());
        assertEquals(Duration.ofSeconds(MAX_AGE), responseCookie.getMaxAge());
        assertEquals(isHttpOnly, responseCookie.isHttpOnly());
        assertEquals(isSecure, responseCookie.isSecure());
    }
}
