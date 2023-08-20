/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * TODO: Description
 */
public class BoardTest {
    
    // TODO: Testing strategy
    // Testing strategy:
    // - Test board creation with different dimensions and mine counts
    // - Test revealing a square and its effects
    // - Test flagging a square

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // TODO: Tests
    @Test
    public void testBoardCreation() {
        Board board = new Board(5, 5, 5);
        Assert.assertNotNull(board);
        // TODO: Further tests to check the board's state, perhaps by adding more visibility methods to the Board class
    }

    @Test
    public void testRevealSafeSquare() {
        Board board = new Board(10, 10, 10);
        assertFalse("Shouldn't reveal a mine", board.dig(0, 0) && board.getSquare(0, 0) == Board.Square.MINE);
        assertTrue("Square should be revealed", board.isRevealed(0, 0));
    }

    @Test
    public void testRevealMineSquare() {
        Board board = new Board(1, 1, 1);  // Only one square and it's a mine
        assertTrue("Should reveal a mine", board.dig(0, 0) && board.getSquare(0, 0) == Board.Square.MINE);
    }

    @Test
    public void testFlagSquare() {
        Board board = new Board(10, 10, 10);
        assertTrue("Square should be flagged", board.flag(0, 0));
        assertTrue("Square should remain flagged", board.isFlagged(0, 0));
        assertTrue("Square should be unflagged", !board.flag(0, 0) && !board.isFlagged(0, 0));
    }

    @Test
    public void testRevealOutOfBound() {
        Board board = new Board(10, 10, 10);
        assertFalse(board.dig(10, 10));  // This should cause an assertion error due to the checkRep method
    }

    @Test
    public void testFlagOutOfBound() {
        Board board = new Board(10, 10, 10);
        assertFalse(board.flag(10, 10));  // This should cause an assertion error due to the checkRep method
    }

    @Test
    public void testRevealFlaggedSquare() {
        Board board = new Board(10, 10, 10);
        board.flag(0, 0);
        assertFalse("Shouldn't reveal a flagged square", board.dig(0, 0));
    }
}
