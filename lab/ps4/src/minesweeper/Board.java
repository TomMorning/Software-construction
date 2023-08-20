/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Minesweeper board consisting of mines and safe squares.
 */
public class Board {

    // TODO: Abstraction function, rep invariant, rep exposure, thread safety
    private final int width;
    private final int height;
    private final Square[][] grid;
    private final boolean[][] revealed;
    private final boolean[][] flagged;
    private AtomicInteger boardState = new AtomicInteger(0);  // 0 = ongoing, 1 = over

    /**
     * Thread Safety Argument:
     * The board is thread-safe because all methods that modify the state of the board are synchronized
     * using the lock object. This ensures that only one thread can execute these methods at any given
     * time, preventing race conditions.
     */
    private final Object lock = new Object();

    /**
     * Create a Minesweeper board of given width and height, and with mines placed randomly.
     * @param width  width of the board
     * @param height height of the board
     * @param mines  number of mines to be placed
     */
    public Board(int width, int height, int mines) {
        this.width = width;
        this.height = height;
        this.grid = new Square[width][height];
        this.revealed = new boolean[width][height];
        this.flagged = new boolean[width][height];

        // Initialize board with safe squares
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = Square.SAFE;
            }
        }

        // Randomly place mines
        int placedMines = 0;
        while (placedMines < mines) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            if (grid[x][y] == Square.SAFE) {
                grid[x][y] = Square.MINE;
                placedMines++;
            }
        }

        checkRep();
    }

    public Board(Square grid[][]) {
        this.width = grid.length;
        this.height = grid[0].length;
        this.grid = grid;
        this.revealed = new boolean[width][height];
        this.flagged = new boolean[width][height];
    }

    public boolean dig(int x, int y) {
        synchronized (lock) {
            checkRep();
            if (x < 0 || x >= width || y < 0 || y >= height || revealed[x][y] || flagged[x][y]) {
                return false;  // Invalid reveal operation
            }
            revealed[x][y] = true;
            boolean result = grid[x][y] == Square.MINE;// Returns true if a mine is revealed
            if (result) {
                boardState.compareAndSet(0, 1);  // Game over
            }
            return result;
        }
    }

    public boolean flag(int x, int y) {
        synchronized (lock) {
            checkRep();
            if (x < 0 || x >= width || y < 0 || y >= height || revealed[x][y]) {
                return false;  // Invalid flag operation
            }
            flagged[x][y] = !flagged[x][y];  // Toggle the flag
            return flagged[x][y];  // Return the current flag status
        }
    }

    public Square getSquare(int x, int y) {
        return grid[x][y];
    }

    public boolean isRevealed(int x, int y) {
        return revealed[x][y];
    }

    public boolean isFlagged(int x, int y) {
        return flagged[x][y];
    }

    private void checkRep() {
        assert width > 0 && height > 0 : "Board dimensions should be positive";
        assert grid != null && grid.length == width && grid[0].length == height : "Board grid mismatch";
    }

    public void deflag(int x, int y) {
        synchronized (lock) {
            checkRep();
            if (x < 0 || x >= width || y < 0 || y >= height || revealed[x][y]) {
                return;  // Invalid deflag operation
            }
            flagged[x][y] = false;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (boardState.get() == 0) {
            for (int y = height - 1; y >= 0; y--) {
                sb.append(y);
                sb.append(" ");
                for (int x = 0; x < width; x++) {
                    if (revealed[x][y]) {
                        sb.append(grid[x][y].getValue());
                    } else if (flagged[x][y]) {
                        sb.append("F");
                    } else {
                        sb.append("-");
                    }
                    sb.append(" ");
                }
                sb.append("\n");
            }
            sb.append("  ");
            for (int x = 0; x < width; x++) {
                sb.append(x);
                sb.append(" ");
            }
        } else {
            for (int y = height - 1; y >= 0; y--) {
                sb.append(y);
                sb.append(" ");
                for (int x = 0; x < width; x++) {
                    if (grid[x][y] == Square.MINE) {
                        sb.append("1");
                    } else {
                        sb.append(" ");
                    }
                    sb.append(" ");
                }
                sb.append("\n");
            }
            sb.append("  ");
            for (int x = 0; x < width; x++) {
                sb.append(x);
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public enum Square {
        MINE(1), SAFE(0);
        int value;
        Square(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
        public static Square getSquare(int value) {
            return value == 1 ? MINE : SAFE;
        }

    }
    // TODO: Specify, test, and implement in problem 2

}
