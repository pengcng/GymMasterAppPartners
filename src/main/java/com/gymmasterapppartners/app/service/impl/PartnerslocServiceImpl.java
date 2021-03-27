package com.gymmasterapppartners.app.service.impl;

import com.gymmasterapppartners.app.service.PartnerslocService;
import com.gymmasterapppartners.app.domain.Partnersloc;
import com.gymmasterapppartners.app.repository.PartnerslocRepository;
import com.gymmasterapppartners.app.repository.search.PartnerslocSearchRepository;
import com.gymmasterapppartners.app.service.dto.PartnerslocDTO;
import com.gymmasterapppartners.app.service.mapper.PartnerslocMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Partnersloc}.
 */
@Service
@Transactional
public class PartnerslocServiceImpl implements PartnerslocService {

    private final Logger log = LoggerFactory.getLogger(PartnerslocServiceImpl.class);

    private final PartnerslocRepository partnerslocRepository;

    private final PartnerslocMapper partnerslocMapper;

    private final PartnerslocSearchRepository partnerslocSearchRepository;

    public PartnerslocServiceImpl(PartnerslocRepository partnerslocRepository, PartnerslocMapper partnerslocMapper, PartnerslocSearchRepository partnerslocSearchRepository) {
        this.partnerslocRepository = partnerslocRepository;
        this.partnerslocMapper = partnerslocMapper;
        this.partnerslocSearchRepository = partnerslocSearchRepository;
    }

    @Override
    public PartnerslocDTO save(PartnerslocDTO partnerslocDTO) {
        log.debug("Request to save Partnersloc : {}", partnerslocDTO);
        Partnersloc partnersloc = partnerslocMapper.toEntity(partnerslocDTO);
        partnersloc = partnerslocRepository.save(partnersloc);
        PartnerslocDTO result = partnerslocMapper.toDto(partnersloc);
        partnerslocSearchRepository.save(partnersloc);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PartnerslocDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Partnerslocs");
        return partnerslocRepository.findAll(pageable)
            .map(partnerslocMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PartnerslocDTO> findOne(Long id) {
        log.debug("Request to get Partnersloc : {}", id);
        return partnerslocRepository.findById(id)
            .map(partnerslocMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Partnersloc : {}", id);
        partnerslocRepository.deleteById(id);
        partnerslocSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PartnerslocDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Partnerslocs for query {}", query);
        return partnerslocSearchRepository.search(queryStringQuery(query), pageable)
            .map(partnerslocMapper::toDto);
    }
}
