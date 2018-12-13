package main.repository;

import main.domain.Bild;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bild entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BildRepository extends JpaRepository<Bild, Long> {

}
