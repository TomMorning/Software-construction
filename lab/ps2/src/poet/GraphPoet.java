/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();

    // Abstraction function:
    //   Represents a graph of word affinities based on a given corpus.
    // Representation invariant:
    //   graph is not null
    // Safety from rep exposure:
    //   graph is private and final, and is never returned or passed to any public methods.
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        try (Stream<String> lines = Files.lines(corpus.toPath())) {
            List<String> words = lines.flatMap(line -> Arrays.stream(line.split("\\W+")))
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());

            for (int i = 0; i < words.size() - 1; i++) {
                String word1 = words.get(i);
                String word2 = words.get(i + 1);
                graph.set(word1, word2, graph.targets(word1).getOrDefault(word2, 0) + 1);
            }
        }
        checkRep();

        // TODO: checkRep();
    }

    
    // TODO checkRep
    private void checkRep() {
        assert graph != null;
    }
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        List<String> words = Arrays.asList(input.split("\\W+"));
        StringBuilder poem = new StringBuilder();

        for (int i = 0; i < words.size() - 1; i++) {
            String word1 = words.get(i).toLowerCase();
            String word2 = words.get(i + 1).toLowerCase();
            poem.append(words.get(i)).append(" ");

            Map<String, Integer> bridges1 = graph.targets(word1);
            Map<String, Integer> bridges2 = graph.targets(word2);
            String bestBridge = null;
            int maxWeight = -1;

            for (String bridge : bridges1.keySet()) {
                if (bridges2.containsKey(bridge) && bridges1.get(bridge) + bridges2.get(bridge) > maxWeight) {
                    maxWeight = bridges1.get(bridge) + bridges2.get(bridge);
                    bestBridge = bridge;
                }
            }

            if (bestBridge != null) {
                poem.append(bestBridge).append(" ");
            }
        }
        poem.append(words.get(words.size() - 1));

        return poem.toString();
    }


    @Override
    public String toString() {
        return graph.toString();
    }
    
}
