package org.jbehave.examples.trader.i18n;

import org.jbehave.core.JUnitStory;
import org.jbehave.core.MostUsefulStoryConfiguration;
import org.jbehave.core.StoryConfiguration;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.i18n.StringEncoder;
import org.jbehave.core.model.Keywords;
import org.jbehave.core.parser.LoadFromClasspath;
import org.jbehave.core.parser.PatternStoryParser;
import org.jbehave.core.parser.UnderscoredCamelCaseResolver;
import org.jbehave.core.reporters.PrintStreamStoryReporter;
import org.jbehave.core.steps.MostUsefulStepsConfiguration;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.StepsConfiguration;
import org.jbehave.core.steps.StepsFactory;

import java.util.Locale;

public class ItTraderStory extends JUnitStory {

    public ItTraderStory() {
        StoryConfiguration storyConfiguration = new MostUsefulStoryConfiguration();
        storyConfiguration.useStoryPathResolver(new UnderscoredCamelCaseResolver(".story"));
        ClassLoader classLoader = this.getClass().getClassLoader();
        Keywords keywords = new LocalizedKeywords(new Locale("it"), new StringEncoder(), "org/jbehave/examples/trader/i18n/keywords", classLoader);
        // use Italian for keywords
        storyConfiguration.useKeywords(keywords);
        storyConfiguration.useStoryParser(new PatternStoryParser(storyConfiguration.keywords()));
        storyConfiguration.useStoryLoader(new LoadFromClasspath(this.getClass().getClassLoader()));
        storyConfiguration.useStoryReporter(new PrintStreamStoryReporter(storyConfiguration.keywords()));
        useConfiguration(storyConfiguration);

        StepsConfiguration stepsConfiguration = new MostUsefulStepsConfiguration();
        // use Italian for keywords
        stepsConfiguration.useKeywords(keywords);
        stepsConfiguration.useParameterConverters(new ParameterConverters(new ParameterConverters.ExamplesTableConverter(keywords.examplesTableHeaderSeparator(), keywords.examplesTableValueSeparator())));
        addSteps(new StepsFactory(stepsConfiguration).createCandidateSteps(new ItTraderSteps()));
    }

}
