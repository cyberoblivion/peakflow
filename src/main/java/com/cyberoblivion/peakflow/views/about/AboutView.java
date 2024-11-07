package com.cyberoblivion.peakflow.views.about;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

import jakarta.annotation.security.PermitAll;

@PageTitle("About")
@Route("about")
@Menu(order = 1, icon = "line-awesome/svg/question-solid.svg")
@PermitAll
public class AboutView extends VerticalLayout {

    public AboutView() {
       setSpacing(false);        
        Image cyberImg = new Image("/images/logo_final.svg", "CyberOblivion logo");
        Image peakflowImg = new Image("/images/peakflow.png", "PeakFlow logo");
        Anchor cyberAnchor = new Anchor("https://blog.cyberoblivion.com");        
        cyberAnchor.add(cyberImg);
        cyberImg.setWidth("200px");
        peakflowImg.setWidth("400px");
        add(peakflowImg);

        H2 header = new H2("Peak Flow");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);
        add(new Paragraph("By CyberOblivion 🤗"));
        add(cyberAnchor);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}
