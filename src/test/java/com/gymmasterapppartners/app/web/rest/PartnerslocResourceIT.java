package com.gymmasterapppartners.app.web.rest;

import com.gymmasterapppartners.app.GymmasterapppartnersApp;
import com.gymmasterapppartners.app.domain.Partnersloc;
import com.gymmasterapppartners.app.repository.PartnerslocRepository;
import com.gymmasterapppartners.app.repository.search.PartnerslocSearchRepository;
import com.gymmasterapppartners.app.service.PartnerslocService;
import com.gymmasterapppartners.app.service.dto.PartnerslocDTO;
import com.gymmasterapppartners.app.service.mapper.PartnerslocMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gymmasterapppartners.app.domain.enumeration.regionEnum;
/**
 * Integration tests for the {@link PartnerslocResource} REST controller.
 */
@SpringBootTest(classes = GymmasterapppartnersApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartnerslocResourceIT {

    private static final regionEnum DEFAULT_REGION = regionEnum.C;
    private static final regionEnum UPDATED_REGION = regionEnum.E;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_POSTAL_CODE = 6;
    private static final Integer UPDATED_POSTAL_CODE = 5;

    private static final Instant DEFAULT_OPEN_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPEN_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CLOSE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLOSE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_POC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_POC_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_POC_NO = 1;
    private static final Integer UPDATED_POC_NO = 2;

    private static final String DEFAULT_POC_EMAIL = "q@MwcvVW.9xCgRh.y1nEf.NF_g.lR.68Ti";
    private static final String UPDATED_POC_EMAIL = "4M@-Zz.w3W6.A.s-.N4F.gI.fo";

    @Autowired
    private PartnerslocRepository partnerslocRepository;

    @Autowired
    private PartnerslocMapper partnerslocMapper;

    @Autowired
    private PartnerslocService partnerslocService;

    /**
     * This repository is mocked in the com.gymmasterapppartners.app.repository.search test package.
     *
     * @see com.gymmasterapppartners.app.repository.search.PartnerslocSearchRepositoryMockConfiguration
     */
    @Autowired
    private PartnerslocSearchRepository mockPartnerslocSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartnerslocMockMvc;

    private Partnersloc partnersloc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partnersloc createEntity(EntityManager em) {
        Partnersloc partnersloc = new Partnersloc()
            .region(DEFAULT_REGION)
            .address(DEFAULT_ADDRESS)
            .postalCode(DEFAULT_POSTAL_CODE)
            .openTime(DEFAULT_OPEN_TIME)
            .closeTime(DEFAULT_CLOSE_TIME)
            .pocName(DEFAULT_POC_NAME)
            .pocNo(DEFAULT_POC_NO)
            .pocEmail(DEFAULT_POC_EMAIL);
        return partnersloc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partnersloc createUpdatedEntity(EntityManager em) {
        Partnersloc partnersloc = new Partnersloc()
            .region(UPDATED_REGION)
            .address(UPDATED_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .openTime(UPDATED_OPEN_TIME)
            .closeTime(UPDATED_CLOSE_TIME)
            .pocName(UPDATED_POC_NAME)
            .pocNo(UPDATED_POC_NO)
            .pocEmail(UPDATED_POC_EMAIL);
        return partnersloc;
    }

    @BeforeEach
    public void initTest() {
        partnersloc = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartnersloc() throws Exception {
        int databaseSizeBeforeCreate = partnerslocRepository.findAll().size();
        // Create the Partnersloc
        PartnerslocDTO partnerslocDTO = partnerslocMapper.toDto(partnersloc);
        restPartnerslocMockMvc.perform(post("/api/partnerslocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnerslocDTO)))
            .andExpect(status().isCreated());

        // Validate the Partnersloc in the database
        List<Partnersloc> partnerslocList = partnerslocRepository.findAll();
        assertThat(partnerslocList).hasSize(databaseSizeBeforeCreate + 1);
        Partnersloc testPartnersloc = partnerslocList.get(partnerslocList.size() - 1);
        assertThat(testPartnersloc.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testPartnersloc.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPartnersloc.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testPartnersloc.getOpenTime()).isEqualTo(DEFAULT_OPEN_TIME);
        assertThat(testPartnersloc.getCloseTime()).isEqualTo(DEFAULT_CLOSE_TIME);
        assertThat(testPartnersloc.getPocName()).isEqualTo(DEFAULT_POC_NAME);
        assertThat(testPartnersloc.getPocNo()).isEqualTo(DEFAULT_POC_NO);
        assertThat(testPartnersloc.getPocEmail()).isEqualTo(DEFAULT_POC_EMAIL);

        // Validate the Partnersloc in Elasticsearch
        verify(mockPartnerslocSearchRepository, times(1)).save(testPartnersloc);
    }

    @Test
    @Transactional
    public void createPartnerslocWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partnerslocRepository.findAll().size();

        // Create the Partnersloc with an existing ID
        partnersloc.setId(1L);
        PartnerslocDTO partnerslocDTO = partnerslocMapper.toDto(partnersloc);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerslocMockMvc.perform(post("/api/partnerslocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnerslocDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Partnersloc in the database
        List<Partnersloc> partnerslocList = partnerslocRepository.findAll();
        assertThat(partnerslocList).hasSize(databaseSizeBeforeCreate);

        // Validate the Partnersloc in Elasticsearch
        verify(mockPartnerslocSearchRepository, times(0)).save(partnersloc);
    }


    @Test
    @Transactional
    public void checkRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerslocRepository.findAll().size();
        // set the field null
        partnersloc.setRegion(null);

        // Create the Partnersloc, which fails.
        PartnerslocDTO partnerslocDTO = partnerslocMapper.toDto(partnersloc);


        restPartnerslocMockMvc.perform(post("/api/partnerslocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnerslocDTO)))
            .andExpect(status().isBadRequest());

        List<Partnersloc> partnerslocList = partnerslocRepository.findAll();
        assertThat(partnerslocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerslocRepository.findAll().size();
        // set the field null
        partnersloc.setAddress(null);

        // Create the Partnersloc, which fails.
        PartnerslocDTO partnerslocDTO = partnerslocMapper.toDto(partnersloc);


        restPartnerslocMockMvc.perform(post("/api/partnerslocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnerslocDTO)))
            .andExpect(status().isBadRequest());

        List<Partnersloc> partnerslocList = partnerslocRepository.findAll();
        assertThat(partnerslocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerslocRepository.findAll().size();
        // set the field null
        partnersloc.setPostalCode(null);

        // Create the Partnersloc, which fails.
        PartnerslocDTO partnerslocDTO = partnerslocMapper.toDto(partnersloc);


        restPartnerslocMockMvc.perform(post("/api/partnerslocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnerslocDTO)))
            .andExpect(status().isBadRequest());

        List<Partnersloc> partnerslocList = partnerslocRepository.findAll();
        assertThat(partnerslocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpenTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerslocRepository.findAll().size();
        // set the field null
        partnersloc.setOpenTime(null);

        // Create the Partnersloc, which fails.
        PartnerslocDTO partnerslocDTO = partnerslocMapper.toDto(partnersloc);


        restPartnerslocMockMvc.perform(post("/api/partnerslocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnerslocDTO)))
            .andExpect(status().isBadRequest());

        List<Partnersloc> partnerslocList = partnerslocRepository.findAll();
        assertThat(partnerslocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCloseTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerslocRepository.findAll().size();
        // set the field null
        partnersloc.setCloseTime(null);

        // Create the Partnersloc, which fails.
        PartnerslocDTO partnerslocDTO = partnerslocMapper.toDto(partnersloc);


        restPartnerslocMockMvc.perform(post("/api/partnerslocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnerslocDTO)))
            .andExpect(status().isBadRequest());

        List<Partnersloc> partnerslocList = partnerslocRepository.findAll();
        assertThat(partnerslocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPocNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerslocRepository.findAll().size();
        // set the field null
        partnersloc.setPocName(null);

        // Create the Partnersloc, which fails.
        PartnerslocDTO partnerslocDTO = partnerslocMapper.toDto(partnersloc);


        restPartnerslocMockMvc.perform(post("/api/partnerslocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnerslocDTO)))
            .andExpect(status().isBadRequest());

        List<Partnersloc> partnerslocList = partnerslocRepository.findAll();
        assertThat(partnerslocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPocNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerslocRepository.findAll().size();
        // set the field null
        partnersloc.setPocNo(null);

        // Create the Partnersloc, which fails.
        PartnerslocDTO partnerslocDTO = partnerslocMapper.toDto(partnersloc);


        restPartnerslocMockMvc.perform(post("/api/partnerslocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnerslocDTO)))
            .andExpect(status().isBadRequest());

        List<Partnersloc> partnerslocList = partnerslocRepository.findAll();
        assertThat(partnerslocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPocEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerslocRepository.findAll().size();
        // set the field null
        partnersloc.setPocEmail(null);

        // Create the Partnersloc, which fails.
        PartnerslocDTO partnerslocDTO = partnerslocMapper.toDto(partnersloc);


        restPartnerslocMockMvc.perform(post("/api/partnerslocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnerslocDTO)))
            .andExpect(status().isBadRequest());

        List<Partnersloc> partnerslocList = partnerslocRepository.findAll();
        assertThat(partnerslocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartnerslocs() throws Exception {
        // Initialize the database
        partnerslocRepository.saveAndFlush(partnersloc);

        // Get all the partnerslocList
        restPartnerslocMockMvc.perform(get("/api/partnerslocs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnersloc.getId().intValue())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].openTime").value(hasItem(DEFAULT_OPEN_TIME.toString())))
            .andExpect(jsonPath("$.[*].closeTime").value(hasItem(DEFAULT_CLOSE_TIME.toString())))
            .andExpect(jsonPath("$.[*].pocName").value(hasItem(DEFAULT_POC_NAME)))
            .andExpect(jsonPath("$.[*].pocNo").value(hasItem(DEFAULT_POC_NO)))
            .andExpect(jsonPath("$.[*].pocEmail").value(hasItem(DEFAULT_POC_EMAIL)));
    }
    
    @Test
    @Transactional
    public void getPartnersloc() throws Exception {
        // Initialize the database
        partnerslocRepository.saveAndFlush(partnersloc);

        // Get the partnersloc
        restPartnerslocMockMvc.perform(get("/api/partnerslocs/{id}", partnersloc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partnersloc.getId().intValue()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.openTime").value(DEFAULT_OPEN_TIME.toString()))
            .andExpect(jsonPath("$.closeTime").value(DEFAULT_CLOSE_TIME.toString()))
            .andExpect(jsonPath("$.pocName").value(DEFAULT_POC_NAME))
            .andExpect(jsonPath("$.pocNo").value(DEFAULT_POC_NO))
            .andExpect(jsonPath("$.pocEmail").value(DEFAULT_POC_EMAIL));
    }
    @Test
    @Transactional
    public void getNonExistingPartnersloc() throws Exception {
        // Get the partnersloc
        restPartnerslocMockMvc.perform(get("/api/partnerslocs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartnersloc() throws Exception {
        // Initialize the database
        partnerslocRepository.saveAndFlush(partnersloc);

        int databaseSizeBeforeUpdate = partnerslocRepository.findAll().size();

        // Update the partnersloc
        Partnersloc updatedPartnersloc = partnerslocRepository.findById(partnersloc.getId()).get();
        // Disconnect from session so that the updates on updatedPartnersloc are not directly saved in db
        em.detach(updatedPartnersloc);
        updatedPartnersloc
            .region(UPDATED_REGION)
            .address(UPDATED_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .openTime(UPDATED_OPEN_TIME)
            .closeTime(UPDATED_CLOSE_TIME)
            .pocName(UPDATED_POC_NAME)
            .pocNo(UPDATED_POC_NO)
            .pocEmail(UPDATED_POC_EMAIL);
        PartnerslocDTO partnerslocDTO = partnerslocMapper.toDto(updatedPartnersloc);

        restPartnerslocMockMvc.perform(put("/api/partnerslocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnerslocDTO)))
            .andExpect(status().isOk());

        // Validate the Partnersloc in the database
        List<Partnersloc> partnerslocList = partnerslocRepository.findAll();
        assertThat(partnerslocList).hasSize(databaseSizeBeforeUpdate);
        Partnersloc testPartnersloc = partnerslocList.get(partnerslocList.size() - 1);
        assertThat(testPartnersloc.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testPartnersloc.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPartnersloc.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testPartnersloc.getOpenTime()).isEqualTo(UPDATED_OPEN_TIME);
        assertThat(testPartnersloc.getCloseTime()).isEqualTo(UPDATED_CLOSE_TIME);
        assertThat(testPartnersloc.getPocName()).isEqualTo(UPDATED_POC_NAME);
        assertThat(testPartnersloc.getPocNo()).isEqualTo(UPDATED_POC_NO);
        assertThat(testPartnersloc.getPocEmail()).isEqualTo(UPDATED_POC_EMAIL);

        // Validate the Partnersloc in Elasticsearch
        verify(mockPartnerslocSearchRepository, times(1)).save(testPartnersloc);
    }

    @Test
    @Transactional
    public void updateNonExistingPartnersloc() throws Exception {
        int databaseSizeBeforeUpdate = partnerslocRepository.findAll().size();

        // Create the Partnersloc
        PartnerslocDTO partnerslocDTO = partnerslocMapper.toDto(partnersloc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerslocMockMvc.perform(put("/api/partnerslocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnerslocDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Partnersloc in the database
        List<Partnersloc> partnerslocList = partnerslocRepository.findAll();
        assertThat(partnerslocList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Partnersloc in Elasticsearch
        verify(mockPartnerslocSearchRepository, times(0)).save(partnersloc);
    }

    @Test
    @Transactional
    public void deletePartnersloc() throws Exception {
        // Initialize the database
        partnerslocRepository.saveAndFlush(partnersloc);

        int databaseSizeBeforeDelete = partnerslocRepository.findAll().size();

        // Delete the partnersloc
        restPartnerslocMockMvc.perform(delete("/api/partnerslocs/{id}", partnersloc.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Partnersloc> partnerslocList = partnerslocRepository.findAll();
        assertThat(partnerslocList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Partnersloc in Elasticsearch
        verify(mockPartnerslocSearchRepository, times(1)).deleteById(partnersloc.getId());
    }

    @Test
    @Transactional
    public void searchPartnersloc() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        partnerslocRepository.saveAndFlush(partnersloc);
        when(mockPartnerslocSearchRepository.search(queryStringQuery("id:" + partnersloc.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(partnersloc), PageRequest.of(0, 1), 1));

        // Search the partnersloc
        restPartnerslocMockMvc.perform(get("/api/_search/partnerslocs?query=id:" + partnersloc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnersloc.getId().intValue())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].openTime").value(hasItem(DEFAULT_OPEN_TIME.toString())))
            .andExpect(jsonPath("$.[*].closeTime").value(hasItem(DEFAULT_CLOSE_TIME.toString())))
            .andExpect(jsonPath("$.[*].pocName").value(hasItem(DEFAULT_POC_NAME)))
            .andExpect(jsonPath("$.[*].pocNo").value(hasItem(DEFAULT_POC_NO)))
            .andExpect(jsonPath("$.[*].pocEmail").value(hasItem(DEFAULT_POC_EMAIL)));
    }
}
