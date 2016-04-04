package edu.sjsu.cmpe275.lab1;

import java.io.IOException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RetryAndDoStats implements MethodInterceptor {
	/***
	 * Following is the dummy implementation of advice. Students are expected to
	 * complete the required implementation as part of lab completion.
	 */

	String user = "";
	String message = "";
	int length = 0;
	String follower = "";
	String followee = "";
	String methodName = "";

	static int numberOfRetries;

	public Object invoke(MethodInvocation invocation) throws Throwable {

		methodName = invocation.getMethod().getName();
		// if number of retries = 0
		if (numberOfRetries == 0) {
			// if tweet() is called
			if (methodName.equals("tweet")) {
				user = invocation.getArguments()[0].toString();
				message = invocation.getArguments()[1].toString();
				length = message.length();

				// check current tweet's length with longest tweet attempted
				if (length > TweetStatsImpl.lengthOfLongestTweetAttempted) {
					TweetStatsImpl.lengthOfLongestTweetAttempted = length;
				}

				// check if previous user's tweet or new one
				if (TweetStatsImpl.messageLengthHashMap.containsKey(user) && length <= 140) {
					// store this tweet to HashMap with accumulation of length
					int previousLengthForUser = TweetStatsImpl.messageLengthHashMap.get(user);
					TweetStatsImpl.messageLengthHashMap.put(user, previousLengthForUser + length);
				} else if (length < 140) {
					// create new user and store this length
					TweetStatsImpl.messageLengthHashMap.put(user, length);
				}
			} // end of tweet() method
		} // end of number of retries

		try {
			invocation.proceed();

			// if follow() is called
			if (methodName.equals("follow")) {
				follower = invocation.getArguments()[0].toString();
				followee = invocation.getArguments()[1].toString();

				// user cannot follow himself
				if (!follower.equals(followee)) {
					// if followee is available then +1 his follower's list
					if (TweetStatsImpl.followerCountHashMap.containsKey(followee)) {
						int previousFollowersCount = TweetStatsImpl.followerCountHashMap.get(followee);
						TweetStatsImpl.followerCountHashMap.put(followee, previousFollowersCount + 1);
					} else {
						// create a user with name of followee
						TweetStatsImpl.followerCountHashMap.put(followee, 1);
					}
				} else {
					System.out.println("*** User cannot follow himself ***");
				}

				numberOfRetries = 0;
			} // end of follow() method
		} catch (IOException e) {
			// first check IOException 3 times
			numberOfRetries++;
			if (numberOfRetries < 4) {
				ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
				TweetService tweeter = (TweetService) ctx.getBean("tweetServiceProxy");

				System.out.println("IOException - Network failure. Retry attempt = " + numberOfRetries);

				// check tweet() method
				if (methodName.equals("tweet")) {
					user = invocation.getArguments()[0].toString();
					message = invocation.getArguments()[1].toString();
					tweeter.tweet(user, message);
				}

				// check follow() method
				if (methodName.equals("follow")) {
					follower = invocation.getArguments()[0].toString();
					followee = invocation.getArguments()[1].toString();
					tweeter.follow(follower, followee);
				}
			} else {
				// number of retries over, this tweet cannot be posted
				numberOfRetries = 0;
				System.out.println("Network failure. 3 retries completed.");
				String failedMethod = invocation.getMethod().getName();
				String arg1 = invocation.getArguments()[0].toString();
				String arg2 = invocation.getArguments()[1].toString();
				System.out.println(failedMethod + "(" + arg1 + ", " + arg2 + ") failed to complete execution.");
				// throw new IOException();
			}
		} catch (IllegalArgumentException e) {
			String failedMethod = invocation.getMethod().getName();
			String arg1 = invocation.getArguments()[0].toString();
			String arg2 = invocation.getArguments()[1].toString();
			System.out.println("IllegalArgumentException - Message with more than 140 characters not allowed.");
			System.out.println(failedMethod + "(" + arg1 + ", " + arg2 + ") failed to complete execution.");
			// throw new IllegalArgumentException();
		}
		return null;
	} // end of invoke() method
}
