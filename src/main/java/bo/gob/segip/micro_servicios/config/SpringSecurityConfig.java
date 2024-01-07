package bo.gob.segip.micro_servicios.config;

import bo.gob.segip.micro_servicios.security.CustomUserDetailService;
import bo.gob.segip.micro_servicios.security.JwtAuthenticationEntryPoint;
import bo.gob.segip.micro_servicios.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final CustomUserDetailService customUserDetailService;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // BASIC AUTH

//    @Bean
//    public SecurityFilterChain customFilterChain(HttpSecurity pHttpSecurity) throws Exception {
//        pHttpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
//                        authorizationManagerRequestMatcherRegistry
//                                .anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults());
//
//        return pHttpSecurity.build();
//    }

    @Bean
    public SecurityFilterChain customFilterChain(HttpSecurity pHttpSecurity) throws Exception {

        CsrfTokenRequestAttributeHandler csrfRequestHandler = new CsrfTokenRequestAttributeHandler();
        csrfRequestHandler.setCsrfRequestAttributeName("_csrf");

        pHttpSecurity
                .cors(Customizer.withDefaults())
//                .csrf(pCsrfConfigurer -> pCsrfConfigurer.disable())
                .csrf(csrf -> csrf
                        .csrfTokenRequestHandler(csrfRequestHandler)
                        .ignoringRequestMatchers("/auth/**")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                .authenticationProvider(this.customAuthenticationProvider());

        pHttpSecurity.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return pHttpSecurity.build();
    }

    @Bean
    public PasswordEncoder customPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationProvider customAuthenticationProvider() {
//
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(customUserDetailService);
//        authenticationProvider.setPasswordEncoder(customPasswordEncoder());
//
//        return authenticationProvider;
//    }

    @Bean
    public AuthenticationManager customAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
