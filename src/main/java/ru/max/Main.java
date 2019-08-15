package ru.max;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String path = null;
        Scanner input = new Scanner((System.in));
        String OS = System.getProperty("os.name").toLowerCase();
        System.out.println(OS);

        System.out.print("Enter disk's letter for searching: ");
        String enterDisk = input.nextLine();


        System.out.print("Enter name file for searching: ");
        String searchFiles = input.nextLine();

        if (OS.toLowerCase().startsWith("windows")) {
            System.out.println("You system Windows");
            path = enterDisk + ":\\";
        } else if (OS.toLowerCase().startsWith("linux")) {
            System.out.println("You system Unix");
            path = "/";
        } else if (OS.toLowerCase().startsWith("mac")) {
            System.out.println("You system Mac");
            path = "/";
        }
        File pathF = new File(path);

        try {
            for (int i = 0; i < pathF.listFiles().length; i++) {
                if (pathF.isDirectory()) {
                    int finalI = i;
                    Thread thread = new Thread(() -> searchFile(pathF.listFiles()[finalI], searchFiles));
                    thread.start();
                }
            }
        } catch (NullPointerException ignored) {
        }
    }

    private static void searchFile(File dirFile, String name) {
        File[] listDir = dirFile.listFiles();
        if (dirFile.isDirectory()) {
            try {
                for (File dir : listDir) {
                    searchFile(dir, name);
                    if (dir.isFile()) {
                        if (name.startsWith(".")) {
                            if (dir.getName().toLowerCase().endsWith(name.toLowerCase())) {
                                System.out.println(dir);
                            }
                        } else {
                            if (dir.getName().toLowerCase().startsWith(name.toLowerCase())) {
                                System.out.println(dir);
                            }
                        }
                    }
                }
            } catch (NullPointerException n) {
            }
        }
    }
}