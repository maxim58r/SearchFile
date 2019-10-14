package ru.max;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String path = null;
        Scanner input = new Scanner((System.in));
        String OS = System.getProperty("os.name").toLowerCase();
        System.out.println(OS);

        System.out.print("Enter disk's letter for searching: ");
        String enterDiskForSearch = input.nextLine();


        System.out.print("Enter name file for searching: ");
        String searchFiles = input.nextLine();

        if (OS.toLowerCase().startsWith("windows")) {
            System.out.println("You system Windows");
            path = enterDiskForSearch + ":\\";
        } else if (OS.toLowerCase().startsWith("linux")) {
            System.out.println("You system Unix");
            path = "/";
        } else if (OS.toLowerCase().startsWith("mac")) {
            System.out.println("You system Mac");
            path = "/";
        }
        assert path != null;
        File diskForSearch = new File(path);

        try {
            for (int i = 0; i < Objects.requireNonNull(diskForSearch.listFiles()).length; i++) {
                if (diskForSearch.isDirectory()) {
                    int finalI = i;
                    Thread thread = new Thread(() -> searchFile(Objects.requireNonNull(diskForSearch.listFiles())[finalI], searchFiles));
                    thread.start();
                }
            }
        } catch (NullPointerException ignored) {
        }
    }

    private static void searchFile(File fileSearchDirectory, String searchFileName) {
        File[] listDirectories = fileSearchDirectory.listFiles();
        if (fileSearchDirectory.isDirectory()) {
            try {
                assert listDirectories != null;
                for (File directory : listDirectories) {
                    searchFile(directory, searchFileName);
                    if (directory.isFile()) {
                        if (searchFileName.startsWith(".")) {
                            if (directory.getName().toLowerCase().endsWith(searchFileName.toLowerCase())) {
                                System.out.println(directory);
                            }
                        } else {
                            if (directory.getName().toLowerCase().startsWith(searchFileName.toLowerCase())) {
                                System.out.println(directory);
                            }
                        }
                    }
                }
            } catch (NullPointerException ignored) {
            }
        }
    }
}