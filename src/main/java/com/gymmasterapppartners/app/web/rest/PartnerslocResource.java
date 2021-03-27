package com.gymmasterapppartners.app.web.rest;

import com.gymmasterapppartners.app.service.PartnerslocService;
import com.gymmasterapppartners.app.web.rest.errors.BadRequestAlertException;
import com.gymmasterapppartners.app.service.dto.PartnerslocDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.gymmasterapppartners.app.domain.Partnersloc}.
 */
@RestController
@RequestMapping("/api")
public class PartnerslocResource {

    private final Logger log = LoggerFactory.getLogger(PartnerslocResource.class);

    private static final String ENTITY_NAME = "gymmasterapppartnersPartnersloc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartnerslocService partnerslocService;

    public PartnerslocResource(PartnerslocService partnerslocService) {
        this.partnerslocService = partnerslocService;
    }

    /**
     * {@code POST  /partnerslocs} : Create a new partnersloc.
     *
     * @param partnerslocDTO the partnerslocDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partnerslocDTO, or with status {@code 400 (Bad Request)} if the partnersloc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/partnerslocs")
    public ResponseEntity<PartnerslocDTO> createPartnersloc(@Valid @RequestBody PartnerslocDTO partnerslocDTO) throws URISyntaxException {
        log.debug("REST request to save Partnersloc : {}", partnerslocDTO);
        if (partnerslocDTO.getId() != null) {
            throw new BadRequestAlertException("A new partnersloc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartnerslocDTO result = partnerslocService.save(partnerslocDTO);
        return ResponseEntity.created(new URI("/api/partnerslocs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /partnerslocs} : Updates an existing partnersloc.
     *
     * @param partnerslocDTO the partnerslocDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partnerslocDTO,
     * or with status {@code 400 (Bad Request)} if the partnerslocDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partnerslocDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/partnerslocs")
    public ResponseEntity<PartnerslocDTO> updatePartnersloc(@Valid @RequestBody PartnerslocDTO partnerslocDTO) throws URISyntaxException {
        log.debug("REST request to update Partnersloc : {}", partnerslocDTO);
        if (partnerslocDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartnerslocDTO result = partnerslocService.save(partnerslocDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partnerslocDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /partnerslocs} : get all the partnerslocs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partnerslocs in body.
     */
    @GetMapping("/partnerslocs")
    public ResponseEntity<List<PartnerslocDTO>> getAllPartnerslocs(Pageable pageable) {
        log.debug("REST request to get a page of Partnerslocs");
        Page<PartnerslocDTO> page = partnerslocService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /partnerslocs/:id} : get the "id" partnersloc.
     *
     * @param id the id of the partnerslocDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partnerslocDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/partnerslocs/{id}")
    public ResponseEntity<PartnerslocDTO> getPartnersloc(@PathVariable Long id) {
        log.debug("REST request to get Partnersloc : {}", id);
        Optional<PartnerslocDTO> partnerslocDTO = partnerslocService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partnerslocDTO);
    }

    /**
     * {@code DELETE  /partnerslocs/:id} : delete the "id" partnersloc.
     *
     * @param id the id of the partnerslocDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/partnerslocs/{id}")
    public ResponseEntity<Void> deletePartnersloc(@PathVariable Long id) {
        log.debug("REST request to delete Partnersloc : {}", id);
        partnerslocService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/partnerslocs?query=:query} : search for the partnersloc corresponding
     * to the query.
     *
     * @param query the query of the partnersloc search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/partnerslocs")
    public ResponseEntity<List<PartnerslocDTO>> searchPartnerslocs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Partnerslocs for query {}", query);
        Page<PartnerslocDTO> page = partnerslocService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
