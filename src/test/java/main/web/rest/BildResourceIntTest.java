package main.web.rest;

import main.BudenzauberApp;

import main.domain.Bild;
import main.repository.BildRepository;
import main.service.BildService;
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
import java.util.List;


import static main.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BildResource REST controller.
 *
 * @see BildResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BudenzauberApp.class)
public class BildResourceIntTest {

    private static final Boolean DEFAULT_INVISIBLE = false;
    private static final Boolean UPDATED_INVISIBLE = true;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_THUMB_URL = "AAAAAAAAAA";
    private static final String UPDATED_THUMB_URL = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private BildRepository bildRepository;

    @Autowired
    private BildService bildService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBildMockMvc;

    private Bild bild;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BildResource bildResource = new BildResource(bildService);
        this.restBildMockMvc = MockMvcBuilders.standaloneSetup(bildResource)
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
    public static Bild createEntity(EntityManager em) {
        Bild bild = new Bild()
            .invisible(DEFAULT_INVISIBLE)
            .title(DEFAULT_TITLE)
            .thumbUrl(DEFAULT_THUMB_URL)
            .url(DEFAULT_URL);
        return bild;
    }

    @Before
    public void initTest() {
        bild = createEntity(em);
    }

    @Test
    @Transactional
    public void createBild() throws Exception {
        int databaseSizeBeforeCreate = bildRepository.findAll().size();

        // Create the Bild
        restBildMockMvc.perform(post("/api/bilds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bild)))
            .andExpect(status().isCreated());

        // Validate the Bild in the database
        List<Bild> bildList = bildRepository.findAll();
        assertThat(bildList).hasSize(databaseSizeBeforeCreate + 1);
        Bild testBild = bildList.get(bildList.size() - 1);
        assertThat(testBild.isInvisible()).isEqualTo(DEFAULT_INVISIBLE);
        assertThat(testBild.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBild.getThumbUrl()).isEqualTo(DEFAULT_THUMB_URL);
        assertThat(testBild.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createBildWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bildRepository.findAll().size();

        // Create the Bild with an existing ID
        bild.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBildMockMvc.perform(post("/api/bilds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bild)))
            .andExpect(status().isBadRequest());

        // Validate the Bild in the database
        List<Bild> bildList = bildRepository.findAll();
        assertThat(bildList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBilds() throws Exception {
        // Initialize the database
        bildRepository.saveAndFlush(bild);

        // Get all the bildList
        restBildMockMvc.perform(get("/api/bilds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bild.getId().intValue())))
            .andExpect(jsonPath("$.[*].invisible").value(hasItem(DEFAULT_INVISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].thumbUrl").value(hasItem(DEFAULT_THUMB_URL.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }
    
    @Test
    @Transactional
    public void getBild() throws Exception {
        // Initialize the database
        bildRepository.saveAndFlush(bild);

        // Get the bild
        restBildMockMvc.perform(get("/api/bilds/{id}", bild.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bild.getId().intValue()))
            .andExpect(jsonPath("$.invisible").value(DEFAULT_INVISIBLE.booleanValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.thumbUrl").value(DEFAULT_THUMB_URL.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBild() throws Exception {
        // Get the bild
        restBildMockMvc.perform(get("/api/bilds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBild() throws Exception {
        // Initialize the database
        bildService.save(bild);

        int databaseSizeBeforeUpdate = bildRepository.findAll().size();

        // Update the bild
        Bild updatedBild = bildRepository.findById(bild.getId()).get();
        // Disconnect from session so that the updates on updatedBild are not directly saved in db
        em.detach(updatedBild);
        updatedBild
            .invisible(UPDATED_INVISIBLE)
            .title(UPDATED_TITLE)
            .thumbUrl(UPDATED_THUMB_URL)
            .url(UPDATED_URL);

        restBildMockMvc.perform(put("/api/bilds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBild)))
            .andExpect(status().isOk());

        // Validate the Bild in the database
        List<Bild> bildList = bildRepository.findAll();
        assertThat(bildList).hasSize(databaseSizeBeforeUpdate);
        Bild testBild = bildList.get(bildList.size() - 1);
        assertThat(testBild.isInvisible()).isEqualTo(UPDATED_INVISIBLE);
        assertThat(testBild.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBild.getThumbUrl()).isEqualTo(UPDATED_THUMB_URL);
        assertThat(testBild.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingBild() throws Exception {
        int databaseSizeBeforeUpdate = bildRepository.findAll().size();

        // Create the Bild

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBildMockMvc.perform(put("/api/bilds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bild)))
            .andExpect(status().isBadRequest());

        // Validate the Bild in the database
        List<Bild> bildList = bildRepository.findAll();
        assertThat(bildList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBild() throws Exception {
        // Initialize the database
        bildService.save(bild);

        int databaseSizeBeforeDelete = bildRepository.findAll().size();

        // Get the bild
        restBildMockMvc.perform(delete("/api/bilds/{id}", bild.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bild> bildList = bildRepository.findAll();
        assertThat(bildList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bild.class);
        Bild bild1 = new Bild();
        bild1.setId(1L);
        Bild bild2 = new Bild();
        bild2.setId(bild1.getId());
        assertThat(bild1).isEqualTo(bild2);
        bild2.setId(2L);
        assertThat(bild1).isNotEqualTo(bild2);
        bild1.setId(null);
        assertThat(bild1).isNotEqualTo(bild2);
    }
}
