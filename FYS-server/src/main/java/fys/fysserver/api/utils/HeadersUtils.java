package fys.fysserver.api.utils;

import fys.fysserver.api.security.jwt.JwtUtils;

public class HeadersUtils {
    public static String extractTokenFromAuthorizationHeader(String authorizationHeader, JwtUtils jwtUtils) {
        try {
            String token = authorizationHeader.substring(7);
            return jwtUtils.getUsernameFromJwtToken(token);
        } catch (Exception e) {
            return null;
        }
    }
}
