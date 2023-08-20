/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.File;
import java.io.IOException;

/**
 * Example program using GraphPoet.
 * 
 * <p>PS2 instructions: you are free to change this example class.
 */
public class Main {
    
    /**
     * Generate example poetry.
     * 
     * @param args unused
     * @throws IOException if a poet corpus file cannot be found or read
     */
    public static void main(String[] args) throws IOException {
        final GraphPoet nimoy = new GraphPoet(new File("src/poet/mugar-omni-theater.txt"));
        final String input = "Test the system.";
        System.out.println(input + "\n>>>\n" + nimoy.poem(input));

        final GraphPoet cosmicPoet = new GraphPoet(new File("src/poet/cosmic.txt"));

        String input1 = "Dreamers gaze.";
        System.out.println(input1 + "\n>>>\n" + cosmicPoet.poem(input1));

        String input2 = "Stars collide.";
        System.out.println("\n" + input2 + "\n>>>\n" + cosmicPoet.poem(input2));

        String input3 = "Planets shine.";
        System.out.println("\n" + input3 + "\n>>>\n" + cosmicPoet.poem(input3));
    }
    
}
