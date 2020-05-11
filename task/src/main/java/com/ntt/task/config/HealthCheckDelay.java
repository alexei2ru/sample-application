package com.ntt.task.config;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HealthCheckDelay extends PercentBasedAction {

    @Override
    protected int getPercentage() {
        return 100;
    }

    @Override
    protected void applyAction() {
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
