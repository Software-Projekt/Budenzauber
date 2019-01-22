package main.service.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

import main.config.ApplicationProperties;
import main.config.Constants;
import main.domain.Event;
import main.service.EventService;
import main.service.IStorageService;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;


@Service
public class StorageService implements IStorageService {

    private final Path fileStorageLocation;
    private final Logger log = LoggerFactory.getLogger(StorageService.class);

    @Autowired
    private EventService eventService;

    @Autowired
    public StorageService(ApplicationProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getFile().getUploadDir())
            .toAbsolutePath().normalize();

        //BUG: Overwrites /uploads
        /*if (Files.notExists(this.fileStorageLocation) && !Files.isDirectory(this.fileStorageLocation)) {
            try {
                Files.createDirectories(this.fileStorageLocation);
            } catch (Exception ex) {
                throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
            }
        }*/
    }

    public String storeFile(MultipartFile file, long eventId) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String eventFolder = "/" + eventService.findEventbyId(eventId).getName()
            + "-"
            + eventService.findEventbyId(eventId).getErstellungsDatum().toString().substring(0, 10) + "/";
        System.out.println(eventFolder);
        //String directoryName = StringUtils.cleanPath(eventService.findOne(eventId).get().getName());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }


            // Copy file to the target location (Replacing existing file with the same name)
            File path = new File(this.fileStorageLocation.toString() + eventFolder + fileName);
            //Path targetLocation = this.fileStorageLocation.resolve(fileName);
            BufferedImage image = ImageIO.read(file.getInputStream());
            ImageIO.write(image, "jpg", path);
            //Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }


    ///////////////////////////

    public void store(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.fileStorageLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }


    public Resource loadFile(String filename) {
        try {
            Path file = fileStorageLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public void createFolder(String name){
        String dirName = StringUtils.cleanPath(fileStorageLocation+ "/" + name);
        log.debug("Directory Name: " + name);
        try {
            Path path = Paths.get(dirName);
            log.debug(path.toString());
            Files.createDirectory(path);
        } catch (IOException e){
            throw new RuntimeException("Could not create Directory");
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(fileStorageLocation.toFile());
    }

    public void init() {
        if (Files.notExists(this.fileStorageLocation) && !Files.isDirectory(this.fileStorageLocation)) {
            try {
                Files.createDirectory(fileStorageLocation);
            } catch (Exception e) {
                throw new RuntimeException("Could not initialize storage!");
            }
        }
    }
}
