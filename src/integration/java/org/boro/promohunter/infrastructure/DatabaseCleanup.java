package org.boro.promohunter.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DatabaseCleanup {

    private final EntityManager entityManager;
    private final List<String> tableNames;

    @Transactional
    public void truncateTables() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        if (tableNames.isEmpty()) {
            log.info("No database tables to truncate found");
        } else {
            log.info("Truncating database tables: {}", tableNames);
        }
        for (final String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}
