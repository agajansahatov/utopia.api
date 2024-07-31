package io.github.agajansahatov.utopia.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;

@Configuration
@Profile("test")
public class TestDatabaseCleanup {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @AfterTestClass
    @SuppressWarnings("unchecked")
    public void cleanupDatabase() {
        Query query = entityManager.createNativeQuery(
                "SELECT table_name FROM information_schema.tables WHERE table_type = 'BASE TABLE' AND table_schema = 'utopia_test'"
        );
        List<String> tables = query.getResultList();

        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        for (String table : tables) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + table).executeUpdate();
        }
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }
}
