package main.web.rest;

import com.codahale.metrics.annotation.Timed;
import main.domain.Photo;
import main.service.IStorageService;
import main.service.PhotoService;
import main.service.storage.UploadFileResponse;
import main.web.rest.errors.BadRequestAlertException;
import main.web.rest.util.HeaderUtil;
import main.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URISyntaxException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

/**
 * REST controller for managing Photo.
 */
@RestController
@RequestMapping("/api")
public class PhotoResource {

    private static final Logger logger = LoggerFactory.getLogger(PhotoResource.class);

    @Autowired
    IStorageService storageService;

    PhotoService photoService;


    /**
     * POST  /photos : Upload photos to filesystem.
     *
     */
    @PostMapping("/photos/{id}")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("id") long id) {
        String fileName = storageService.storeFile(file, id);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/downloadFile/")
            .path(fileName)
            .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
            file.getContentType(), file.getSize());


    }


    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = storageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }

//    @GetMapping("/photos/getallfiles")
//    public ResponseEntity<List<String>> getListFiles(Model model) {
//        List<String> fileNames = files
//            .stream().map(fileName -> MvcUriComponentsBuilder
//                .fromMethodName(PhotoResource.class, "getFile", fileName).build().toString())
//            .collect(Collectors.toList());
//
//        return ResponseEntity.ok().body(fileNames);
//    }
//
//    @GetMapping("/photos/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<org.springframework.core.io.Resource> getFile(@PathVariable String filename) {
//        Resource file = storageService.loadFile(filename);
//        return ResponseEntity.ok()
//            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//            .body(file);
//    }
//
//    /**
//     * PUT  /photos : Updates an existing photo.
//     *
//     * @param photo the photo to update
//     * @return the ResponseEntity with status 200 (OK) and with body the updated photo,
//     * or with status 400 (Bad Request) if the photo is not valid,
//     * or with status 500 (Internal Server Error) if the photo couldn't be updated
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @PutMapping("/photos")
//    @Timed
//    public ResponseEntity<Photo> updatePhoto(@Valid @RequestBody Photo photo) throws URISyntaxException {
//        log.debug("REST request to update Photo : {}", photo);
//        if (photo.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        Photo result = photoService.save(photo);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, photo.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * GET  /photos : get all the photos.
//     *
//     * @param pageable the pagination information
//     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
//     * @return the ResponseEntity with status 200 (OK) and the list of photos in body
//     */
//    @GetMapping("/photos")
//    @Timed
//    public ResponseEntity<List<Photo>> getAllPhotos(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
//        log.debug("REST request to get a page of Photos");
//        Page<Photo> page;
//        if (eagerload) {
//            page = photoService.findAllWithEagerRelationships(pageable);
//        } else {
//            page = photoService.findAll(pageable);
//        }
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/photos?eagerload=%b", eagerload));
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
//    }
//
//    /**
//     * GET  /photos/:id : get the "id" photo.
//     *
//     * @param id the id of the photo to retrieve
//     * @return the ResponseEntity with status 200 (OK) and with body the photo, or with status 404 (Not Found)
//     */
//    @GetMapping("/photos/{id}")
//    @Timed
//    public ResponseEntity<Photo> getPhoto(@PathVariable Long id) {
//        log.debug("REST request to get Photo : {}", id);
//        Optional<Photo> photo = photoService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(photo);
//    }
//
//    /**
//     * DELETE  /photos/:id : delete the "id" photo.
//     *
//     * @param id the id of the photo to delete
//     * @return the ResponseEntity with status 200 (OK)
//     */
//    @DeleteMapping("/photos/{id}")
//    @Timed
//    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
//        log.debug("REST request to delete Photo : {}", id);
//        photoService.delete(id);
//        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
//    }
}
