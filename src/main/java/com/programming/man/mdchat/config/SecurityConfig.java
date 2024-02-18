package com.programming.man.mdchat.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${jwt.public.key}")
    RSAPublicKey publicKey;

    @Value("${jwt.private.key}")
    RSAPrivateKey privateKey;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
//                .cors().and()
.csrf().disable()

.authorizeRequests(authorize -> authorize
                           .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                           .requestMatchers("/api/auth/**",
                                            "/api/auth/signup",
                                            "/api/auth/login",
                                            "/api/auth/logout",
                                            "/api/auth/changeuserinfo",
                                            "/api/auth/getusersinfo").permitAll()
                           .requestMatchers("/api/posts/**").permitAll()
                           .requestMatchers("/api/posts/all").permitAll()
                           .requestMatchers("/api/posts/v1/all").permitAll()
                           .requestMatchers("/api/posts/cud").authenticated()
                           .requestMatchers("/api/channel/**").permitAll()
                           .requestMatchers("/api/comments/**",
                                            "/api/comments/cud",
                                            "/api/comments/universal-comments",
                                            "/api/comments/by-post/**",
                                            "/api/comments/by-post").permitAll()
                           .requestMatchers("/api/votes/**").permitAll()
                           .requestMatchers("/api/channel/search").permitAll()
                           .requestMatchers("/api/posts/chposts/**").permitAll()
                           .requestMatchers("/api/file/**").permitAll()
                           .requestMatchers("/api/file/upload/**").permitAll()
                           .requestMatchers("/api/posts/by-id/**").permitAll()
                           .requestMatchers("/api/comments/by-post/**")
                           .permitAll()
                           .requestMatchers("/api/channel/create")
                           .permitAll()
                           .requestMatchers("/v3/api-docs/**",
                                            "/configuration/ui",
                                            "/swagger-resources/**",
                                            "/configuration/security",
//                                            "/swagger-ui/**",
                                            "/webjars/**"
                                           )
                           .permitAll()
                           .anyRequest()
                           .authenticated()
                  )
.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
.exceptionHandling(exceptions -> exceptions
        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
        .accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
.csrf(AbstractHttpConfigurer::disable)
.cors(AbstractHttpConfigurer::disable)
.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}
