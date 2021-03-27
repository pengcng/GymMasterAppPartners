package com.gymmasterapppartners.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gymmasterapppartners.app.web.rest.TestUtil;

public class PartnerslocTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Partnersloc.class);
        Partnersloc partnersloc1 = new Partnersloc();
        partnersloc1.setId(1L);
        Partnersloc partnersloc2 = new Partnersloc();
        partnersloc2.setId(partnersloc1.getId());
        assertThat(partnersloc1).isEqualTo(partnersloc2);
        partnersloc2.setId(2L);
        assertThat(partnersloc1).isNotEqualTo(partnersloc2);
        partnersloc1.setId(null);
        assertThat(partnersloc1).isNotEqualTo(partnersloc2);
    }
}
