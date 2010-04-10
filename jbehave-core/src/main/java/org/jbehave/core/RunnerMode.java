package org.jbehave.core;

public class RunnerMode {
    private boolean batch;
    private boolean skip;
    private boolean ignoreFailure;

    public RunnerMode(boolean batch, boolean skip, boolean ignoreFailure) {
        this.batch = batch;
        this.skip = skip;
        this.ignoreFailure = ignoreFailure;
    }

    public boolean ignoreFailure() {
        return ignoreFailure;
    }

    public boolean batch() {
        return batch;
    }

    public boolean skip() {
        return skip;
    }
}
