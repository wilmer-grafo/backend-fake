//package bo.gob.segip.micro_servicios.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.session.SessionRegistry;
//import org.springframework.security.core.session.SessionRegistryImpl;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain customFilterChain(HttpSecurity pHttpSecurity) throws Exception {
//        return pHttpSecurity
////                .csrf(pCsrfConfigurer -> pCsrfConfigurer.disable())
//                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
//                        authorizationManagerRequestMatcherRegistry
//                                .requestMatchers("/free/route").permitAll()
//                                .anyRequest().authenticated())
//                .formLogin(httpSecurityFormLoginConfigurer ->
//                        httpSecurityFormLoginConfigurer
//                                .successHandler(this.customSuccessHandler())
//                                .permitAll())
//                .sessionManagement(httpSecuritySessionManagementConfigurer ->
//                        httpSecuritySessionManagementConfigurer
////                                .sessionFixation().migrateSession()
//                                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//                                .invalidSessionUrl("/login")
//                                .maximumSessions(1)
//                                .expiredUrl("/login")
//                                .sessionRegistry(this.customSessionRegistry()))
//                .build();
//    }
//
//    @Bean
//    public SessionRegistry customSessionRegistry() {
//        return new SessionRegistryImpl();
//    }
//
//    public AuthenticationSuccessHandler customSuccessHandler() {
//        return ((request, response, authentication) -> {
//            response.sendRedirect("/api/categories/");
//            ;
//        });
//    }
//
//}
