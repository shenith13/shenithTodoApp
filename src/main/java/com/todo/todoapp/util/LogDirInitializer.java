package com.todo.todoapp.util;

import java.io.File;

public class LogDirInitializer {
    public static void createLogDirectory() {
        String localAppData = System.getenv("LOCALAPPDATA");
        if (localAppData == null) {
            System.err.println("LOCALAPPDATA environment variable is not set.");
            return;
        }

        File logDir = new File(localAppData + File.separator + "todoapp" + File.separator + "logs");

        if (!logDir.exists()) {
            boolean created = logDir.mkdirs();
            if (created) {
                System.out.println("Created log directory at: " + logDir.getAbsolutePath());
            } else {
                System.err.println("Failed to create log directory at: " + logDir.getAbsolutePath());
            }
        }
    }
}
