package main.service.impl;

import main.service.VeranstaltungService;
import main.domain.Veranstaltung;
import main.repository.VeranstaltungRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Veranstaltung.
 */
@Service
@Transactional
public class VeranstaltungServiceImpl implements VeranstaltungService {

    private final Logger log = LoggerFactory.getLogger(VeranstaltungServiceImpl.class);

    private final VeranstaltungRepository veranstaltungRepository;

    public VeranstaltungServiceImpl(VeranstaltungRepository veranstaltungRepository) {
        this.veranstaltungRepository = veranstaltungRepository;
    }

    /**
     * Save a veranstaltung.
     *
     * @param veranstaltung the entity to save
     * @return the persisted entity
     */
    @Override
    public Veranstaltung save(Veranstaltung veranstaltung) {
        log.debug("Request to save Veranstaltung : {}", veranstaltung);
        return veranstaltungRepository.save(veranstaltung);
    }

    /**
     * Get all the veranstaltungs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Veranstaltung> findAll(Pageable pageable) {
        log.debug("Request to get all Veranstaltungs");
        return veranstaltungRepository.findAll(pageable);
    }


    /**
     * Get one veranstaltung by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Veranstaltung> findOne(Long id) {
        log.debug("Request to get Veranstaltung : {}", id);
        return veranstaltungRepository.findById(id);
    }

    /**
     * Delete the veranstaltung by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Veranstaltung : {}", id);
        veranstaltungRepository.deleteById(id);
    }
}
