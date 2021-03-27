package com.gymmasterapppartners.app.web.rest;

import com.gymmasterapppartners.app.GymmasterapppartnersApp;
import com.gymmasterapppartners.app.domain.Partners;
import com.gymmasterapppartners.app.repository.PartnersRepository;
import com.gymmasterapppartners.app.repository.search.PartnersSearchRepository;
import com.gymmasterapppartners.app.service.PartnersService;
import com.gymmasterapppartners.app.service.dto.PartnersDTO;
import com.gymmasterapppartners.app.service.mapper.PartnersMapper;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PartnersResource} REST controller.
 */
@SpringBootTest(classes = GymmasterapppartnersApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartnersResourceIT {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE_IND = false;
    private static final Boolean UPDATED_ACTIVE_IND = true;

    @Autowired
    private PartnersRepository partnersRepository;

    @Autowired
    private PartnersMapper partnersMapper;

    @Autowired
    private PartnersService partnersService;

    /**
     * This repository is mocked in the com.gymmasterapppartners.app.repository.search test package.
     *
     * @see com.gymmasterapppartners.app.repository.search.PartnersSearchRepositoryMockConfiguration
     */
    @Autowired
    private PartnersSearchRepository mockPartnersSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartnersMockMvc;

    private Partners partners;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partners createEntity(EntityManager em) {
        Partners partners = new Partners()
            .companyName(DEFAULT_COMPANY_NAME)
            .userName(DEFAULT_USER_NAME)
            .type(DEFAULT_TYPE)
            .activeInd(DEFAULT_ACTIVE_IND);
        return partners;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partners createUpdatedEntity(EntityManager em) {
        Partners partners = new Partners()
            .companyName(UPDATED_COMPANY_NAME)
            .userName(UPDATED_USER_NAME)
            .type(UPDATED_TYPE)
            .activeInd(UPDATED_ACTIVE_IND);
        return partners;
    }

    @BeforeEach
    public void initTest() {
        partners = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartners() throws Exception {
        int databaseSizeBeforeCreate = partnersRepository.findAll().size();
        // Create the Partners
        PartnersDTO partnersDTO = partnersMapper.toDto(partners);
        restPartnersMockMvc.perform(post("/api/partners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnersDTO)))
            .andExpect(status().isCreated());

        // Validate the Partners in the database
        List<Partners> partnersList = partnersRepository.findAll();
        assertThat(partnersList).hasSize(databaseSizeBeforeCreate + 1);
        Partners testPartners = partnersList.get(partnersList.size() - 1);
        assertThat(testPartners.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testPartners.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testPartners.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPartners.isActiveInd()).isEqualTo(DEFAULT_ACTIVE_IND);

        // Validate the Partners in Elasticsearch
        verify(mockPartnersSearchRepository, times(1)).save(testPartners);
    }

    @Test
    @Transactional
    public void createPartnersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partnersRepository.findAll().size();

        // Create the Partners with an existing ID
        partners.setId(1L);
        PartnersDTO partnersDTO = partnersMapper.toDto(partners);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnersMockMvc.perform(post("/api/partners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Partners in the database
        List<Partners> partnersList = partnersRepository.findAll();
        assertThat(partnersList).hasSize(databaseSizeBeforeCreate);

        // Validate the Partners in Elasticsearch
        verify(mockPartnersSearchRepository, times(0)).save(partners);
    }


    @Test
    @Transactional
    public void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnersRepository.findAll().size();
        // set the field null
        partners.setCompanyName(null);

        // Create the Partners, which fails.
        PartnersDTO partnersDTO = partnersMapper.toDto(partners);


        restPartnersMockMvc.perform(post("/api/partners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnersDTO)))
            .andExpect(status().isBadRequest());

        List<Partners> partnersList = partnersRepository.findAll();
        assertThat(partnersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnersRepository.findAll().size();
        // set the field null
        partners.setUserName(null);

        // Create the Partners, which fails.
        PartnersDTO partnersDTO = partnersMapper.toDto(partners);


        restPartnersMockMvc.perform(post("/api/partners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnersDTO)))
            .andExpect(status().isBadRequest());

        List<Partners> partnersList = partnersRepository.findAll();
        assertThat(partnersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnersRepository.findAll().size();
        // set the field null
        partners.setType(null);

        // Create the Partners, which fails.
        PartnersDTO partnersDTO = partnersMapper.toDto(partners);


        restPartnersMockMvc.perform(post("/api/partners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnersDTO)))
            .andExpect(status().isBadRequest());

        List<Partners> partnersList = partnersRepository.findAll();
        assertThat(partnersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIndIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnersRepository.findAll().size();
        // set the field null
        partners.setActiveInd(null);

        // Create the Partners, which fails.
        PartnersDTO partnersDTO = partnersMapper.toDto(partners);


        restPartnersMockMvc.perform(post("/api/partners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnersDTO)))
            .andExpect(status().isBadRequest());

        List<Partners> partnersList = partnersRepository.findAll();
        assertThat(partnersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartners() throws Exception {
        // Initialize the database
        partnersRepository.saveAndFlush(partners);

        // Get all the partnersList
        restPartnersMockMvc.perform(get("/api/partners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partners.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPartners() throws Exception {
        // Initialize the database
        partnersRepository.saveAndFlush(partners);

        // Get the partners
        restPartnersMockMvc.perform(get("/api/partners/{id}", partners.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partners.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.activeInd").value(DEFAULT_ACTIVE_IND.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPartners() throws Exception {
        // Get the partners
        restPartnersMockMvc.perform(get("/api/partners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartners() throws Exception {
        // Initialize the database
        partnersRepository.saveAndFlush(partners);

        int databaseSizeBeforeUpdate = partnersRepository.findAll().size();

        // Update the partners
        Partners updatedPartners = partnersRepository.findById(partners.getId()).get();
        // Disconnect from session so that the updates on updatedPartners are not directly saved in db
        em.detach(updatedPartners);
        updatedPartners
            .companyName(UPDATED_COMPANY_NAME)
            .userName(UPDATED_USER_NAME)
            .type(UPDATED_TYPE)
            .activeInd(UPDATED_ACTIVE_IND);
        PartnersDTO partnersDTO = partnersMapper.toDto(updatedPartners);

        restPartnersMockMvc.perform(put("/api/partners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnersDTO)))
            .andExpect(status().isOk());

        // Validate the Partners in the database
        List<Partners> partnersList = partnersRepository.findAll();
        assertThat(partnersList).hasSize(databaseSizeBeforeUpdate);
        Partners testPartners = partnersList.get(partnersList.size() - 1);
        assertThat(testPartners.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testPartners.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testPartners.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPartners.isActiveInd()).isEqualTo(UPDATED_ACTIVE_IND);

        // Validate the Partners in Elasticsearch
        verify(mockPartnersSearchRepository, times(1)).save(testPartners);
    }

    @Test
    @Transactional
    public void updateNonExistingPartners() throws Exception {
        int databaseSizeBeforeUpdate = partnersRepository.findAll().size();

        // Create the Partners
        PartnersDTO partnersDTO = partnersMapper.toDto(partners);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnersMockMvc.perform(put("/api/partners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partnersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Partners in the database
        List<Partners> partnersList = partnersRepository.findAll();
        assertThat(partnersList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Partners in Elasticsearch
        verify(mockPartnersSearchRepository, times(0)).save(partners);
    }

    @Test
    @Transactional
    public void deletePartners() throws Exception {
        // Initialize the database
        partnersRepository.saveAndFlush(partners);

        int databaseSizeBeforeDelete = partnersRepository.findAll().size();

        // Delete the partners
        restPartnersMockMvc.perform(delete("/api/partners/{id}", partners.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Partners> partnersList = partnersRepository.findAll();
        assertThat(partnersList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Partners in Elasticsearch
        verify(mockPartnersSearchRepository, times(1)).deleteById(partners.getId());
    }

    @Test
    @Transactional
    public void searchPartners() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        partnersRepository.saveAndFlush(partners);
        when(mockPartnersSearchRepository.search(queryStringQuery("id:" + partners.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(partners), PageRequest.of(0, 1), 1));

        // Search the partners
        restPartnersMockMvc.perform(get("/api/_search/partners?query=id:" + partners.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partners.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())));
    }
}
