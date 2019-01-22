package main.repository;

import main.domain.Event;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Event entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select event from Event event where event.user.login = ?#{principal.username}")
    List<Event> findByUserIsCurrentUser();

    @Query("select event from Event event where event.id =:id")
    Event findEventById(@Param("id") Long id);
}
