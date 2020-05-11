package com.ntt.task.config;

import java.util.Random;

public abstract class PercentBasedAction {

    protected abstract int getPercentage();
    protected abstract void applyAction();

    public void apply() {
        if(randomNumberFrom1To100() <= getPercentage()){
            applyAction();
        }
    }

    private int randomNumberFrom1To100() {
        return new Random().nextInt(100) + 1;
    }
}
