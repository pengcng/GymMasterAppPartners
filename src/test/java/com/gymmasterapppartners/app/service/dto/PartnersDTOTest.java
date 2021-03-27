package com.gymmasterapppartners.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gymmasterapppartners.app.web.rest.TestUtil;

public class PartnersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnersDTO.class);
        PartnersDTO partnersDTO1 = new PartnersDTO();
        partnersDTO1.setId(1L);
        PartnersDTO partnersDTO2 = new PartnersDTO();
        assertThat(partnersDTO1).isNotEqualTo(partnersDTO2);
        partnersDTO2.setId(partnersDTO1.getId());
        assertThat(partnersDTO1).isEqualTo(partnersDTO2);
        partnersDTO2.setId(2L);
        assertThat(partnersDTO1).isNotEqualTo(partnersDTO2);
        partnersDTO1.setId(null);
        assertThat(partnersDTO1).isNotEqualTo(partnersDTO2);
    }
}
