package com.epam.textparser.parser;

import com.epam.textparser.textcomponents.ComponentContainer;
import com.epam.textparser.textcomponents.TextComponentType;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ivan on 6/25/14.
 * Converts given text to TEXT type ComponentContainer
 */
class ParagraphParser {

    private static Logger log = Logger.getLogger(ParagraphParser.class);
    private SentenceParser sentenceParser;
    private Pattern pattern;

    ParagraphParser(String sentenceRegExp,
                    SentenceParser sentenceParser) {
        pattern = Pattern.compile(sentenceRegExp);
        this.sentenceParser = sentenceParser;
    }

    ComponentContainer parse(String textParagraph) {
        Matcher matcher = pattern.matcher(textParagraph);
        ComponentContainer paragraph = new ComponentContainer(TextComponentType.PARAGRAPH);
        while (matcher.find()) {
            String textSentence = matcher.group();
            log.debug("found: " + textSentence);
            ComponentContainer sentence = sentenceParser.parse(textSentence);
            paragraph.addComponent(sentence);
        }
        return paragraph;
    }
}
