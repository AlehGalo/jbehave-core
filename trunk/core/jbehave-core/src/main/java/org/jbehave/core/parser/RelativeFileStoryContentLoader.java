package org.jbehave.core.parser;

import org.apache.commons.io.IOUtils;
import org.jbehave.core.errors.InvalidStoryResourceException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Loads the content from a place that is relative and
 * predictable to the compiled scenario class.
 * <p/>
 * See MAVEN_TEST_DIR, which implies a traversal out of 'target/test-classes'
 */
public class RelativeFileStoryContentLoader implements StoryContentLoader {

    private final String traversal;
    private final URL location;
    private static final String MAVEN_TEST_DIR = "../../src/test/java";

    public RelativeFileStoryContentLoader(Class storyClass, String traversal) {
        this.traversal = traversal;
        this.location = locationFor(storyClass);
    }
    
    public RelativeFileStoryContentLoader(Class storyClass) {
        this(storyClass, MAVEN_TEST_DIR);
    }

    protected URL locationFor(Class storyClass) {
        return storyClass.getProtectionDomain().getCodeSource().getLocation();
    }

    public String loadStoryContent(String storyPath) {
        try {
            String fileLocation = new File(location.getFile()).getCanonicalPath() + "/";
            fileLocation = fileLocation + traversal + "/" + storyPath;
            fileLocation = fileLocation.replace("/", File.separator); // Windows and Unix
            File file = new File(fileLocation);
            return IOUtils.toString(new FileInputStream(file));            
        } catch (IOException e) {
            throw new InvalidStoryResourceException("Story path '" + storyPath + "' not found.", e);
        }

    }

}