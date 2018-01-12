package com.worldpay.LLP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class LlpApplication {

	public static void main(String[] args) {
		SpringApplication.run(LlpApplication.class, args);

//		File monitoredDir = new File("/home/robertbu/Desktop/monitoredDirectory");
//		File destinationDir = new File("/home/robertbu/Desktop/DestinationDirectory/INPUT");
		File monitoredDir = new File(args[0]);
		File destinationDir = new File(args[1]);

		DirectoryMonitor monitor = new DirectoryMonitor(monitoredDir.toPath(), destinationDir.toPath());
		monitor.watchDirectoryPath();
	}
}
