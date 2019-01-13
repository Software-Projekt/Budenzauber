package main.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "archivierungs_datum")
    private Instant archivierungsDatum;

    @Column(name = "erstellungs_datum")
    private Instant erstellungsDatum;

    @Column(name = "freigegeben")
    private Boolean freigegeben;

    @Lob
    @Column(name = "grusswort")
    private String grusswort;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getArchivierungsDatum() {
        return archivierungsDatum;
    }

    public Event archivierungsDatum(Instant archivierungsDatum) {
        this.archivierungsDatum = archivierungsDatum;
        return this;
    }

    public void setArchivierungsDatum(Instant archivierungsDatum) {
        this.archivierungsDatum = archivierungsDatum;
    }

    public Instant getErstellungsDatum() {
        return erstellungsDatum;
    }

    public Event erstellungsDatum(Instant erstellungsDatum) {
        this.erstellungsDatum = erstellungsDatum;
        return this;
    }

    public void setErstellungsDatum(Instant erstellungsDatum) {
        this.erstellungsDatum = erstellungsDatum;
    }

    public Boolean isFreigegeben() {
        return freigegeben;
    }

    public Event freigegeben(Boolean freigegeben) {
        this.freigegeben = freigegeben;
        return this;
    }

    public void setFreigegeben(Boolean freigegeben) {
        this.freigegeben = freigegeben;
    }

    public String getGrusswort() {
        return grusswort;
    }

    public Event grusswort(String grusswort) {
        this.grusswort = grusswort;
        return this;
    }

    public void setGrusswort(String grusswort) {
        this.grusswort = grusswort;
    }

    public String getName() {
        return name;
    }

    public Event name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public Event user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        if (event.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", archivierungsDatum='" + getArchivierungsDatum() + "'" +
            ", erstellungsDatum='" + getErstellungsDatum() + "'" +
            ", freigegeben='" + isFreigegeben() + "'" +
            ", grusswort='" + getGrusswort() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
