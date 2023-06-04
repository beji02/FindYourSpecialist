package fys.fysserver.api.utils;

public class HeadersUtils {
    public static String extractTokenFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            System.out.println("extractTokenFromAuthorizationHeader");
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
