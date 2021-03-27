package com.gymmasterapppartners.app.service;

import com.gymmasterapppartners.app.service.dto.PartnerslocDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.gymmasterapppartners.app.domain.Partnersloc}.
 */
public interface PartnerslocService {

    /**
     * Save a partnersloc.
     *
     * @param partnerslocDTO the entity to save.
     * @return the persisted entity.
     */
    PartnerslocDTO save(PartnerslocDTO partnerslocDTO);

    /**
     * Get all the partnerslocs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PartnerslocDTO> findAll(Pageable pageable);


    /**
     * Get the "id" partnersloc.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartnerslocDTO> findOne(Long id);

    /**
     * Delete the "id" partnersloc.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the partnersloc corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PartnerslocDTO> search(String query, Pageable pageable);
}
