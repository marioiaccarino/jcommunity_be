package it.jpanik.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import it.jpanik.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jcommunity.app.jwtSecret}")
    private String jwtSecret;

    @Value("${jcommunity.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication)   {
        User user = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((user.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken)   {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e)   {
            LOGGER.error("Invalid jwt Token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("Jwt Token is expired: {}", e.getMessage());
        }catch (UnsupportedJwtException e)  {
            LOGGER.error("Jwt token is unsupported : {}" ,e.getMessage());
        }catch (IllegalArgumentException e) {
            LOGGER.error("Jwt Claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
