package com.worldpay.LLP;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.Properties;

/**
 * Created by robertbu on 08.12.2017.
 */
public class SFTPSender {

    public void sendFile(String file) throws JSchException {
        String user = "robertbu";
        String psswd = "rb123";
        String host = "127.0.0.1";
        int port = 22;

        try {
            Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");

            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(psswd);
            session.setConfig(config);

            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");

            System.out.println("Creating SFTP Channel.");
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();

            System.out.println("SFTP Channel created. sending file: " + file);
            File f = new File(file);
            sftpChannel.put(new FileInputStream(file), f.getName());

            sftpChannel.exit();
            session.disconnect();
        }
        catch(JSchException | SftpException | IOException e) {
            System.out.println(">>> " + e);
        }
    }
}
