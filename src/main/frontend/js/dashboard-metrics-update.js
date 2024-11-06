function startMetricsUpdate(container) {
    setInterval(function() {
        // Generate random values between 50% and 100% for CPU and Memory
        var cpuValue = Math.floor(50 + Math.random() * 50);
        var memoryValue = Math.floor(50 + Math.random() * 50);

        // Update CPU progress bar and label
        var cpuProgressBar = container.querySelector('#cpuProgress');
        var cpuLabel = container.querySelector('#cpuLabel');
        if (cpuProgressBar) cpuProgressBar.value = cpuValue / 100;
        if (cpuLabel) cpuLabel.textContent = 'CPU Overpower: ' + cpuValue + '%';

        // Update Memory progress bar and label
        var memoryProgressBar = container.querySelector('#memoryProgress');
        var memoryLabel = container.querySelector('#memoryLabel');
        if (memoryProgressBar) memoryProgressBar.value = memoryValue / 100;
        if (memoryLabel) memoryLabel.textContent = 'Memory Explosion Level: ' + memoryValue + '%';
    }, 500);
}
// Export the function
window.startMetricsUpdate = startMetricsUpdate;