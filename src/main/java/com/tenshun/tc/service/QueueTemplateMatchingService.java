package com.tenshun.tc.service;

import com.tenshun.tc.service.dto.CustomMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.tenshun.tc.service.utils.QueueConstants.QUEUE;

/**
 *
 * Client must connect to the same queue(s) and send text that he want to compare to specific topic/exchange(????)
 * Possible scenario:
 * 1) We have some message broker(RabbitMQ, Kafka)
 * 2) Client subscribes for example to "/results" topic
 * 3) Client publish to "/process" topic some text that he wants to be processed
 * 4) Server subscribes to the same "/process" topic and gets messages from it
 * 5) Server processes messages and push result of processing to "/results" topic
 *
 * Also we need additional properties: guid(duplicates) and timestamp(order) for every message.
 * todo https://habrahabr.ru/company/mailru/blog/216363/
 */

@Service
@Qualifier("queueBasedService")
public class QueueTemplateMatchingService implements TemplateMatchingService {

    private static final Logger logger = LoggerFactory.getLogger(QueueTemplateMatchingService.class);

    @Value("${com.tenshun.tc.templates.path}")
    public String path;

    @Value("${com.tenshun.tc.templates.regex}")
    public String regex;

    @Value("${com.tenshun.tc.templates.deprecatedRegex}")
    public String deprecatedRegex;

    @Autowired
    public DummyMessageSender dummyMessageSender;

    private Map<String, Pattern> templateStorage;

    @RabbitListener(queues = QUEUE)
    public void receiveMessage(final CustomMessage message) {
        logger.info("Received message: {}", message.toString());
        /*Optional<List<Pattern>> patterns = compareWithTemplates(message.getValue());
        patterns.ifPresent(item -> {
            CustomMessage<List<Pattern>> output = new CustomMessage<>(item);
            dummyMessageSender.sendMessage(output);
        });*/

    }

    /*@RabbitListener(queues = QUEUE)
    public void receiveResults(final CustomMessage<List<Pattern>> templates) {
        logger.info("Received message: {}", templates.toString());

    }*/

    @Override
    public void init() throws Exception {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            templateStorage = lines
                    .map(line -> line.replaceAll(deprecatedRegex, regex))
                    .map(Pattern::compile)
                    .collect(Collectors.toMap(item -> UUID.randomUUID().toString(), Function.identity()));

            logger.info("QueueTemplateMatching Service is Ready for use!");
        } catch (IOException e) {
            logger.info("Something went wrong", e);
        }
    }

    @Override
    public Optional<List<Pattern>> compareWithTemplates(String text) {
        return null;
    }

    @Override
    public Optional<Pattern> getPatternByGuid(String guid) {
        return null;
    }

    @Override
    public Optional<Map<String, Pattern>> getAllTemplates() {
        return null;
    }

    @Override
    public Optional<Pattern> putToTemplateStorage(String template) {
        return null;
    }

    @Override
    public boolean compareWithTemplateByGuid(String text, String templateGuid) {
        return false;
    }
}
