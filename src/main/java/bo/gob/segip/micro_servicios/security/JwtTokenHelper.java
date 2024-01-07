package bo.gob.segip.micro_servicios.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private static final SecretKey key = Jwts.SIG.HS256.key().build();
    private static final String SECRET = Encoders.BASE64.encode(key.getEncoded());

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //	retrieve userName from JWT token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //	retrieve expiration date from jwt Token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //	to retrieve any inform from token we'll need secret key
    private Claims getAllClaimsFromToken(String token) {
                return Jwts.parser()
                .setSigningKey(SECRET)
                        .build()
                .parseClaimsJws(token).getBody();

//        return Jwts.parser()
//                .verifyWith((PublicKey) getSignKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
    }

    //	check if token expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //	generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //	while creating token -
    //	1. Define claims of the token, like Issuer, Expiration. Subject and the ID
    //	2. Sign the JWT using HS512 algorithm and secret key
    //	3. Acc to JWS compact Serialization (https://tols.ietf.org/html/draft-ietf-jose-
    //	compaction of the 	JWT to a URL-SAFE STRING
    private String doGenerateToken(Map<String, Object> claims, String subject) {

//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 100))
//                .signWith(SignatureAlgorithm.HS512, secret).compact();

        return Jwts.builder()
                .claims().add(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 100))
                .and()
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();


    }

    //	VALIDATE TOKEN
    public boolean validateToken(String authToken, UserDetails userDetails) {
        final String username = getUsernameFromToken(authToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(authToken));
    }

}
