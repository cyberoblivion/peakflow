package com.cyberoblivion.peakflow.views.dashboard;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.shared.ui.LoadMode;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;

import java.util.Timer;
import java.util.TimerTask;

@PageTitle("Dashboard")
@RouteAlias("/dashboard/")
@Route("")
@Menu(order = 0, icon = "line-awesome/svg/chart-line-solid.svg")
@PermitAll
@JsModule(value = "./js/dashboard-metrics-update.js")  
public class DashboardView extends VerticalLayout {

    private ProgressBar cpuProgress;
    private ProgressBar memoryProgress;
    private Div cpuLabel;
    private Div memoryLabel;
    private Timer timer;
    private UI ui; // Store a reference to the current UI

    public DashboardView() {
        // Capture the current UI
        this.ui = UI.getCurrent();
        Div title = new Div("System Dashboard - Dangerously Overpowered");
        title.getStyle().set("font-size", "24px").set("font-weight", "bold");

        // CPU "Overpower" Section
        cpuLabel = new Div("CPU Overpower: 0%");
        cpuProgress = new ProgressBar();
        cpuProgress.setWidth("80%");
        HorizontalLayout cpuLayout = new HorizontalLayout(cpuLabel, cpuProgress);
        cpuLayout.setAlignItems(Alignment.CENTER);

        // Memory "Explosion" Section
        memoryLabel = new Div("Memory Explosion Level: 0%");
        memoryProgress = new ProgressBar();
        memoryProgress.setWidth("80%");
        HorizontalLayout memoryLayout = new HorizontalLayout(memoryLabel, memoryProgress);
        memoryLayout.setAlignItems(Alignment.CENTER);

        // Refresh Button
        Button refreshButton = new Button("Recalculate Danger Levels");
        refreshButton.addClickListener(e -> updateMetrics());

        // Add components to the layout
        add(title, cpuLayout, memoryLayout, refreshButton);

        cpuProgress.setId("cpuProgress");
        memoryProgress.setId("memoryProgress");
        cpuLabel.setId("cpuLabel");
        memoryLabel.setId("memoryLabel");
        // Initial random values
        updateMetrics();
        // Start the client-side JavaScript updates
        getElement().executeJs("window.startMetricsUpdate($0)", getElement());
    }

    private void updateMetrics() {
        double cpuValue = 20 + Math.random() * 50; // Random between 50 and 100
        double memoryValue = 20 + Math.random() * 50; // Random between 50 and 100

        cpuProgress.setValue(cpuValue / 100.0);
        memoryProgress.setValue(memoryValue / 100.0);

        cpuLabel.setText("CPU Overpower: " + (int) cpuValue + "%");
        memoryLabel.setText("Memory Explosion Level: " + (int) memoryValue + "%");
    }

    private void executeClientSideUpdate() {
        getElement().executeJs(
            """
              setInterval(function() {
              // Generate random values between 50% and 100% for CPU and Memory
              var cpuValue = Math.floor(50 + Math.random() * 50);
              var memoryValue = Math.floor(50 + Math.random() * 50);

              // Update CPU progress bar and label
              var cpuProgressBar = $0.querySelector('#cpuProgress');
              var cpuLabel = $0.querySelector('#cpuLabel');
              if (cpuProgressBar) cpuProgressBar.value = cpuValue / 100;
              if (cpuLabel) cpuLabel.textContent = 'CPU Overpower: ' + cpuValue + '%';

              // Update Memory progress bar and label
              var memoryProgressBar = $0.querySelector('#memoryProgress');
              var memoryLabel = $0.querySelector('#memoryLabel');
              if (memoryProgressBar) memoryProgressBar.value = memoryValue / 100;
              if (memoryLabel) memoryLabel.textContent = 'Memory Explosion Level: ' + memoryValue + '%';
            }, 3000);", getElement()
                    """            
        );
    }

}
