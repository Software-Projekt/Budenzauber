package main.web.rest;

import com.codahale.metrics.annotation.Timed;
import main.domain.Veranstaltung;
import main.service.VeranstaltungService;
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
 * REST controller for managing Veranstaltung.
 */
@RestController
@RequestMapping("/api")
public class VeranstaltungResource {

    private final Logger log = LoggerFactory.getLogger(VeranstaltungResource.class);

    private static final String ENTITY_NAME = "veranstaltung";

    private final VeranstaltungService veranstaltungService;

    public VeranstaltungResource(VeranstaltungService veranstaltungService) {
        this.veranstaltungService = veranstaltungService;
    }

    /**
     * POST  /veranstaltungs : Create a new veranstaltung.
     *
     * @param veranstaltung the veranstaltung to create
     * @return the ResponseEntity with status 201 (Created) and with body the new veranstaltung, or with status 400 (Bad Request) if the veranstaltung has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/veranstaltungs")
    @Timed
    public ResponseEntity<Veranstaltung> createVeranstaltung(@RequestBody Veranstaltung veranstaltung) throws URISyntaxException {
        log.debug("REST request to save Veranstaltung : {}", veranstaltung);
        if (veranstaltung.getId() != null) {
            throw new BadRequestAlertException("A new veranstaltung cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Veranstaltung result = veranstaltungService.save(veranstaltung);
        return ResponseEntity.created(new URI("/api/veranstaltungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /veranstaltungs : Updates an existing veranstaltung.
     *
     * @param veranstaltung the veranstaltung to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated veranstaltung,
     * or with status 400 (Bad Request) if the veranstaltung is not valid,
     * or with status 500 (Internal Server Error) if the veranstaltung couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/veranstaltungs")
    @Timed
    public ResponseEntity<Veranstaltung> updateVeranstaltung(@RequestBody Veranstaltung veranstaltung) throws URISyntaxException {
        log.debug("REST request to update Veranstaltung : {}", veranstaltung);
        if (veranstaltung.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Veranstaltung result = veranstaltungService.save(veranstaltung);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, veranstaltung.getId().toString()))
            .body(result);
    }

    /**
     * GET  /veranstaltungs : get all the veranstaltungs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of veranstaltungs in body
     */
    @GetMapping("/veranstaltungs")
    @Timed
    public ResponseEntity<List<Veranstaltung>> getAllVeranstaltungs(Pageable pageable) {
        log.debug("REST request to get a page of Veranstaltungs");
        Page<Veranstaltung> page = veranstaltungService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/veranstaltungs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /veranstaltungs/:id : get the "id" veranstaltung.
     *
     * @param id the id of the veranstaltung to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the veranstaltung, or with status 404 (Not Found)
     */
    @GetMapping("/veranstaltungs/{id}")
    @Timed
    public ResponseEntity<Veranstaltung> getVeranstaltung(@PathVariable Long id) {
        log.debug("REST request to get Veranstaltung : {}", id);
        Optional<Veranstaltung> veranstaltung = veranstaltungService.findOne(id);
        return ResponseUtil.wrapOrNotFound(veranstaltung);
    }

    /**
     * DELETE  /veranstaltungs/:id : delete the "id" veranstaltung.
     *
     * @param id the id of the veranstaltung to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/veranstaltungs/{id}")
    @Timed
    public ResponseEntity<Void> deleteVeranstaltung(@PathVariable Long id) {
        log.debug("REST request to delete Veranstaltung : {}", id);
        veranstaltungService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
