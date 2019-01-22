package main.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface IStorageService {

    void store(MultipartFile file);

    Resource loadFile(String filename);

    void deleteAll();
    void init();

    /////

    String storeFile(MultipartFile file, long id);
    Resource loadFileAsResource(String fileName);

    void createFolder(String name);



}
