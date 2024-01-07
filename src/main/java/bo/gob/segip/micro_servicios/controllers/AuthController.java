package bo.gob.segip.micro_servicios.controllers;

import bo.gob.segip.micro_servicios.payloads.JwtAuthRequest;
import bo.gob.segip.micro_servicios.payloads.JwtAuthResponse;
import bo.gob.segip.micro_servicios.security.JwtTokenHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenHelper helper;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private void authenticate(String userName, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userName, password);
        try {
            this.authenticationManager.authenticate(authenticationToken);

        } catch (BadCredentialsException e) {
            System.out.println("Invalid Credentials");
             throw new BadCredentialsException("Invalid username or password"); // Exception
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {

        this.authenticate(jwtAuthRequest.getUserName(), jwtAuthRequest.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUserName());
        String token = this.helper.generateToken(userDetails);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);

    }

}
