package org.jbehave.core;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.errors.InvalidRunnableStoryException;
import org.jbehave.core.steps.CandidateSteps;

/**
 * <p>
 * Abstract implementation of RunnableStory which is intended as a base
 * class for delegate implementations of RunnableStory. As such, it has no explicit
 * supports for any test framework.  It provides the
 * {@link StoryEmbedder} used to run the story or stories, with the provided
 * {@link StoryConfiguration} and the {@link CandidateSteps}.
 * </p>
 * <p>
 * Typically, users will find it easier to extend other implementations such as
 * {@link JUnitStory} or {@link JUnitStories} which also provide support for test frameworks
 * and also provide the story class or story paths being implemented by the user.
 * </p>
 */
public abstract class AbstractStory implements RunnableStory {

    protected StoryConfiguration configuration = new MostUsefulStoryConfiguration();
    protected List<CandidateSteps> candidateSteps = new ArrayList<CandidateSteps>();

    public void useConfiguration(StoryConfiguration configuration) {
        this.configuration = configuration;
    }

    public void addSteps(CandidateSteps... steps) {
        this.candidateSteps.addAll(asList(steps));
    }

    public StoryConfiguration getConfiguration() {
        return configuration;
    }

    public List<CandidateSteps> getSteps() {
        return candidateSteps;
    }

    protected StoryEmbedder storyEmbedder() {
        StoryEmbedder embedder = new StoryEmbedder() {
            @Override
            public List<CandidateSteps> candidateSteps() {
                return candidateSteps;
            }

            @Override
            public StoryConfiguration configuration() {
                return configuration;
            }
        };
        return embedder;
    }


}
