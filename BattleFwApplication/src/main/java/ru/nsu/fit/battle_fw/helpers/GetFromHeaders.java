package ru.nsu.fit.battle_fw.helpers;

import org.springframework.util.StringUtils;
import ru.nsu.fit.battle_fw.configs.jwt.JwtUtils;

import java.util.Map;

public class GetFromHeaders {
    public static String getUsernameFromJWT(Map<String, String> headers, JwtUtils jwtUtils){
        String headerAuth = headers.get("authorization");
        String jwt;

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            jwt = headerAuth.substring(7);
        }
        else{
            return null;
        }
        return jwtUtils.getUserNameFromJwtToken(jwt);
    }
}
