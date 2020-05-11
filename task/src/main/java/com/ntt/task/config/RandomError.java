package com.ntt.task.config;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import java.util.logging.Logger;

@ApplicationScoped
public class RandomError extends PercentBasedAction {

    private static final int PERCENTAGE_OF_ERROR = 10;

    private static final Logger LOGGER = Logger.getLogger(RandomError.class.getName());

    @Override
    protected int getPercentage() {
        return PERCENTAGE_OF_ERROR;
    }

    @Override
    protected void applyAction() {
        LOGGER.info("Applying random request error");
        throw new WebApplicationException("A random error occurred");

    }
}
