package com.worldpay.LLP;

import com.jcraft.jsch.JSchException;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * Created by robertbu on 08.12.2017.
 * Monitores a directory and if any new files occur or any changes occur on a file, the file will be sent to LLP SFTP
 */
public class DirectoryMonitor {

    private final FileProcessor fileProcessor = new FileProcessor();
    private final SFTPSender sftpSender = new SFTPSender();
    private Path dirToMonitor;
    private Path destinationDir;
    private Path sentDir;

    public DirectoryMonitor(Path dirToMonitor, Path destinationDir) {
        this.dirToMonitor = dirToMonitor;
        this.destinationDir = destinationDir;
    }

    public void watchDirectoryPath() {
        // Sanity check - Check if path is a folder
        try {
            Boolean isFolder = (Boolean) Files.getAttribute(dirToMonitor, "basic:isDirectory", NOFOLLOW_LINKS);
            if (!isFolder) {
                throw new IllegalArgumentException("Path: " + dirToMonitor + " is not a folder");
            }
        } catch (IOException ioe) {
            // Folder does not exists
            ioe.printStackTrace();
        }

        System.out.println("Waiting for files to be processed in the folder: " + dirToMonitor);

        // We obtain the file system of the Path
        FileSystem fs = dirToMonitor.getFileSystem();

        try (WatchService service = fs.newWatchService()) {

            // We register the path to the service
            // We watch for creation events
            dirToMonitor.register(service, ENTRY_CREATE);

            // Start the infinite polling loop
            WatchKey key;
            while (true) {
                key = service.take();

                // Dequeueing events
                Kind<?> kind;
                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                    // Get the type of the event
                    kind = watchEvent.kind();
                    Path newPath = ((WatchEvent<Path>) watchEvent).context();

                    if (OVERFLOW == kind) {
                        System.out.println("File" + newPath + " was lost or discarded");
                        continue; // loop
                    } else if (ENTRY_CREATE == kind) {
                        // Output
                        System.out.println("New file ready to be processed: " + newPath);
                        File file = newPath.toFile();
                        fileProcessor.processFile(file);
                        sftpSender.sendFile(dirToMonitor + "/" + file.getName());
                    }
                }

                if (!key.reset()) {
                    break; // loop
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } catch (JSchException e) {
            e.printStackTrace();
        }

    }
}
