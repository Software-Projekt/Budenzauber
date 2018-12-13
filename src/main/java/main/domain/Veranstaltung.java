package main.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Veranstaltung.
 */
@Entity
@Table(name = "veranstaltung")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Veranstaltung implements Serializable {

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

    @Column(name = "grusswort")
    private String grusswort;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "veranstaltung")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Bild> bilds = new HashSet<>();
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

    public Veranstaltung archivierungsDatum(Instant archivierungsDatum) {
        this.archivierungsDatum = archivierungsDatum;
        return this;
    }

    public void setArchivierungsDatum(Instant archivierungsDatum) {
        this.archivierungsDatum = archivierungsDatum;
    }

    public Instant getErstellungsDatum() {
        return erstellungsDatum;
    }

    public Veranstaltung erstellungsDatum(Instant erstellungsDatum) {
        this.erstellungsDatum = erstellungsDatum;
        return this;
    }

    public void setErstellungsDatum(Instant erstellungsDatum) {
        this.erstellungsDatum = erstellungsDatum;
    }

    public Boolean isFreigegeben() {
        return freigegeben;
    }

    public Veranstaltung freigegeben(Boolean freigegeben) {
        this.freigegeben = freigegeben;
        return this;
    }

    public void setFreigegeben(Boolean freigegeben) {
        this.freigegeben = freigegeben;
    }

    public String getGrusswort() {
        return grusswort;
    }

    public Veranstaltung grusswort(String grusswort) {
        this.grusswort = grusswort;
        return this;
    }

    public void setGrusswort(String grusswort) {
        this.grusswort = grusswort;
    }

    public String getName() {
        return name;
    }

    public Veranstaltung name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Bild> getBilds() {
        return bilds;
    }

    public Veranstaltung bilds(Set<Bild> bilds) {
        this.bilds = bilds;
        return this;
    }

    public Veranstaltung addBild(Bild bild) {
        this.bilds.add(bild);
        bild.setVeranstaltung(this);
        return this;
    }

    public Veranstaltung removeBild(Bild bild) {
        this.bilds.remove(bild);
        bild.setVeranstaltung(null);
        return this;
    }

    public void setBilds(Set<Bild> bilds) {
        this.bilds = bilds;
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
        Veranstaltung veranstaltung = (Veranstaltung) o;
        if (veranstaltung.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), veranstaltung.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Veranstaltung{" +
            "id=" + getId() +
            ", archivierungsDatum='" + getArchivierungsDatum() + "'" +
            ", erstellungsDatum='" + getErstellungsDatum() + "'" +
            ", freigegeben='" + isFreigegeben() + "'" +
            ", grusswort='" + getGrusswort() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
