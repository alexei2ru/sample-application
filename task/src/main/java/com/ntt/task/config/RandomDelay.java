package com.ntt.task.config;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

@ApplicationScoped
public class RandomDelay extends PercentBasedAction {

    private static final Logger LOGGER = Logger.getLogger(RandomDelay.class.getName());

    public static final int MAX_DELAY_IN_MS = 2000;

    @Override
    protected int getPercentage() {
        return 30;
    }

    @Override
    protected void applyAction() {
        try {
            final long millis = generateRandomDelay();
            LOGGER.info("Delaying request for: " + millis + "ms");
            sleep(millis);
        } catch (final InterruptedException exception) {
            LOGGER.log(Level.SEVERE, "Thread interrupted", exception);
        }
    }

    private long generateRandomDelay() {
        return new Random().nextInt(MAX_DELAY_IN_MS);
    }
}
