package com.gymmasterapppartners.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PartnerslocSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PartnerslocSearchRepositoryMockConfiguration {

    @MockBean
    private PartnerslocSearchRepository mockPartnerslocSearchRepository;

}
