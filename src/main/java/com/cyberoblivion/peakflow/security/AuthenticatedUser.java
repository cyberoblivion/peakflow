package com.cyberoblivion.peakflow.security;

import com.cyberoblivion.peakflow.data.UserAccount;
import com.cyberoblivion.peakflow.data.UserAccountRepository;
import com.vaadin.flow.spring.security.AuthenticationContext;
import java.util.Optional;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUser {

    private final AuthenticationContext authenticationContext;
    private final UserAccountRepository userAccountRepository;

    public AuthenticatedUser(AuthenticationContext authenticationContext, UserAccountRepository userAccountRepository) {
        this.authenticationContext = authenticationContext;
        this.userAccountRepository = userAccountRepository;
    }

    public Optional<OAuth2User> get() {
        return authenticationContext.getAuthenticatedUser(OAuth2User.class);
    }

    public Optional<UserAccount> getUserAccount() {        
        return get()
        .flatMap(oauthUser -> {
            String identifier = oauthUser.getAttribute("email"); // Default to "email" for Google

            if (oauthUser.getAttribute("login") != null) { // Check if GitHub
                identifier = oauthUser.getAttribute("login");
            }

            // Use the identifier for both username and email parameters in the query
            return userAccountRepository.findByUsernameOrEmail(identifier, identifier);
        });
    }

    public void logout() {
        authenticationContext.logout();
    }

}
