package com.sportt5.util;

import javafx.scene.Parent;

public class ThemeManager {

    private static boolean darkMode = true;

    public static void toggle(Parent root) {
        darkMode = !darkMode;
        if (darkMode) {
            root.getStyleClass().remove("light-theme");
        } else {
            root.getStyleClass().add("light-theme");
        }
    }

    public static boolean isDarkMode() {
        return darkMode;
    }
}
