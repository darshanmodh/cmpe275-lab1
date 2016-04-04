package edu.sjsu.cmpe275.lab1;

import java.io.IOException;

public class TweetServiceImpl implements TweetService {

    /***
     * Following is the dummy implementation of methods.
     * Students are expected to complete the actual implementation of these methods as part of lab completion.
     */

    public void tweet(String user, String message) throws IllegalArgumentException, IOException {
    	System.out.println("*** " + user + " tweet " + message + " with length of " + message.length() + " ***");
    	if (message.length() > 140 ) {
    		throw new IllegalArgumentException();
    	}
    }

    public void follow(String follower, String followee) throws IOException {
    	System.out.println("*** " + follower + " following " + followee + " ***");
    }

}
