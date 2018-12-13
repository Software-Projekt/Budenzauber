package main.web.rest;

import main.BudenzauberApp;

import main.domain.Veranstaltung;
import main.repository.VeranstaltungRepository;
import main.service.VeranstaltungService;
import main.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static main.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VeranstaltungResource REST controller.
 *
 * @see VeranstaltungResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BudenzauberApp.class)
public class VeranstaltungResourceIntTest {

    private static final Instant DEFAULT_ARCHIVIERUNGS_DATUM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ARCHIVIERUNGS_DATUM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ERSTELLUNGS_DATUM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ERSTELLUNGS_DATUM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_FREIGEGEBEN = false;
    private static final Boolean UPDATED_FREIGEGEBEN = true;

    private static final String DEFAULT_GRUSSWORT = "AAAAAAAAAA";
    private static final String UPDATED_GRUSSWORT = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private VeranstaltungRepository veranstaltungRepository;

    @Autowired
    private VeranstaltungService veranstaltungService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVeranstaltungMockMvc;

    private Veranstaltung veranstaltung;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VeranstaltungResource veranstaltungResource = new VeranstaltungResource(veranstaltungService);
        this.restVeranstaltungMockMvc = MockMvcBuilders.standaloneSetup(veranstaltungResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Veranstaltung createEntity(EntityManager em) {
        Veranstaltung veranstaltung = new Veranstaltung()
            .archivierungsDatum(DEFAULT_ARCHIVIERUNGS_DATUM)
            .erstellungsDatum(DEFAULT_ERSTELLUNGS_DATUM)
            .freigegeben(DEFAULT_FREIGEGEBEN)
            .grusswort(DEFAULT_GRUSSWORT)
            .name(DEFAULT_NAME);
        return veranstaltung;
    }

    @Before
    public void initTest() {
        veranstaltung = createEntity(em);
    }

    @Test
    @Transactional
    public void createVeranstaltung() throws Exception {
        int databaseSizeBeforeCreate = veranstaltungRepository.findAll().size();

        // Create the Veranstaltung
        restVeranstaltungMockMvc.perform(post("/api/veranstaltungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veranstaltung)))
            .andExpect(status().isCreated());

        // Validate the Veranstaltung in the database
        List<Veranstaltung> veranstaltungList = veranstaltungRepository.findAll();
        assertThat(veranstaltungList).hasSize(databaseSizeBeforeCreate + 1);
        Veranstaltung testVeranstaltung = veranstaltungList.get(veranstaltungList.size() - 1);
        assertThat(testVeranstaltung.getArchivierungsDatum()).isEqualTo(DEFAULT_ARCHIVIERUNGS_DATUM);
        assertThat(testVeranstaltung.getErstellungsDatum()).isEqualTo(DEFAULT_ERSTELLUNGS_DATUM);
        assertThat(testVeranstaltung.isFreigegeben()).isEqualTo(DEFAULT_FREIGEGEBEN);
        assertThat(testVeranstaltung.getGrusswort()).isEqualTo(DEFAULT_GRUSSWORT);
        assertThat(testVeranstaltung.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createVeranstaltungWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = veranstaltungRepository.findAll().size();

        // Create the Veranstaltung with an existing ID
        veranstaltung.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVeranstaltungMockMvc.perform(post("/api/veranstaltungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veranstaltung)))
            .andExpect(status().isBadRequest());

        // Validate the Veranstaltung in the database
        List<Veranstaltung> veranstaltungList = veranstaltungRepository.findAll();
        assertThat(veranstaltungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVeranstaltungs() throws Exception {
        // Initialize the database
        veranstaltungRepository.saveAndFlush(veranstaltung);

        // Get all the veranstaltungList
        restVeranstaltungMockMvc.perform(get("/api/veranstaltungs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(veranstaltung.getId().intValue())))
            .andExpect(jsonPath("$.[*].archivierungsDatum").value(hasItem(DEFAULT_ARCHIVIERUNGS_DATUM.toString())))
            .andExpect(jsonPath("$.[*].erstellungsDatum").value(hasItem(DEFAULT_ERSTELLUNGS_DATUM.toString())))
            .andExpect(jsonPath("$.[*].freigegeben").value(hasItem(DEFAULT_FREIGEGEBEN.booleanValue())))
            .andExpect(jsonPath("$.[*].grusswort").value(hasItem(DEFAULT_GRUSSWORT.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getVeranstaltung() throws Exception {
        // Initialize the database
        veranstaltungRepository.saveAndFlush(veranstaltung);

        // Get the veranstaltung
        restVeranstaltungMockMvc.perform(get("/api/veranstaltungs/{id}", veranstaltung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(veranstaltung.getId().intValue()))
            .andExpect(jsonPath("$.archivierungsDatum").value(DEFAULT_ARCHIVIERUNGS_DATUM.toString()))
            .andExpect(jsonPath("$.erstellungsDatum").value(DEFAULT_ERSTELLUNGS_DATUM.toString()))
            .andExpect(jsonPath("$.freigegeben").value(DEFAULT_FREIGEGEBEN.booleanValue()))
            .andExpect(jsonPath("$.grusswort").value(DEFAULT_GRUSSWORT.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVeranstaltung() throws Exception {
        // Get the veranstaltung
        restVeranstaltungMockMvc.perform(get("/api/veranstaltungs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVeranstaltung() throws Exception {
        // Initialize the database
        veranstaltungService.save(veranstaltung);

        int databaseSizeBeforeUpdate = veranstaltungRepository.findAll().size();

        // Update the veranstaltung
        Veranstaltung updatedVeranstaltung = veranstaltungRepository.findById(veranstaltung.getId()).get();
        // Disconnect from session so that the updates on updatedVeranstaltung are not directly saved in db
        em.detach(updatedVeranstaltung);
        updatedVeranstaltung
            .archivierungsDatum(UPDATED_ARCHIVIERUNGS_DATUM)
            .erstellungsDatum(UPDATED_ERSTELLUNGS_DATUM)
            .freigegeben(UPDATED_FREIGEGEBEN)
            .grusswort(UPDATED_GRUSSWORT)
            .name(UPDATED_NAME);

        restVeranstaltungMockMvc.perform(put("/api/veranstaltungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVeranstaltung)))
            .andExpect(status().isOk());

        // Validate the Veranstaltung in the database
        List<Veranstaltung> veranstaltungList = veranstaltungRepository.findAll();
        assertThat(veranstaltungList).hasSize(databaseSizeBeforeUpdate);
        Veranstaltung testVeranstaltung = veranstaltungList.get(veranstaltungList.size() - 1);
        assertThat(testVeranstaltung.getArchivierungsDatum()).isEqualTo(UPDATED_ARCHIVIERUNGS_DATUM);
        assertThat(testVeranstaltung.getErstellungsDatum()).isEqualTo(UPDATED_ERSTELLUNGS_DATUM);
        assertThat(testVeranstaltung.isFreigegeben()).isEqualTo(UPDATED_FREIGEGEBEN);
        assertThat(testVeranstaltung.getGrusswort()).isEqualTo(UPDATED_GRUSSWORT);
        assertThat(testVeranstaltung.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingVeranstaltung() throws Exception {
        int databaseSizeBeforeUpdate = veranstaltungRepository.findAll().size();

        // Create the Veranstaltung

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVeranstaltungMockMvc.perform(put("/api/veranstaltungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veranstaltung)))
            .andExpect(status().isBadRequest());

        // Validate the Veranstaltung in the database
        List<Veranstaltung> veranstaltungList = veranstaltungRepository.findAll();
        assertThat(veranstaltungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVeranstaltung() throws Exception {
        // Initialize the database
        veranstaltungService.save(veranstaltung);

        int databaseSizeBeforeDelete = veranstaltungRepository.findAll().size();

        // Get the veranstaltung
        restVeranstaltungMockMvc.perform(delete("/api/veranstaltungs/{id}", veranstaltung.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Veranstaltung> veranstaltungList = veranstaltungRepository.findAll();
        assertThat(veranstaltungList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Veranstaltung.class);
        Veranstaltung veranstaltung1 = new Veranstaltung();
        veranstaltung1.setId(1L);
        Veranstaltung veranstaltung2 = new Veranstaltung();
        veranstaltung2.setId(veranstaltung1.getId());
        assertThat(veranstaltung1).isEqualTo(veranstaltung2);
        veranstaltung2.setId(2L);
        assertThat(veranstaltung1).isNotEqualTo(veranstaltung2);
        veranstaltung1.setId(null);
        assertThat(veranstaltung1).isNotEqualTo(veranstaltung2);
    }
}
