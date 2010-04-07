package org.jbehave.core.model;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbehave.core.i18n.StringEncoder;

/**
 * Provides the keywords which allow parsers to find steps in stories and
 * match those steps with candidates through the annotations (Given, When and
 * Then) or though other keywords (And, "!--"). It also provides keywords used
 * in reporting.
 */
public class KeyWords {

    public static final String NARRATIVE = "Narrative";
    public static final String IN_ORDER_TO = "InOrderTo";
    public static final String AS_A = "AsA";
    public static final String I_WANT_TO = "IWantTo";
    public static final String SCENARIO = "Scenario";
    public static final String GIVEN_STORIES = "GivenStories";
    public static final String EXAMPLES_TABLE = "ExamplesTable";
    public static final String EXAMPLES_TABLE_ROW = "ExamplesTableRow";
    public static final String EXAMPLES_TABLE_HEADER_SEPARATOR = "ExamplesTableHeaderSeparator";
    public static final String EXAMPLES_TABLE_VALUE_SEPARATOR = "ExamplesTableValueSeparator";
    public static final String GIVEN = "Given";
    public static final String WHEN = "When";
    public static final String THEN = "Then";
    public static final String AND = "And";
    public static final String IGNORABLE = "Ignorable";
    public static final String PENDING = "Pending";
    public static final String NOT_PERFORMED = "NotPerformed";
    public static final String FAILED = "Failed";
    public static final List<String> KEYWORDS = asList(NARRATIVE, IN_ORDER_TO, AS_A, I_WANT_TO, SCENARIO,
            GIVEN_STORIES, EXAMPLES_TABLE, EXAMPLES_TABLE_ROW, EXAMPLES_TABLE_HEADER_SEPARATOR, EXAMPLES_TABLE_VALUE_SEPARATOR,
            GIVEN, WHEN, THEN, AND, IGNORABLE, PENDING, NOT_PERFORMED, FAILED);

    private final String narrative;
    private final String inOrderTo;
    private final String asA;
    private final String iWantTo;
    private final String scenario;
    private final String givenStories;
    private final String examplesTable;
    private final String examplesTableRow;
    private final String examplesTableHeaderSeparator;
    private final String examplesTableValueSeparator;    
    private final String given;
    private final String when;
    private final String then;
    private final String and;
    private final String ignorable;
    private final String pending;
    private final String notPerformed;
    private final String failed;
    private final String[] others;
    private StringEncoder encoder;

    public static Map<String, String> defaultKeywords() {
        Map<String, String> keywords = new HashMap<String, String>();
        keywords.put(NARRATIVE, "Narrative:");
        keywords.put(IN_ORDER_TO, "In order to:");
        keywords.put(AS_A, "As a:");
        keywords.put(I_WANT_TO, "I want to:");
        keywords.put(SCENARIO, "Scenario:");
        keywords.put(GIVEN_STORIES, "GivenStories:");
        keywords.put(EXAMPLES_TABLE, "Examples:");
        keywords.put(EXAMPLES_TABLE_ROW, "Example:");
        keywords.put(EXAMPLES_TABLE_HEADER_SEPARATOR, "|");
        keywords.put(EXAMPLES_TABLE_VALUE_SEPARATOR, "|");
        keywords.put(GIVEN, "Given");
        keywords.put(WHEN, "When");
        keywords.put(THEN, "Then");
        keywords.put(AND, "And");
        keywords.put(IGNORABLE, "!--");
        keywords.put(PENDING, "PENDING");
        keywords.put(NOT_PERFORMED, "NOT PERFORMED");
        keywords.put(FAILED, "FAILED");
        return keywords;
    }

    /**
     * Creates KeyWords with default values {@link #defaultKeywords()}.
     */
    public KeyWords() {
        this(defaultKeywords());
    }

    /**
     * Creates KeyWords with provided values and default encoder
     * 
     * @param keywords the Map of keywords indexed by their name
     */
    public KeyWords(Map<String, String> keywords) {
        this(keywords, new StringEncoder());
    }

    /**
     * Creates a KeyWords from the map provided.
     * 
     * @param keywords the Map of keywords indexed by their name
     * @param encoder the StringEncoder used to encode the values
     */
    public KeyWords(Map<String, String> keywords, StringEncoder encoder) {
        this.narrative = keyword(NARRATIVE, keywords);
        this.inOrderTo = keyword(IN_ORDER_TO, keywords);
        this.asA = keyword(AS_A, keywords);
        this.iWantTo = keyword(I_WANT_TO, keywords);
        this.scenario = keyword(SCENARIO, keywords);
        this.givenStories = keyword(GIVEN_STORIES, keywords);
        this.examplesTable = keyword(EXAMPLES_TABLE, keywords);
        this.examplesTableRow = keyword(EXAMPLES_TABLE_ROW, keywords);
        this.examplesTableHeaderSeparator = keyword(EXAMPLES_TABLE_HEADER_SEPARATOR, keywords);
        this.examplesTableValueSeparator = keyword(EXAMPLES_TABLE_VALUE_SEPARATOR, keywords);
        this.given = keyword(GIVEN, keywords);
        this.when = keyword(WHEN, keywords);
        this.then = keyword(THEN, keywords);
        this.and = keyword(AND, keywords);
        this.ignorable = keyword(IGNORABLE, keywords);
        this.pending = keyword(PENDING, keywords);
        this.notPerformed = keyword(NOT_PERFORMED, keywords);
        this.failed = keyword(FAILED, keywords);
        this.others = new String[] { and, ignorable };
        this.encoder = encoder;
    }

    private String keyword(String name, Map<String, String> keywords) {
        String keyword = keywords.get(name);
        if (keyword == null) {
            throw new KeywordNotFoundException(name, keywords);
        }
        return keyword;
    }

    public String narrative() {
        return narrative;
    }

    public String inOrderTo() {
        return inOrderTo;
    }

    public String asA() {
        return asA;
    }

    public String iWantTo() {
        return iWantTo;
    }

    public String scenario() {
        return scenario;
    }

    public String givenStories() {
        return givenStories;
    }

    public String examplesTable() {
        return examplesTable;
    }

    public String examplesTableRow() {
        return examplesTableRow;
    }

    public String examplesTableHeaderSeparator() {
        return examplesTableHeaderSeparator;
    }
    
    public String examplesTableValueSeparator() {
        return examplesTableValueSeparator;
    }

    public String given() {
        return given;
    }

    public String when() {
        return when;
    }

    public String then() {
        return then;
    }

    public String and() {
        return and;
    }

    public String ignorable() {
        return ignorable;
    }

    public String pending() {
        return pending;
    }

    public String notPerformed() {
        return notPerformed;
    }

    public String failed() {
        return failed;
    }

    public String[] others() {
        return others;
    }

    public String encode(String value) {
        if (encoder != null) {
            return encoder.encode(value);
        }
        return value;
    }

    @SuppressWarnings("serial")
    public static final class KeywordNotFoundException extends RuntimeException {

        public KeywordNotFoundException(String name, Map<String, String> keywords) {
            super("Keyword " + name + " not found amongst " + keywords);
        }

    }

    @SuppressWarnings("serial")
    public static final class InsufficientKeywordsException extends RuntimeException {

        public InsufficientKeywordsException(String... others) {
            super("Insufficient keywords: " + asList(others) + ", but requires another " + (10 - others.length));
        }

    }
}
