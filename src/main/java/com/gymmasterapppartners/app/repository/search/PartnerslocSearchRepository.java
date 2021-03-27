package com.gymmasterapppartners.app.repository.search;

import com.gymmasterapppartners.app.domain.Partnersloc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Partnersloc} entity.
 */
public interface PartnerslocSearchRepository extends ElasticsearchRepository<Partnersloc, Long> {
}
