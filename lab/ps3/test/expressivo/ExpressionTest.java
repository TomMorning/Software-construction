/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    // TODO tests for Expression

    // Testing strategy for Number
    @Test
    public void testNumberToString() {
        Expression number = new Number(5.0);
        assertEquals("5.0", number.toString());
    }

    @Test
    public void testNumberEqualsAndHashCode() {
        Expression number1 = new Number(5.0);
        Expression number2 = new Number(5.0);
        assertTrue(number1.equals(number2));
        assertEquals(number1.hashCode(), number2.hashCode());
    }

    // Testing strategy for Variable
    @Test
    public void testVariableToString() {
        Expression variable = new Variable("x");
        assertEquals("x", variable.toString());
    }

    @Test
    public void testVariableEqualsAndHashCode() {
        Expression variable1 = new Variable("x");
        Expression variable2 = new Variable("x");
        assertTrue(variable1.equals(variable2));
        assertEquals(variable1.hashCode(), variable2.hashCode());
    }

    // Testing strategy for Addition
    @Test
    public void testAdditionToString() {
        Expression left = new Number(5.0);
        Expression right = new Variable("x");
        Expression addition = new Addition(left, right);
        assertEquals("(5.0 + x)", addition.toString());
    }

    @Test
    public void testAdditionEqualsAndHashCode() {
        Expression left1 = new Number(5.0);
        Expression right1 = new Variable("x");
        Expression addition1 = new Addition(left1, right1);

        Expression left2 = new Number(5.0);
        Expression right2 = new Variable("x");
        Expression addition2 = new Addition(left2, right2);

        assertTrue(addition1.equals(addition2));
        assertEquals(addition1.hashCode(), addition2.hashCode());
    }

    // Testing strategy for Multiplication
    @Test
    public void testMultiplicationToString() {
        Expression left = new Variable("x");
        Expression right = new Variable("y");
        Expression multiplication = new Multiplication(left, right);
        assertEquals("(x * y)", multiplication.toString());
    }

    @Test
    public void testMultiplicationEqualsAndHashCode() {
        Expression left1 = new Variable("x");
        Expression right1 = new Variable("y");
        Expression multiplication1 = new Multiplication(left1, right1);

        Expression left2 = new Variable("x");
        Expression right2 = new Variable("y");
        Expression multiplication2 = new Multiplication(left2, right2);

        assertTrue(multiplication1.equals(multiplication2));
        assertEquals(multiplication1.hashCode(), multiplication2.hashCode());
    }
    
}
