package com.tenshun.tc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author Robert S.
 */

@Service
@Qualifier("fileBasedService")
public class FileTemplateMatchingService implements TemplateMatchingService {

    private final Logger logger = LoggerFactory.getLogger(FileTemplateMatchingService.class);

    @Value("${com.tenshun.tc.templates.path}")
    public String path;

    @Value("${com.tenshun.tc.templates.regex}")
    public String regex;

    @Value("${com.tenshun.tc.templates.deprecatedRegex}")
    public String deprecatedRegex;

    private Map<String, Pattern> templateStorage;


    /**
     * replaces all {@code deprecatedRegex} with {@code regex} in every line from Stream,
     * then map each element to java.util.regex.Pattern
     * and collects all items in the single Map.
     * Probably not thread-safe! todo Refactor: change to ConcurrentHashMap!
     */

    @PostConstruct
    public void init() throws Exception {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            templateStorage = lines
                    .map(line -> line.replaceAll(deprecatedRegex, regex))
                    .map(Pattern::compile)
                    .collect(Collectors.toMap(item -> UUID.randomUUID().toString(), Function.identity()));

            logger.info("FileTemplateMatching Service is Ready for use!");
        } catch (IOException e) {
            logger.info("Something went wrong", e);
        }
    }

    @Override
    public Optional<List<Pattern>> compareWithTemplates(@NotNull String text) {
        List<Pattern> result = templateStorage.values()
                .stream()
                .filter(template -> template.matcher(text).find())
                .collect(Collectors.toList());
        return Optional.of(result);
    }

    @Override
    public Optional<Pattern> getPatternByGuid(@NotNull String guid) {
        return Optional.of(templateStorage.get(guid));
    }

    @Override
    public Optional<Map<String, Pattern>> getAllTemplates() {
        return Optional.of(templateStorage);
    }

    @Override
    public Optional<Pattern> putToTemplateStorage(@NotNull String template) {
        Pattern pattern = validateTemplate(template);
        String guid = UUID.randomUUID().toString();
        logger.info("Valid Pattern: " + pattern);
        return Optional.of(templateStorage.putIfAbsent(guid, pattern));
    }

    private Pattern validateTemplate(@NotNull String template) {
        try {
            return Pattern.compile(template);
        } catch (Exception e) {
            logger.info("Template is not valid!", e);
            throw new IllegalArgumentException("Template is not valid!", e);
        }
    }


    @Override
    public boolean compareWithTemplateByGuid(@NotNull String text, @NotNull String templateGuid) {
        Pattern validated = validateTemplate(templateGuid);
        Matcher matcher = validated.matcher(text);
        return matcher.find();
    }


}
