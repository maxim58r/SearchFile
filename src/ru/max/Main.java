package ru.max;

import java.io.File;

public class Main {
    public static void main(String[] args) {

        File pathFile = new File("/proc/self");
        String nameSearch = "7f5ce90a3000-7f5ce90ae000";

        for (int i = 0; i < pathFile.listFiles().length; i++) {
            if (pathFile.isDirectory()) {
                int finalI = i;
                Thread thread = new Thread(() -> {
                    searchFile(pathFile.listFiles()[finalI], nameSearch);
                });
                thread.start();
            }
        }
    }

    public static void searchFile(File dirFile, String name) {
        File[] listDir = dirFile.listFiles();
        if (dirFile.isDirectory()) {
            try {
                for (File dir : listDir) {
//                    System.out.println(dir);
                    searchFile(dir, name);
                    if (dir.isFile()) {
//                    System.out.println(dir.getName());
                        if (dir.getName().toLowerCase().startsWith(name.toLowerCase())) {
                            System.out.println(dir);
                            break;
                        }
                    }
                }
            } catch (NullPointerException n) {
            }
        }
    }
}