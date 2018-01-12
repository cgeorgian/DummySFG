package com.worldpay.LLP;

import java.io.File;

/**
 * Created by robertbu on 08.12.2017.
 */
public class FileProcessor {

    public void processFile(File file) {
        signFile(file);
        encryptFile(file);
    };

    private void signFile(File file) {
        System.out.println("Starting signing of the file: " + file.getName());
    }

    private void encryptFile(File file) {
        System.out.println("Starting encrypting of the file: " + file.getName());
    }
}
