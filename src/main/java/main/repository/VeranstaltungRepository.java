package main.repository;

import main.domain.Veranstaltung;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Veranstaltung entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VeranstaltungRepository extends JpaRepository<Veranstaltung, Long> {

}
