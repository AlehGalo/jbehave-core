package com.lunivore.gameoflife;

import org.jbehave.core.PropertyBasedStoryConfiguration;
import org.jbehave.core.JUnitStory;
import org.jbehave.core.parser.*;
import org.jbehave.core.reporters.PrintStreamStoryReporter;
import org.jbehave.core.reporters.StoryReporter;

import com.lunivore.gameoflife.steps.GridSteps;

public class ICanToggleACell extends JUnitStory {

    public ICanToggleACell() {
        useConfiguration(new PropertyBasedStoryConfiguration() {
            @Override
            public StoryParser storyParser() {
                return new RegexStoryParser(keywords());
            }
            @Override
            public StoryReporter storyReporter() {
                return new PrintStreamStoryReporter();
            }
        });
        addSteps(new GridSteps());
    }
}
