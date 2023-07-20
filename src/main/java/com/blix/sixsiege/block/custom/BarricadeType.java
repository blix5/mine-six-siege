package com.blix.sixsiege.block.custom;

import net.minecraft.util.StringIdentifiable;

public enum BarricadeType implements StringIdentifiable {

    SINGLE,
    MIDDLE,
    LEFT,
    RIGHT;

    @Override
    public String asString() {
        switch (this) {
            default -> {
                return "single";
            }
            case MIDDLE -> {
                return "middle";
            }
            case LEFT -> {
                return "left";
            }
            case RIGHT -> {
                return "right";
            }
        }
    }

}
