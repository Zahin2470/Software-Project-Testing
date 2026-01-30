package com.example;

public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    public int subtract(int a, int b) {
        return a - b;
    }

    // multiply two integers
    public int multiply(int a, int b) {
        return a * b;
    }

    // integer division; throws IllegalArgumentException on divide by zero
    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero.");
        }
        return a / b;
    }
}