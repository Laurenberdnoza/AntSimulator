package antsimulation.settingshandler;

public class SettingsHandler {

    private boolean pheromonesVisible = true;

    public void togglePheromoneVisibility() {
        pheromonesVisible = !pheromonesVisible;
    }

    public boolean isPheromonesVisible() {
        return pheromonesVisible;
    }
}
