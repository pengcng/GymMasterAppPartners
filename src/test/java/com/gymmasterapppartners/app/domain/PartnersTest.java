package com.gymmasterapppartners.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gymmasterapppartners.app.web.rest.TestUtil;

public class PartnersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Partners.class);
        Partners partners1 = new Partners();
        partners1.setId(1L);
        Partners partners2 = new Partners();
        partners2.setId(partners1.getId());
        assertThat(partners1).isEqualTo(partners2);
        partners2.setId(2L);
        assertThat(partners1).isNotEqualTo(partners2);
        partners1.setId(null);
        assertThat(partners1).isNotEqualTo(partners2);
    }
}
