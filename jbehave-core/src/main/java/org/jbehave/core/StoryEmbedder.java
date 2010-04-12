package org.jbehave.core;

import org.jbehave.core.parser.StoryPathResolver;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateStep;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.MostUsefulStepsConfiguration;
import org.jbehave.core.steps.StepsFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.jbehave.core.reporters.StoryReporterBuilder.Format.*;

public class StoryEmbedder {
    private StoryRunner runner;
    private StoryRunnerMode runnerMode;
    private StoryRunnerMonitor runnerMonitor;

    public StoryEmbedder() {
        this(new StoryRunner(), new StoryRunnerMode(), new PrintStreamRunnerMonitor());
    }

    public StoryEmbedder(StoryRunner runner, StoryRunnerMode runnerMode, StoryRunnerMonitor runnerMonitor) {
        this.runner = runner;
        this.runnerMonitor = runnerMonitor;
        this.runnerMode = runnerMode;
    }

    public void runStories(List<RunnableStory> runnableStories) {
        if (runnerMode.skip()) {
            runnerMonitor.storiesNotRun();
            return;
        }

        Map<String, Throwable> failedStories = new HashMap<String, Throwable>();
        for (RunnableStory story : runnableStories) {
            String storyName = story.getClass().getName();
            try {
                runnerMonitor.runningStory(storyName);
                story.run();
            } catch (Throwable e) {
                if (runnerMode.batch()) {
                    // collect and postpone decision to throw exception
                    failedStories.put(storyName, e);
                } else {
                    if (runnerMode.ignoreFailure()) {
                        runnerMonitor.storyFailed(storyName, e);
                    } else {
                        throw new RunningStoriesFailedException("Failed to run story " + storyName, e);
                    }
                }
            }
        }

        if (runnerMode.batch() && failedStories.size() > 0) {
            if (runnerMode.ignoreFailure()) {
                runnerMonitor.storiesBatchFailed(format(failedStories));
            } else {
                throw new RunningStoriesFailedException("Failed to run stories in batch: " + format(failedStories));
            }
        }

    }

    public void runStoriesAsClasses(List<? extends Class<? extends RunnableStory>> storyClasses) {
        List<String> storyPaths = new ArrayList<String>();
        StoryPathResolver resolver = configuration().storyPathResolver();
        for (Class<? extends RunnableStory> storyClass : storyClasses) {
            storyPaths.add(resolver.resolve(storyClass));
        }
        runStoriesAsPaths(storyPaths);
    }

    public void runStoriesAsPaths(List<String> storyPaths) {
        if (runnerMode.skip()) {
            runnerMonitor.storiesNotRun();
            return;
        }

        Map<String, Throwable> failedStories = new HashMap<String, Throwable>();
        for (String storyPath : storyPaths) {
            try {
                runnerMonitor.runningStory(storyPath);
                StoryReporter storyReporter = storyReporter(storyPath, storyReporterFormats());
                StoryConfiguration configuration = configuration();
                configuration.addStoryReporter(storyPath, storyReporter);
                runner.run(configuration, candidateSteps(), storyPath);
            } catch (Throwable e) {
                if (runnerMode.batch()) {
                    // collect and postpone decision to throw exception
                    failedStories.put(storyPath, e);
                } else {
                    if (runnerMode.ignoreFailure()) {
                        runnerMonitor.storyFailed(storyPath, e);
                    } else {
                        throw new RunningStoriesFailedException("Failed to run story " + storyPath, e);
                    }
                }
            }
        }

        if (runnerMode.batch() && failedStories.size() > 0) {
            if (runnerMode.ignoreFailure()) {
                runnerMonitor.storiesBatchFailed(format(failedStories));
            } else {
                throw new RunningStoriesFailedException("Failed to run stories in batch: " + format(failedStories));
            }
        }

    }

    public void generateStepdoc() {
        StoryConfiguration configuration = configuration();
        List<CandidateSteps> candidateSteps = candidateSteps();
        configuration.stepdocReporter().report(configuration.stepdocGenerator().generate(candidateSteps.toArray(new CandidateSteps[candidateSteps.size()])));
    }
    
    public StoryConfiguration configuration() {
        return new MostUsefulStoryConfiguration();
    }

    public List<CandidateSteps> candidateSteps() {
        return asList(new StepsFactory(new MostUsefulStepsConfiguration()).createCandidateSteps(new CandidateStep[]{}));
    }

    protected StoryReporter storyReporter(String storyPath, StoryReporterBuilder.Format... formats) {
        StoryReporterBuilder builder = new StoryReporterBuilder(new FilePrintStreamFactory(storyPath));
        for (StoryReporterBuilder.Format format : formats) {
            builder = builder.with(format);
        }
        return builder.build();
    }

    protected StoryReporterBuilder.Format[] storyReporterFormats() {
        return new StoryReporterBuilder.Format[]{CONSOLE};
    }

    public void useStoryRunner(StoryRunner runner) {
        this.runner = runner;
    }

    public void useRunnerMode(StoryRunnerMode runnerMode) {
        this.runnerMode = runnerMode;
    }

    public void useRunnerMonitor(StoryRunnerMonitor runnerMonitor) {
        this.runnerMonitor = runnerMonitor;
    }

    private String format(Map<String, Throwable> failedStories) {
        StringBuffer sb = new StringBuffer();
        for (String storyName : failedStories.keySet()) {
            Throwable cause = failedStories.get(storyName);
            sb.append("\n");
            sb.append(storyName);
            sb.append(": ");
            sb.append(cause.getMessage());
        }
        return sb.toString();
    }

    private class RunningStoriesFailedException extends RuntimeException {
        public RunningStoriesFailedException(String message, Throwable cause) {
            super(message, cause);
        }

        public RunningStoriesFailedException(String message) {
            super(message);
        }
    }


}
