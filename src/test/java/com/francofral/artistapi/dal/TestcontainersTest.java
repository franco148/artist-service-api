package com.francofral.artistapi.dal;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class TestcontainersTest extends AbstractTestContainers {

    @Test
    void canStartPostgresDB() {
        then(postgreSQLContainer.isRunning()).isTrue();
        then(postgreSQLContainer.isCreated()).isTrue();
    }
}
