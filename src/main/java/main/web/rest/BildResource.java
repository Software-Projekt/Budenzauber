package main.web.rest;

import com.codahale.metrics.annotation.Timed;
import main.domain.Bild;
import main.service.BildService;
import main.web.rest.errors.BadRequestAlertException;
import main.web.rest.util.HeaderUtil;
import main.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Bild.
 */
@RestController
@RequestMapping("/api")
public class BildResource {

    private final Logger log = LoggerFactory.getLogger(BildResource.class);

    private static final String ENTITY_NAME = "bild";

    private final BildService bildService;

    public BildResource(BildService bildService) {
        this.bildService = bildService;
    }

    /**
     * POST  /bilds : Create a new bild.
     *
     * @param bild the bild to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bild, or with status 400 (Bad Request) if the bild has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bilds")
    @Timed
    public ResponseEntity<Bild> createBild(@RequestBody Bild bild) throws URISyntaxException {
        log.debug("REST request to save Bild : {}", bild);
        if (bild.getId() != null) {
            throw new BadRequestAlertException("A new bild cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bild result = bildService.save(bild);
        return ResponseEntity.created(new URI("/api/bilds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bilds : Updates an existing bild.
     *
     * @param bild the bild to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bild,
     * or with status 400 (Bad Request) if the bild is not valid,
     * or with status 500 (Internal Server Error) if the bild couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bilds")
    @Timed
    public ResponseEntity<Bild> updateBild(@RequestBody Bild bild) throws URISyntaxException {
        log.debug("REST request to update Bild : {}", bild);
        if (bild.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bild result = bildService.save(bild);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bild.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bilds : get all the bilds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bilds in body
     */
    @GetMapping("/bilds")
    @Timed
    public ResponseEntity<List<Bild>> getAllBilds(Pageable pageable) {
        log.debug("REST request to get a page of Bilds");
        Page<Bild> page = bildService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bilds");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /bilds/:id : get the "id" bild.
     *
     * @param id the id of the bild to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bild, or with status 404 (Not Found)
     */
    @GetMapping("/bilds/{id}")
    @Timed
    public ResponseEntity<Bild> getBild(@PathVariable Long id) {
        log.debug("REST request to get Bild : {}", id);
        Optional<Bild> bild = bildService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bild);
    }

    /**
     * DELETE  /bilds/:id : delete the "id" bild.
     *
     * @param id the id of the bild to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bilds/{id}")
    @Timed
    public ResponseEntity<Void> deleteBild(@PathVariable Long id) {
        log.debug("REST request to delete Bild : {}", id);
        bildService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
