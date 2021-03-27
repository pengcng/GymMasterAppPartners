package com.gymmasterapppartners.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PartnersSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PartnersSearchRepositoryMockConfiguration {

    @MockBean
    private PartnersSearchRepository mockPartnersSearchRepository;

}
