package com.vityarthi.dbms.util;
public final class InputValidator { private InputValidator(){} public static void required(String v,String f){if(v==null||v.isBlank())throw new IllegalArgumentException(f+" cannot be empty");} public static void semester(int n){if(n<1||n>8)throw new IllegalArgumentException("Semester must be between 1 and 8");} }
