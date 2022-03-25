package com.pet.util.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeConvertTest {

    @Test
    void convertStringToLocalDate() {
        String date="2021-12-31";
        System.out.println(TimeConvert.convertStringToLocalDate(date));
    }
}