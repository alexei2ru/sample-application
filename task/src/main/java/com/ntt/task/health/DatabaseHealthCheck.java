package com.ntt.task.health;

import com.ntt.task.config.HealthCheckDelay;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;

@Health
@ApplicationScoped
public class DatabaseHealthCheck implements HealthCheck {

    private static final Logger LOGGER = Logger.getLogger(DatabaseHealthCheck.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private HealthCheckDelay healthCheckDelay;

    @Timed
    @Override
    public HealthCheckResponse call() {
        final HealthCheckResponse build = HealthCheckResponse.builder()
                .name(com.ntt.task.DataSource.class.getSimpleName()).state(isDatabaseUp()).build();
        healthCheckDelay.apply();
        return build;
    }

    private boolean isDatabaseUp() {
        try {
            final Query nativeQuery = entityManager.createNativeQuery("select 1");
            return Integer.valueOf(1).equals(nativeQuery.getSingleResult());
        } catch (final Exception exception) {
            LOGGER.log(Level.SEVERE, "Unable to connect to database", exception);
        }
        return false;
    }

}
