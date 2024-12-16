package com.example.devicesmicroservice.config;

import com.example.devicesmicroservice.controllers.DeviceController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtUtils {

    @Value("${jwtSecret}")
    private String jwtSecret;
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceController.class);

    public boolean isValidToken(String token) {
        token = token.replace("Bearer ", "");
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }
    public boolean isAdmin(String token) {
        token = token.replace("Bearer ", "");
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            List<String> authorities = (List<String>) claims.get("authorities");
            if (authorities != null && authorities.contains("ADMIN")) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("Could not extract Role from Jwt token");
        }
        return false;
    }

    public boolean isUser(String token) {
        token = token.replace("Bearer ", "");
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            List<String> authorities = (List<String>) claims.get("authorities");
            if (authorities != null && authorities.contains("USER")) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("Could not extract Role from Jwt token");
        }
        return false;
    }

}
