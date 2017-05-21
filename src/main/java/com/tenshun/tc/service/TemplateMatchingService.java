package com.tenshun.tc.service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Declares contract for interaction between REST API/Message Broker and Source(File, Database, etc).
 * todo where is contract exactly? need huge refactoring
 * @author Robert S.
 */
public interface TemplateMatchingService {

    /**
     * Initializes templates on start
     * Must be annotated with {@code javax.annotation.@PostConstruct} annotation
     * @throws Exception if Source (File, for example) is not available
     */
    @PostConstruct
    void init() throws Exception;

    /**
     * Searches matching pattern in the Source
     * @param text: text that must be compared with pattern
     * @return matched java.util.List of Pattern
     */
    Optional<List<Pattern>> compareWithTemplates(@NotNull String text);

    /**
     * Gets java.util.regex.Pattern from the Source by guid
     * @param guid of template from Map
     * @return One java.util.regex.Pattern object
     */
    Optional<Pattern> getPatternByGuid(@NotNull String guid);


    /**
     * Get all templates from the Source
     * @return Map of all templates
     */
    Optional<Map<String, Pattern>> getAllTemplates();


    /**
     * Put new template in the Source
     * @param template Concatenation of some string(s) with regex
     * @return Pattern object. todo replace with guid. Because next time client must need to know how to get his template back
     */
    Optional<Pattern> putToTemplateStorage(@NotNull String template);

    /**
     * Compare text with concrete template.
     * @param text Some text that need to compare
     * @param template Concatenation of some string(s) with regex
     * @return
     * todo Use {@code getPatternByGuid} method to get template
     */
    boolean compareWithTemplate(@NotNull String text, @NotNull String template);
}
