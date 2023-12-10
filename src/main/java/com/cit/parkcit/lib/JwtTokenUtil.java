// package com.cit.parkcit.lib;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;

// import com.cit.parkcit.model.User;

// import java.security.Key;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.Map;

// import javax.crypto.spec.SecretKeySpec;

// @Component
// public class JwtTokenUtil {

//     @Value("${jwt.expiration}")
//     private Long expiration;

//     @Value("${jwt.secret}")
//     private String secret; // Replace with your actual secret key

//     public String generateToken(User user) {
//         Map<String, Object> claims = new HashMap<>();
//         return doGenerateToken(claims, user.getUsername());
//     }

//     private String doGenerateToken(Map<String, Object> claims, String subject) {
//         Date createdDate = new Date();
//         Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);

//         return Jwts.builder()
//                 .setClaims(claims)
//                 .setSubject(subject)
//                 .setIssuedAt(createdDate)
//                 .setExpiration(expirationDate)
//                 .signWith(getKey(), SignatureAlgorithm.HS512)
//                 .compact();
//     }

//     public String getUsernameFromToken(String token) {
//         Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
//         return claims.getSubject();
//     }

//     public boolean validateToken(String token, User user) {
//         final String username = getUsernameFromToken(token);
//         return username.equals(user.getUsername()) && !isTokenExpired(token);
//     }

//     private boolean isTokenExpired(String token) {
//         Date expirationDate = getExpirationDateFromToken(token);
//         return expirationDate.before(new Date());
//     }

//     private Date getExpirationDateFromToken(String token) {
//         Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
//         return claims.getExpiration();
//     }

//     private Key getKey() {
//         return new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS512.getJcaName());
//     }
// }
