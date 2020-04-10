package br.com.lucasmancan.pms.configurations.security;

public final class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/api/auth/login";

    public static final String JWT_SECRET = "r5u8x/A?D(G+KbPeShVmYq3t6v9y$B&E)H@McQfTjWnZr4u7x!z%C*F-JaNdRgUk\n";

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "api-rocks";
    public static final String TOKEN_AUDIENCE = "secure-app";

}