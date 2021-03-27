package com.gymmasterapppartners.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PartnerslocMapperTest {

    private PartnerslocMapper partnerslocMapper;

    @BeforeEach
    public void setUp() {
        partnerslocMapper = new PartnerslocMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(partnerslocMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(partnerslocMapper.fromId(null)).isNull();
    }
}
