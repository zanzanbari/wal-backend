package backend.wal.utils;

import org.springframework.http.HttpHeaders;

public class HttpHeaderUtils {

    private HttpHeaderUtils() {
    }

    public static final String AUTHORIZATION = HttpHeaders.AUTHORIZATION;
    public static final String AUTHENTICATION_TYPE = "Bearer ";
    public static final String REFRESH_TOKEN = "Refresh-Token";

    public static String withBearerToken(String accessToken) {
        return AUTHENTICATION_TYPE.concat(accessToken);
    }
}
