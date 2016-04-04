package edu.sjsu.cmpe275.lab1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        /***
         * Following is the dummy implementation of App to demonstrate bean creation with Application context.
         * Students may alter the following code as required.
         */

        ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        TweetService tweeter = (TweetService) ctx.getBean("tweetServiceProxy");
        TweetStats tweetStats = (TweetStats) ctx.getBean("tweetStats");

        try {
            tweeter.tweet("alex", "first tweet");
            tweeter.follow("bob", "alex");
            //tweeter.tweet("bob", "second");
            tweeter.tweet("bob", "DarshanModh");
            tweeter.follow("carl", "alex");
            tweeter.follow("bob", "bob");
            tweeter.tweet("alex", "cS6JDD4vYh0Vb5xE3jg8wlMnXfa352aSTHoDo47B05Lw0Gm3NDrDCYC49ZiI6RyOF7f0yz2ONK0rbrYx98sy2TQ9NzZjNZaDRmHxqfgUeTas5t4q4q1mDaRZagPnVbUS6EeCVybCoZEJySW39yeY6r1oKHcaaYHhkKUFKETAEh2xcJAVzlqHoaxaE69ksiWvkleCHE9al");
            tweeter.tweet("carl", "final tweet");
            tweeter.follow("carl", "bob");
            tweeter.follow("alex", "bob");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Most productive user: " + tweetStats.getMostProductiveUser());
        System.out.println("Most followed user: " + tweetStats.getMostFollowedUser());
        System.out.println("Length of the longest tweet: " + tweetStats.getLengthOfLongestTweetAttempted());
    }
}
