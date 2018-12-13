package main.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "bild")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bild implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invisible")
    private Boolean invisible;

    @Column(name = "title")
    private String title;

    @Column(name = "thumb_url")
    private String thumbUrl;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JsonIgnoreProperties("bilds")
    private Veranstaltung veranstaltung;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isInvisible() {
        return invisible;
    }

    public Bild invisible(Boolean invisible) {
        this.invisible = invisible;
        return this;
    }

    public void setInvisible(Boolean invisible) {
        this.invisible = invisible;
    }

    public String getTitle() {
        return title;
    }

    public Bild title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public Bild thumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
        return this;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getUrl() {
        return url;
    }

    public Bild url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Veranstaltung getVeranstaltung() {
        return veranstaltung;
    }

    public Bild veranstaltung(Veranstaltung veranstaltung) {
        this.veranstaltung = veranstaltung;
        return this;
    }

    public void setVeranstaltung(Veranstaltung veranstaltung) {
        this.veranstaltung = veranstaltung;
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
        Bild bild = (Bild) o;
        if (bild.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bild.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bild{" +
            "id=" + getId() +
            ", invisible='" + isInvisible() + "'" +
            ", title='" + getTitle() + "'" +
            ", thumbUrl='" + getThumbUrl() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
