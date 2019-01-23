package main.service.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Date;

import main.config.ApplicationProperties;
import main.config.Constants;
import main.domain.Event;
import main.domain.Photo;
import main.service.EventService;
import main.service.IStorageService;
import main.service.PhotoService;
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
    Date date = new Date();

    @Autowired
    private EventService eventService;

   @Autowired
   private PhotoService photoService;

    @Autowired
    public StorageService(ApplicationProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getFile().getUploadDir()).normalize().toAbsolutePath();

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
        Photo photo = new Photo();
        photo.setTitle(file.getOriginalFilename());
        photo.setUploaded(date.toInstant());
        photo.setEvent(eventService.findEventbyId(eventId));
        photoService.save(photo);
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String eventFolder = "/" + eventService.findEventbyId(eventId).getName()
            + "-"
            + eventService.findEventbyId(eventId).getErstellungsDatum().toString().substring(0, 10) + "/";

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

    public ArrayList<String> getAllImages(long eventId) {
        String eventFolder = "/" + eventService.findEventbyId(eventId).getName()
            + "-"
            + eventService.findEventbyId(eventId).getErstellungsDatum().toString().substring(0, 10) + "/";
        File directory = new File(this.fileStorageLocation.toString() + eventFolder);
        ArrayList<String> resultList = new ArrayList<String>(256);
        File[] f = directory.listFiles();

        for (File file : f) {
            if (file != null && file.getName().toLowerCase().endsWith(".jpg") && !file.getName().startsWith("tn_")) {
                try {
                    resultList.add(file.getCanonicalPath());
                }catch(IOException e){
                    new RuntimeException("getAllImages fehlgeschlagen");
                }
            }
        }
        if (resultList.size() > 0)
            return resultList;
        else
            return null;
    }




    public Resource loadFile(String filename, long eventId) {
        String eventFolder = "/" + eventService.findEventbyId(eventId).getName()
            + "-"
            + eventService.findEventbyId(eventId).getErstellungsDatum().toString().substring(0, 10) + "/";
        try {
            Path file = fileStorageLocation.resolve(eventFolder + filename);
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
        Path path = Paths.get(dirName);
        log.debug("Directory Name: " + name);

        if(!Files.isDirectory(path) && Files.notExists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException("Could not create Directory");
            }
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
