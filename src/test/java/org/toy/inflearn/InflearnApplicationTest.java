package org.toy.inflearn;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class InflearnApplicationTest {
    @Test
    void run() {
        try(MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            InflearnApplication.main(new String[0]);

            mocked.verify(() -> SpringApplication.run(InflearnApplication.class, new String[0]));
        }
    }
}