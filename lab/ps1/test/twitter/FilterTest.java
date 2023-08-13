/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * Testing strategy for writtenBy:
     *
     * partition:
     *   1. tweets size: 0, 1, >1
     *   2. username present in tweets: no, once, more than once
     *
     * Testing strategy for inTimespan:
     *
     * partition:
     *   1. tweets size: 0, 1, >1
     *   2. timespan covering: all before, all after, some within
     *
     * Testing strategy for containing:
     *
     * partition:
     *   1. tweets size: 0, 1, >1
     *   2. words size: 0, 1, >1
     *   3. words case: matching exactly, differing by case
     *   4. words presence: none in tweets, some in tweets, all in tweets
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "john", "this is a later tweet", d3);
    private static final Tweet tweet4 = new Tweet(4, "charlie", "Sometimes silence speaks volumes.", Instant.now());

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // covers tweets size = 0
    @Test
    public void testWrittenByNoTweets() {
        List<Tweet> writtenBy = Filter.writtenBy(new ArrayList<>(), "alyssa");
        assertEquals("expected empty list", 0, writtenBy.size());
    }

    // covers tweets size = 1, username not present
    @Test
    public void testWrittenBySingleTweetUsernameNotPresent() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1), "bbitdiddle");
        assertEquals("expected empty list", 0, writtenBy.size());
    }

    // covers tweets size = 1, username present once
    @Test
    public void testWrittenBySingleTweetUsernamePresent() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1), "alyssa");
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }

    // covers tweets size > 1, username not present
    @Test
    public void testWrittenByMultipleTweetsUsernameNotPresent() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet1), "bbitdiddle");
        assertEquals("expected empty list", 0, writtenBy.size());
    }

    // covers tweets size > 1, username present once (Test already present)
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }

    // covers tweets size > 1, username present more than once
    @Test
    public void testWrittenByMultipleTweetsMultipleResults() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet1, tweet2), "alyssa");
        assertEquals("expected list of size 2", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }


    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }

    // covers tweets size = 0
    @Test
    public void testInTimespanNoTweets() {
        Timespan timespan = new Timespan(d1, d2);
        List<Tweet> inTimespan = Filter.inTimespan(new ArrayList<>(), timespan);
        assertEquals("expected empty list", 0, inTimespan.size());
    }

    // covers timespan that includes all tweets
    @Test
    public void testInTimespanAllIncluded() {
        Timespan timespan = new Timespan(d1.minusSeconds(1), d3.plusSeconds(1));
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3), timespan);
        assertEquals("expected list of size 3", 3, inTimespan.size());
    }

    // covers timespan before all tweets
    @Test
    public void testInTimespanBeforeAll() {
        Timespan timespan = new Timespan(d1.minusSeconds(60), d1.minusSeconds(1));
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3), timespan);
        assertEquals("expected empty list", 0, inTimespan.size());
    }

    // covers timespan after all tweets
    @Test
    public void testInTimespanAfterAll() {
        Timespan timespan = new Timespan(d3.plusSeconds(1), d3.plusSeconds(60));
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3), timespan);
        assertEquals("expected empty list", 0, inTimespan.size());
    }

    // covers timespan that includes some tweets
    @Test
    public void testInTimespanPartialIncluded() {
        Timespan timespan = new Timespan(d1.minusSeconds(1), d2.plusSeconds(1));
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3), timespan);
        assertEquals("expected list of size 2", 2, inTimespan.size());
    }

    // covers timespan that overlaps with some tweets but does not fully include them
    @Test
    public void testInTimespanOverlap() {
        Timespan timespan = new Timespan(d2.minusSeconds(1), d2.plusSeconds(1));
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3), timespan);
        assertEquals("expected list of size 1", 1, inTimespan.size());
        assertTrue("expected list to contain tweet2", inTimespan.contains(tweet2));
    }


    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }


    // covers tweets size = 0
    @Test
    public void testContainingNoTweets() {
        List<Tweet> containing = Filter.containing(new ArrayList<>(), Arrays.asList("talk"));
        assertTrue("expected empty list", containing.isEmpty());
    }

    // covers words list is empty
    @Test
    public void testContainingEmptyWordsList() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4), new ArrayList<>());
        assertTrue("expected empty list", containing.isEmpty());
    }

    // covers all tweets contain at least one word from the list
    @Test
    public void testContainingAllTweetsMatch() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk", "rivest"));
        assertEquals("expected list of size 2", 2, containing.size());
    }

    // covers no tweet contains the word
    @Test
    public void testContainingNoTweetsMatch() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4), Arrays.asList("missingword"));
        assertTrue("expected empty list", containing.isEmpty());
    }

    // covers some tweets contain the word
    @Test
    public void testContainingPartialTweetsMatch() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4), Arrays.asList("talk", "silence"));
        assertEquals("expected list of size 3", 3, containing.size());
    }

    // covers case insensitivity
    @Test
    public void testContainingCaseInsensitivity() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet4), Arrays.asList("TALK", "SILENCE"));
        assertEquals("expected list of size 2", 2, containing.size());
        assertTrue("expected list to contain tweet1 and tweet4", containing.containsAll(Arrays.asList(tweet1, tweet4)));
    }


    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
