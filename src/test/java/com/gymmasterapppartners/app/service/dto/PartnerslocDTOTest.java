package com.gymmasterapppartners.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gymmasterapppartners.app.web.rest.TestUtil;

public class PartnerslocDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerslocDTO.class);
        PartnerslocDTO partnerslocDTO1 = new PartnerslocDTO();
        partnerslocDTO1.setId(1L);
        PartnerslocDTO partnerslocDTO2 = new PartnerslocDTO();
        assertThat(partnerslocDTO1).isNotEqualTo(partnerslocDTO2);
        partnerslocDTO2.setId(partnerslocDTO1.getId());
        assertThat(partnerslocDTO1).isEqualTo(partnerslocDTO2);
        partnerslocDTO2.setId(2L);
        assertThat(partnerslocDTO1).isNotEqualTo(partnerslocDTO2);
        partnerslocDTO1.setId(null);
        assertThat(partnerslocDTO1).isNotEqualTo(partnerslocDTO2);
    }
}
