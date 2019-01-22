package main.service.impl;

import main.domain.User;
import main.service.EventService;
import main.domain.Event;
import main.repository.EventRepository;
import main.service.IStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.Optional;

/**
 * Service Implementation for managing Event.
 */
@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    private final EventRepository eventRepository;

    @Autowired
    IStorageService storageService;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Save a event.
     *
     * @param event the entity to save
     * @return the persisted entity
     */
    @Override
    public Event save(Event event) {
        Instant date = Instant.now();
        event.setErstellungsDatum(date);
        //event.setArchivierungsDatum(date.plus(Duration.ofDays(60)));
        log.debug("Request to save Event : {}", event);
        //Ordner für Bilder erstellen
        this.storageService.createFolder(event.getName() + "-" + event.getErstellungsDatum().toString().substring(0, 10));
        return eventRepository.save(event);
    }

    /**
     * Get all the events.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Event> findAll(Pageable pageable) {
        log.debug("Request to get all Events");
        return eventRepository.findAll(pageable);
    }


    /**
     * Get one event by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Event> findOne(Long id) {
        log.debug("Request to get Event : {}", id);
        return eventRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Event findEventbyId(Long id) {
        log.debug("Request to find Event : {}", id);
        return eventRepository.findEventById(id);
    }

    /**
     * Delete the event by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Event : {}", id);
        eventRepository.deleteById(id);
    }

}
