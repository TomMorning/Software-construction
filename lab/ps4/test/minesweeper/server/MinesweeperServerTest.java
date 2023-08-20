/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper.server;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

/**
 * TODO
 */
public class MinesweeperServerTest {
    
    // TODO
    // This is a hypothetical function for the sake of the example
    void handleClient(BufferedReader input, PrintWriter output) throws IOException {
        // This is a very simple example.
        // Here we expect a coordinate input from the client (like "2,3") and
        // the server will just respond with "SAFE" or "MINE".
        String line = input.readLine();
        if (line.equals("2,3")) {
            output.println("MINE");
        } else {
            output.println("SAFE");
        }
    }

    @Test
    public void testHandleClient() throws IOException {
        // Mockup client sends "2,3"
        String inString = "2,3\n";
        ByteArrayInputStream inBytes = new ByteArrayInputStream(inString.getBytes());
        ByteArrayOutputStream outBytes = new ByteArrayOutputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(inBytes));
        PrintWriter out = new PrintWriter(outBytes, true);

        handleClient(in, out);

        // Check that it responded with "MINE"
        assertEquals("MINE\n", outBytes.toString());
    }
}
