package com.diginamic.species.enums;

import java.util.Arrays;
import java.util.Objects;

import jakarta.persistence.AttributeConverter;
import lombok.Getter;

@Getter
public enum Sex {

    FEMALE("F"),
    MALE("M");

    private String abbreviation;

    private Sex(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public static class Converter implements AttributeConverter<Sex, String> {

        @Override
        public String convertToDatabaseColumn(Sex attribute) {
            if (attribute == null) {
                return null;
            }
            return attribute.getAbbreviation();
        }

        @Override
        public Sex convertToEntityAttribute(String dbData) {
            return Arrays.asList(Sex.values()).stream()
                .filter(o -> Objects.equals(o.getAbbreviation(), dbData))
                .findAny().orElse(null);
        }

    }

}
