package com.epam.am.entity;

import java.util.List;

public class Paragraph extends AbstractCompoundText<Sentence> implements TextPart, DeepCloneable<Paragraph> {

    public Paragraph(List<Sentence> components) {
        super(components);
    }

    public Paragraph() {
        super();
    }

    public List<Sentence> getSentences() {
        return components;
    }

    public boolean add(Sentence sentence) {
        return components.add(sentence);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Sentence sentence : components) {
            sb.append("\n" + sentence);
        }
        return "\n\nParagraph{" +
                "components=" + sb.toString() +
                '}';
    }

    public String toOriginal() {
        StringBuilder sb = new StringBuilder();
        for (Sentence sentence : components) {
            sb.append(sentence.toOriginal());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Paragraph paragraph = (Paragraph) o;

        if (!components.equals(paragraph.components)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return components.hashCode();
    }

    @Override
    public Paragraph deepClone() {
        Paragraph result = new Paragraph();
        for (Sentence sentence : components) {
            result.add(sentence.deepClone());
        }
        return result;
    }

    public Text asText() {
        Text result = new Text();
        result.add(this);
        return result;
    }
}
