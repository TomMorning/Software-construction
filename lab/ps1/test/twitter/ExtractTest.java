/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "aerf", "rivest talk in 30 minutes #hype @alyssa", d3);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }

    @Test
    public void testGetTimespanEmptyTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList());

        assertNull("expected null for empty tweets", timespan);
    }

    @Test
    public void testGetTimespanSingleTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));

        assertEquals("expected start and end to be the same for a single tweet", tweet1.getTimestamp(), timespan.getStart());
        assertEquals("expected start and end to be the same for a single tweet", tweet1.getTimestamp(), timespan.getEnd());
    }

    @Test
    public void testGetTimespanMultipleTweetsSameTimestamp() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, new Tweet(4, "test", "test tweet", d1)));

        assertEquals("expected start and end to be the same for tweets with same timestamp", d1, timespan.getStart());
        assertEquals("expected start and end to be the same for tweets with same timestamp", d1, timespan.getEnd());
    }

    @Test
    public void testGetTimespanTweetsNotInChronologicalOrder() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet1));

        assertEquals("expected start to be the earliest timestamp", d1, timespan.getStart());
        assertEquals("expected end to be the latest timestamp", d2, timespan.getEnd());
    }


    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    @Test
    public void testGetMentionedUsersOneMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));

        assertTrue("expected singleton set", mentionedUsers.size() == 1);
        assertTrue("expected @alyssa", mentionedUsers.contains("alyssa"));
    }

    @Test
    public void testGetMentionedUsersEmptyTweets() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList());

        assertTrue("expected empty set for empty tweets", mentionedUsers.isEmpty());
    }

    @Test
    public void testGetMentionedUsersMultipleMentions() {
        Tweet tweet = new Tweet(4, "test", "@user1 @user2", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet));

        assertTrue("expected set size of 2", mentionedUsers.size() == 2);
        assertTrue("expected @user1", mentionedUsers.contains("user1"));
        assertTrue("expected @user2", mentionedUsers.contains("user2"));
    }

    @Test
    public void testGetMentionedUsersInvalidMentions() {
        Tweet tweet = new Tweet(5, "test", "email@domain.com @1user.co", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet));

        assertTrue("expected empty set for invalid mentions", mentionedUsers.isEmpty());
    }

    @Test
    public void testGetMentionedUsersMixedValidInvalidMentions() {
        Tweet tweet = new Tweet(6, "test", "email@domain.com @user", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet));

        assertTrue("expected set size of 1", mentionedUsers.size() == 1);
        assertTrue("expected @user", mentionedUsers.contains("user"));
    }

    @Test
    public void testGetMentionedUsersCaseInsensitivity() {
        Tweet tweet = new Tweet(7, "test", "@USER", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet));

        assertTrue("expected @user in lowercase", mentionedUsers.contains("user"));
    }

    @Test
    public void testGetMentionedUsersDuplicateMentions() {
        Tweet tweet = new Tweet(8, "test", "@user @user @USER", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet));

        assertTrue("expected set size of 1 for duplicate mentions", mentionedUsers.size() == 1);
        assertTrue("expected @user", mentionedUsers.contains("user"));
    }


    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
