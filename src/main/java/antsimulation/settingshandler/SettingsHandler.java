package antsimulation.settingshandler;

public class SettingsHandler {

    private boolean pheromonesVisible = true;
    private boolean wallMode = false;

    public void togglePheromoneVisibility() {
        pheromonesVisible = !pheromonesVisible;
    }

    public boolean isPheromonesVisible() {
        return pheromonesVisible;
    }

    public void toggleWallMode() {
        wallMode = !wallMode;
    }

    public boolean isWallModeActive() {
        return wallMode;
    }
}
