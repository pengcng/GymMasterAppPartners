package com.gymmasterapppartners.app.repository.search;

import com.gymmasterapppartners.app.domain.Partners;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Partners} entity.
 */
public interface PartnersSearchRepository extends ElasticsearchRepository<Partners, Long> {
}
