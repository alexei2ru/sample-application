package com.ntt.task.config;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class MemoryLeakProducer extends PercentBasedAction{

    private static final Logger LOGGER = Logger.getLogger(RandomError.class.getName());

    private static final List<String> MEMORY_LEAK = new ArrayList<>();

    private void eatMemory() {
        final String randomText = RandomStringUtils.randomAlphanumeric(1000000);
        LOGGER.info("eating memory");
        MEMORY_LEAK.add(randomText);
    }

    @Override
    protected int getPercentage() {
        return 50;
    }

    @Override
    protected void applyAction() {
        eatMemory();
    }
}
