package com.upgrad.FoodOrderingApp.service.common;

public enum ItemType {
    NON_VEG("NON_VEG"),
    VEG("VEG");

    private String value;

    ItemType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
