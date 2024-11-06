package com.cyberoblivion.peakflow.views.dashboard;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;

@PageTitle("Dashboard")
@RouteAlias("/dashboard/")
@Route("")
@Menu(order = 0, icon = "line-awesome/svg/chart-line-solid.svg")
@PermitAll
public class DashboardView extends VerticalLayout {

    private ProgressBar cpuProgress;
    private ProgressBar memoryProgress;
    private Label cpuLabel;
    private Label memoryLabel;

    public DashboardView() {
        Label title = new Label("System Dashboard - Dangerously Overpowered");
        title.getStyle().set("font-size", "24px").set("font-weight", "bold");

        // CPU "Overpower" Section
        cpuLabel = new Label("CPU Overpower: 0%");
        cpuProgress = new ProgressBar();
        cpuProgress.setWidth("80%");
        HorizontalLayout cpuLayout = new HorizontalLayout(cpuLabel, cpuProgress);
        cpuLayout.setAlignItems(Alignment.CENTER);

        // Memory "Explosion" Section
        memoryLabel = new Label("Memory Explosion Level: 0%");
        memoryProgress = new ProgressBar();
        memoryProgress.setWidth("80%");
        HorizontalLayout memoryLayout = new HorizontalLayout(memoryLabel, memoryProgress);
        memoryLayout.setAlignItems(Alignment.CENTER);

        // Refresh Button
        Button refreshButton = new Button("Recalculate Danger Levels");
        refreshButton.addClickListener(e -> updateMetrics());

        // Add components to the layout
        add(title, cpuLayout, memoryLayout, refreshButton);

        // Initial random values
        updateMetrics();
    }

    private void updateMetrics() {
        double cpuValue = 20 + Math.random() * 50;  // Random between 50 and 100
        double memoryValue = 20 + Math.random() * 50;  // Random between 50 and 100

        cpuProgress.setValue(cpuValue / 100.0);
        memoryProgress.setValue(memoryValue / 100.0);

        cpuLabel.setText("CPU Overpower: " + (int) cpuValue + "%");
        memoryLabel.setText("Memory Explosion Level: " + (int) memoryValue + "%");
    }
}
