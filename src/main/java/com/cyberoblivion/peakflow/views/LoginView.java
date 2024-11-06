package com.cyberoblivion.peakflow.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends LoginOverlay {

    /**
     * URL that Spring uses to connect to Google services
     */
    private static final String OAUTH_GOOGLE_URL = "/oauth2/authorization/google";
    private static final String OAUTH_GITHUB_URL = "/oauth2/authorization/github";

    public LoginView() {
        setAction("login");

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("PeakFlow");
        i18n.getHeader().setDescription("Login using PeakFlow username and password or Social SSO. Accounts are created automatically and cleared every night");
        i18n.setAdditionalInformation(null);
        setI18n(i18n);
        setForgotPasswordButtonVisible(false);
        getFooter().add(buildSsoFooter());
        setOpened(true);
    }

    private Component buildSsoFooter() {
        VerticalLayout layout = new VerticalLayout();
        Span orspan = new Span("Or");
        layout.add(orspan);
        layout.setAlignSelf(Alignment.CENTER, orspan);     
    
        // GitHub Login Button
        Button githubButton = getGithubButton();        
        layout.add(githubButton);
        // Google Login Button
        Button googleButton = getGoogleButton();
        layout.add(googleButton);

        return layout;
    }

    private Button getGoogleButton() {
        Button googleButton = new Button("Login with Google", createGoogleIcon());
        googleButton.addClassName("google-button"); // Apply Google button style
        googleButton.addClickListener(
                event -> getUI().ifPresent(ui -> ui.getPage().setLocation(OAUTH_GOOGLE_URL)));
        return googleButton;
    }

    private Button getGithubButton() {
        Button githubButton = new Button("Login with GitHub", createGitHubIcon());
        githubButton.addClassName("github-button"); // Apply GitHub button style
        githubButton.addClickListener(
                event -> getUI().ifPresent(ui -> ui.getPage().setLocation(OAUTH_GITHUB_URL)));
        githubButton.focus();
        return githubButton;
    }

    private Image createGoogleIcon() {
        // Google logo from Google Developers branding resources
        Image googleIcon = new Image("https://developers.google.com/identity/images/g-logo.png", "Google logo");
        googleIcon.addClassName("button-icon"); // Apply icon size class
        return googleIcon;
    }

    private Image createGitHubIcon() {
        // GitHub logo hosted on GitHub's Octodex
        Image githubIcon = new Image("https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png",
                "GitHub logo");
        githubIcon.addClassName("button-icon"); // Apply icon size class
        return githubIcon;
    }
}