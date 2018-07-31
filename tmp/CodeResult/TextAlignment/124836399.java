package ch.atreju.btg.svg;

enum TextAlignment {
    LEFT("start"), RIGHT("end"), CENTERED("middle");
    private final String textAnchorValue;

    private TextAlignment(String textAnchorValue) {
        this.textAnchorValue = textAnchorValue;
    }

    public String getTextAnchorValue() {
        return textAnchorValue;
    }
}
