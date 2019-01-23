package main.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


public interface IStorageService {


    void deleteAll();
    void init();

    /////

    String storeFile(MultipartFile file, long id);
    Resource loadFileAsResource(String fileName);

    void createFolder(String name);

    public ArrayList<String> getAllImages(long eventId);
    Resource loadFile(String filename, long eventId);



}
