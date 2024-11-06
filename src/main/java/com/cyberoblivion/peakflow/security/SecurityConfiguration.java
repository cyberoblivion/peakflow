package com.cyberoblivion.peakflow.security;

import com.cyberoblivion.peakflow.data.Role;
import com.cyberoblivion.peakflow.data.UserAccount;
import com.cyberoblivion.peakflow.data.UserAccountRepository;
import com.cyberoblivion.peakflow.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {
    static org.slf4j.Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);
    public static final String LOGOUT_URL = "/";
    private static final String LOGIN_URL = "/login";
    private UserAccountRepository userAccountRepository;

    public SecurityConfiguration(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/images/*.png")).permitAll());

        // Icons from the line-awesome addon
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(new AntPathRequestMatcher("/line-awesome/**/*.svg")).permitAll());

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")) // Disable CSRF for H2 Console
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // Allow access to H2 Console without                
                )
                .headers(headers -> headers
                        .frameOptions(FrameOptionsConfig::sameOrigin) // Allow H2 Console to be loaded in
                                                                      // frames
                );

        setLoginView(http, LoginView.class, LOGOUT_URL);

        http.oauth2Login(t -> t.successHandler(onAuthenticationSuccess()));
        http.oauth2Login(t -> t.loginPage(LOGIN_URL));

        super.configure(http);
    }

    public AuthenticationSuccessHandler onAuthenticationSuccess() {
        return (request, response, authentication) -> {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User principal = oauthToken.getPrincipal();
            String givenName = principal.getAttribute("given_name");
            String familyName = principal.getAttribute("family_name");
            String email = principal.getAttribute("email");
            String picture = principal.getAttribute("picture");
            String username = email==null?principal.getAttribute("login"):email;
            try {
                UserAccount user = userAccountRepository.findByUsername(username)
                        .orElseGet(() -> createUser(username, givenName, familyName, email, picture));

                List<GrantedAuthority> authorities = user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList());

                // Create a new OAuth2User with updated authorities google users email github uses login for the principal name
                OAuth2User updatedPrincipal = new DefaultOAuth2User(authorities, principal.getAttributes(),
                        email == null ? "login" : "email");

                // Create a new OAuth2AuthenticationToken with the updated principal and
                // authorities
                OAuth2AuthenticationToken newAuth = new OAuth2AuthenticationToken(
                        updatedPrincipal,
                        authorities,
                        oauthToken.getAuthorizedClientRegistrationId());

                // Update the SecurityContext with the new authentication
                SecurityContextHolder.getContext().setAuthentication(newAuth);
                response.sendRedirect("/dashboard");
            } catch (Exception exception) {
                response.sendRedirect("/login-failure");
                log.error("could not complete post authentication process", exception);
            }
        };
    }

    private UserAccount createUser(String username, String givenName, String familyName, String email, String picture) {
        UserAccount user = new UserAccount();
        user.setUsername(username == null ? email : username);
        user.setEmail(email); // Assuming email and username are the same for OAuth
        user.setFirstName(givenName);
        user.setLastName(familyName);
        user.setPassword("Not a Valid Password"); // OAuth providers don’t supply passwords
        user.setEnabled(true);
        user.setRoles(Collections.singletonList(Role.USER)); // Assign default role
        user.setPicture(picture);
        return userAccountRepository.save(user);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
