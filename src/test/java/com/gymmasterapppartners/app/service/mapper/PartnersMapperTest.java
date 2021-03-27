package com.gymmasterapppartners.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PartnersMapperTest {

    private PartnersMapper partnersMapper;

    @BeforeEach
    public void setUp() {
        partnersMapper = new PartnersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(partnersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(partnersMapper.fromId(null)).isNull();
    }
}
