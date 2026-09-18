package com.vityarthi.dbms.util;
import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.*;
class InputValidatorTest { @Test void validSemester(){assertDoesNotThrow(()->InputValidator.semester(4));} @Test void invalidSemester(){assertThrows(IllegalArgumentException.class,()->InputValidator.semester(9));} @Test void requiredField(){assertThrows(IllegalArgumentException.class,()->InputValidator.required("","Name"));} }
