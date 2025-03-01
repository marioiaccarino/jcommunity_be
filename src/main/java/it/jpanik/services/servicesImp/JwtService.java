package it.jpanik.services.servicesImp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import it.jpanik.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "4bb6d1dfbafb64a681139d1586f1160d18159afd57c8c79136d7490630407c";

    public <T> T extractClaim(String token, Function<Claims, T> resolver)   {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractUsername(String token)     {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user)    {
        String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /*checkError*/
    private Claims extractAllClaims(String token)   {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(User user)  {
        String token = Jwts
                .builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24*60*60*1000 ))
                .signWith(getSigninKey())
                .compact();
        return token;
    }

    private SecretKey getSigninKey()    {
        byte[] KeyBites = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(KeyBites);
    }
}

