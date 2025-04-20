package com.coditas.HangMan.security;


import com.coditas.HangMan.entity.Player;
import com.coditas.HangMan.entity.Role;
import com.coditas.HangMan.entity.User;
import com.coditas.HangMan.exception.BlogApiException;
import com.coditas.HangMan.repository.PlayerRepository;
import com.coditas.HangMan.repository.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlayerRepository playerRepository;


    //gen token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String token = Jwts.builder().
                setSubject(username).setIssuedAt(new Date()).setExpiration(expireDate).
                signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
        return token;
    }

    //get username
    public String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String getRole(String username) {
        User user = userRepository.findByUserName(username).get();
        Set<Role> roleSet = user.getRoleSet();
        String r1 = null;
        for (Role r :
                roleSet) {
            r1 = r.getRoleName();
            break;
        }
        System.out.println(r1);
        String r2 = r1.substring(5, r1.length()).toLowerCase();
        System.out.println(r2);
        return r2;
    }

    public Long getUserId(String username) {
        String roleOfUser = getRole(username);
        if (roleOfUser.equals("admin")) {
            return 0l;
        } else if (roleOfUser.equals("player")) {
            User user = userRepository.findByUserName(username).get();
            return user.getPlayer().getPlayerId();
        }else {
            return 0l;
        }
    }

    //validate
    public boolean getValidate(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return  true;
        }catch (SignatureException ex){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST,"invalid jwt signature");
        }catch (MalformedJwtException ex) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }

    }
}

