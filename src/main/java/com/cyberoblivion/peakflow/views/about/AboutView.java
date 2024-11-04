package com.cyberoblivion.peakflow.views.about;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PageTitle("About")
@Route("about")
@Menu(order = 1, icon = "line-awesome/svg/question-solid.svg")
@PermitAll
public class AboutView extends Composite<VerticalLayout> {

    public AboutView() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
    }
}
