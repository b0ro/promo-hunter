package org.boro.promohunter.source.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(properties = "spring.profiles.active=integration",
        webEnvironment = RANDOM_PORT)
class SourceApiTest {

    @Test
    void shouldAddSource() {
        // given

        // when

        // then
        assertThat(1).isSameAs(1);
    }
}
