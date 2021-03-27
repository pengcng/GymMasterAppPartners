package com.gymmasterapppartners.app.service.impl;

import com.gymmasterapppartners.app.service.PartnersService;
import com.gymmasterapppartners.app.domain.Partners;
import com.gymmasterapppartners.app.repository.PartnersRepository;
import com.gymmasterapppartners.app.repository.search.PartnersSearchRepository;
import com.gymmasterapppartners.app.service.dto.PartnersDTO;
import com.gymmasterapppartners.app.service.mapper.PartnersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Partners}.
 */
@Service
@Transactional
public class PartnersServiceImpl implements PartnersService {

    private final Logger log = LoggerFactory.getLogger(PartnersServiceImpl.class);

    private final PartnersRepository partnersRepository;

    private final PartnersMapper partnersMapper;

    private final PartnersSearchRepository partnersSearchRepository;

    public PartnersServiceImpl(PartnersRepository partnersRepository, PartnersMapper partnersMapper, PartnersSearchRepository partnersSearchRepository) {
        this.partnersRepository = partnersRepository;
        this.partnersMapper = partnersMapper;
        this.partnersSearchRepository = partnersSearchRepository;
    }

    @Override
    public PartnersDTO save(PartnersDTO partnersDTO) {
        log.debug("Request to save Partners : {}", partnersDTO);
        Partners partners = partnersMapper.toEntity(partnersDTO);
        partners = partnersRepository.save(partners);
        PartnersDTO result = partnersMapper.toDto(partners);
        partnersSearchRepository.save(partners);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PartnersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Partners");
        return partnersRepository.findAll(pageable)
            .map(partnersMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PartnersDTO> findOne(Long id) {
        log.debug("Request to get Partners : {}", id);
        return partnersRepository.findById(id)
            .map(partnersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Partners : {}", id);
        partnersRepository.deleteById(id);
        partnersSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PartnersDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Partners for query {}", query);
        return partnersSearchRepository.search(queryStringQuery(query), pageable)
            .map(partnersMapper::toDto);
    }
}
