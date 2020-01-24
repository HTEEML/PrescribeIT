package com.jarifjak.prescribeit.model;

public class DashboardObject {

    private int objectIcon;
    private int objectBackground;
    private String objectTitle;

    public DashboardObject() {

    }

    public DashboardObject(int objectIcon, int objectBackground, String objectTitle) {

        this.objectIcon = objectIcon;
        this.objectBackground = objectBackground;
        this.objectTitle = objectTitle;
    }

    public int getObjectIcon() {
        return objectIcon;
    }

    public void setObjectIcon(int objectIcon) {
        this.objectIcon = objectIcon;
    }

    public int getObjectBackground() {
        return objectBackground;
    }

    public void setObjectBackground(int objectBackground) {
        this.objectBackground = objectBackground;
    }

    public String getObjectTitle() {
        return objectTitle;
    }

    public void setObjectTitle(String objectTitle) {
        this.objectTitle = objectTitle;
    }
}
