package com.gymmasterapppartners.app.web.rest;

import com.gymmasterapppartners.app.service.PartnersService;
import com.gymmasterapppartners.app.web.rest.errors.BadRequestAlertException;
import com.gymmasterapppartners.app.service.dto.PartnersDTO;

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
 * REST controller for managing {@link com.gymmasterapppartners.app.domain.Partners}.
 */
@RestController
@RequestMapping("/api")
public class PartnersResource {

    private final Logger log = LoggerFactory.getLogger(PartnersResource.class);

    private static final String ENTITY_NAME = "gymmasterapppartnersPartners";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartnersService partnersService;

    public PartnersResource(PartnersService partnersService) {
        this.partnersService = partnersService;
    }

    /**
     * {@code POST  /partners} : Create a new partners.
     *
     * @param partnersDTO the partnersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partnersDTO, or with status {@code 400 (Bad Request)} if the partners has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/partners")
    public ResponseEntity<PartnersDTO> createPartners(@Valid @RequestBody PartnersDTO partnersDTO) throws URISyntaxException {
        log.debug("REST request to save Partners : {}", partnersDTO);
        if (partnersDTO.getId() != null) {
            throw new BadRequestAlertException("A new partners cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartnersDTO result = partnersService.save(partnersDTO);
        return ResponseEntity.created(new URI("/api/partners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /partners} : Updates an existing partners.
     *
     * @param partnersDTO the partnersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partnersDTO,
     * or with status {@code 400 (Bad Request)} if the partnersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partnersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/partners")
    public ResponseEntity<PartnersDTO> updatePartners(@Valid @RequestBody PartnersDTO partnersDTO) throws URISyntaxException {
        log.debug("REST request to update Partners : {}", partnersDTO);
        if (partnersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartnersDTO result = partnersService.save(partnersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partnersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /partners} : get all the partners.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partners in body.
     */
    @GetMapping("/partners")
    public ResponseEntity<List<PartnersDTO>> getAllPartners(Pageable pageable) {
        log.debug("REST request to get a page of Partners");
        Page<PartnersDTO> page = partnersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /partners/:id} : get the "id" partners.
     *
     * @param id the id of the partnersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partnersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/partners/{id}")
    public ResponseEntity<PartnersDTO> getPartners(@PathVariable Long id) {
        log.debug("REST request to get Partners : {}", id);
        Optional<PartnersDTO> partnersDTO = partnersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partnersDTO);
    }

    /**
     * {@code DELETE  /partners/:id} : delete the "id" partners.
     *
     * @param id the id of the partnersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/partners/{id}")
    public ResponseEntity<Void> deletePartners(@PathVariable Long id) {
        log.debug("REST request to delete Partners : {}", id);
        partnersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/partners?query=:query} : search for the partners corresponding
     * to the query.
     *
     * @param query the query of the partners search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/partners")
    public ResponseEntity<List<PartnersDTO>> searchPartners(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Partners for query {}", query);
        Page<PartnersDTO> page = partnersService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
