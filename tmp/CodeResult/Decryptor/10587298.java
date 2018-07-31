package lv.odylab.evemanage.shared.eve;

/**
 * 0.6 | +9 | -2 | +1 | Circular Logic | Interface Alignment Chart | Symbiotic Figures | Circuitry Schematics
 * 1.0 | +2 | +1 | +4 | Sacred Manifesto | User Manual | Engagement Plan | Operation Handbook
 * 1.1 | 0 | +3 | +3 | Formation Layout | Tuning Instructions | Collision Measurements | Calibration Data
 * 1.2 | +1 | +2 | +5 | Classic Doctrine | Prototype Diagram | Test Reports | Advanced Theories
 * 1.8 | +4 | -1 | +2 | War Strategon | Installation Guide | Stolen Formulas | Assembly Instructions
 */
public enum Decryptor {
    TUNING_INSTRUCTIONS(21573L, 731L, "Tuning Instructions", "1.1", 0, 3, 3),
    PROTOTYPE_DIAGRAM(21574L, 731L, "Prototype Diagram", "1.2", 1, 2, 5),
    USER_MANUAL(21575L, 731L, "User Manual", "1.0", 2, 1, 4),
    INTERFACE_ALIGNMENT_CHART(21576L, 731L, "Interface Alignment Chart", "0.6", 9, -2, 1),
    INSTALLATION_GUIDE(21577L, 731L, "Installation Guide", "1.8", 4, -1, 2),
    CALIBRATION_DATA(21579L, 729L, "Calibration Data", "1.1", 0, 3, 3),
    ADVANCED_THEORIES(21580L, 729L, "Advanced Theories", "1.2", 1, 2, 5),
    OPERATION_HANDBOOK(21581L, 729L, "Operation Handbook", "1.0", 2, 1, 4),
    CIRCUITRY_SCHEMATICS(21582L, 729L, "Circuitry Schematics", "0.6", 9, -2, 1),
    ASSEMBLY_INSTRUCTIONS(21583L, 729L, "Assembly Instructions", "1.8", 4, -1, 2),
    FORMATION_LAYOUT(23178L, 728L, "Formation Layout", "1.1", 0, 3, 3),
    CLASSIC_DOCTRINE(23179L, 728L, "Classic Doctrine", "1.2", 1, 2, 5),
    SACRED_MANIFESTO(23180L, 728L, "Sacred Manifesto", "1.0", 2, 1, 4),
    CIRCULAR_LOGIC(23181L, 728L, "Circular Logic", "0.6", 9, -2, 1),
    WAR_STRATEGON(23182L, 728L, "War Strategon", "1.8", 4, -1, 2),
    COLLISION_MEASUREMENTS(23183L, 730L, "Collision Measurements", "1.1", 0, 3, 3),
    TEST_REPORTS(23184L, 730L, "Test Reports", "1.2", 1, 2, 5),
    ENGAGEMENT_PLAN(23185L, 730L, "Engagement Plan", "1.0", 2, 1, 4),
    SYMBIOTIC_FIGURES(23186L, 730L, "Symbiotic Figures", "0.6", 9, -2, 1),
    STOLEN_FORMULAS(23187L, 730L, "Stolen Formulas", "1.8", 4, -1, 2);

    private final Long typeID;
    private final Long categoryID;
    private final String name;
    private final String probabilityMultiplier;
    private final Integer maxRunModifier;
    private final Integer meModifier;
    private final Integer peModifier;

    private Decryptor(Long typeID, Long categoryID, String name, String probabilityMultiplier, Integer maxRunModifier, Integer meModifier, Integer peModifier) {
        this.typeID = typeID;
        this.categoryID = categoryID;
        this.name = name;
        this.probabilityMultiplier = probabilityMultiplier;
        this.maxRunModifier = maxRunModifier;
        this.meModifier = meModifier;
        this.peModifier = peModifier;
    }

    public Long getTypeID() {
        return typeID;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public String getName() {
        return name;
    }

    public String getProbabilityMultiplier() {
        return probabilityMultiplier;
    }

    public Integer getMaxRunModifier() {
        return maxRunModifier;
    }

    public Integer getMeModifier() {
        return meModifier;
    }

    public Integer getPeModifier() {
        return peModifier;
    }
}

/*
SELECT
  CONCAT(
    REPLACE(UPPER(it.typeName), ' ', '_'),
    '(',
    it.typeID, 'L, ',
    it.groupID, 'L, ',
    '"', it.typeName, '"', '),'
  )
FROM
  invTypes it,
  invGroups ig
WHERE
  it.groupID = ig.groupID and
  ig.categoryID = 35
ORDER BY it.typeID
 */
