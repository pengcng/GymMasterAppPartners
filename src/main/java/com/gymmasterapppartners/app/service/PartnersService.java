package com.gymmasterapppartners.app.service;

import com.gymmasterapppartners.app.service.dto.PartnersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.gymmasterapppartners.app.domain.Partners}.
 */
public interface PartnersService {

    /**
     * Save a partners.
     *
     * @param partnersDTO the entity to save.
     * @return the persisted entity.
     */
    PartnersDTO save(PartnersDTO partnersDTO);

    /**
     * Get all the partners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PartnersDTO> findAll(Pageable pageable);


    /**
     * Get the "id" partners.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartnersDTO> findOne(Long id);

    /**
     * Delete the "id" partners.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the partners corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PartnersDTO> search(String query, Pageable pageable);
}
