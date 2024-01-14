package com.udacity.security.service.service;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StyleServiceTest {

    @Test
    void testHeadingFont() {
        // Assert
        assertEquals(new Font("Sans Serif", Font.BOLD, 24), StyleService.HEADING_FONT);
    }
}