package com.elephant.test;

import java.io.File;

public class Mp3FileReader {

    public static void main(String[] args) {
        String directoryPath = "D:\\zhoujielun";
        readMp3Files(directoryPath);
    }

    public static void readMp3Files(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            System.err.println("Directory does not exist: " + directoryPath);
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            System.err.println("Failed to read files from directory: " + directoryPath);
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                readMp3Files(file.getAbsolutePath());
            } else if (file.getName().endsWith(".mp3")) {
                System.out.println(file.getAbsolutePath());
            }
        }
    }
}
