package com.terrypacker.cardcollection.entity.card;

public enum Sport {

    BASEBALL("Baseball"),
    BASKETBALL("Basketball"),
    FOOTBALL("Football"),
    HOCKEY("Hockey"), ;

    private final String label;
    private Sport(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
