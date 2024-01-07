package bo.gob.segip.micro_servicios.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //    @Autowired
//    private UserDetailsService userDetailsService;
    private final UserDetailsService userDetailsService;

    //    @Autowired
//    private JwtTokenHelper jwtTokenHelper;
    private final JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1 get token
        String bearerToken = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (request != null
                && bearerToken != null
                && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException a) {
                System.out.println("Unable to getJWt token");
            } catch (ExpiredJwtException c) {
                System.out.println("Jwt token has expired");
            } catch (MalformedJwtException e) {
                System.out.println("Invalid jwt");
            }
        }
        /*
        else {
            System.out.println("JWT token does not begin with Bearer");
        }
        */

        // Once get token validate the received token
        if (username != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (this.jwtTokenHelper.validateToken(token, userDetails)) {
                // authenticate
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken); // usernamePasswordAuthenticationToken
            } else
                System.out.println("Invalid JWT token");
        }
        //else
          //  System.out.println("USERNAME IS NULL OR CONTEXT IS NOT NULL");

        filterChain.doFilter(request, response);

    }

}
