package com.worldpay.LLP.pgp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by georgianc on 12.12.2017.
 */
public class PGPEncrypt {

    private static final String LOCATION = "/home/georgianc/techs/sfg_sym/test.in";
    private static final String RECIPIENT = "llp@llp.com";

    public static void encryptPGP() throws IOException {
        List<String> encryptCMD = new ArrayList();
        encryptCMD.add("gpg");
        encryptCMD.add("--recipient");
        encryptCMD.add("llp@llp.com");
        encryptCMD.add("--encrypt");
        encryptCMD.add("test.in");
        Process process = Runtime.getRuntime().exec((String[])encryptCMD.toArray());
    }

    public static void encryptRSAwithOpenSSL(String message) throws IOException {
        List<String> encryptCMD = new ArrayList();
        encryptCMD.add("echo " + message + " |");
        encryptCMD.add("openssl");
        encryptCMD.add("rsautl");
        encryptCMD.add("-encrypt");
        encryptCMD.add("-pubin");
        encryptCMD.add("inkey");
        encryptCMD.add("llpkey.pem");
        encryptCMD.add(">");
        encryptCMD.add("message.out");
        Process process = Runtime.getRuntime().exec((String[])encryptCMD.toArray());
    }

    static void printLines(BufferedReader in) throws IOException {
        String line = null;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
    }

    public static void main(String[] args) throws IOException {
        String gpgCommand = new StringBuilder().append("gpg").
                append(" --recipient ").
                append(RECIPIENT).
                append(" --trust-model always").
                append(" --encrypt ").
                append(LOCATION).
                toString();

        System.out.println("Command: " + gpgCommand);
        Process process = Runtime.getRuntime().exec(gpgCommand);

        BufferedReader is = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader es = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        printLines(is);
        printLines(es);

        System.out.println("done...");
    }

}
