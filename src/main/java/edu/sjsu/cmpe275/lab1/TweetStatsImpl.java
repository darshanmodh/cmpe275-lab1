package edu.sjsu.cmpe275.lab1;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeSet;

public class TweetStatsImpl implements TweetStats {

	/***
	 * Following is the dummy implementation of methods. Students are expected
	 * to complete the actual implementation of these methods as part of lab
	 * completion. This is NOT aspect. No MethodInterceptor
	 */

	static int lengthOfLongestTweetAttempted;
	static HashMap<String, Integer> messageLengthHashMap = new HashMap<String, Integer>();
	static HashMap<String, Integer> followerCountHashMap = new HashMap<String, Integer>();
	static TreeSet<String> mostFollowedUserTreeSet = new TreeSet<String>();
	static TreeSet<String> mostProductiveUserTreeSet = new TreeSet<String>();

	@Override
	public void resetStats() {
		// TODO Auto-generated method stub
		lengthOfLongestTweetAttempted = 0;
		messageLengthHashMap.clear();
		followerCountHashMap.clear();
		mostFollowedUserTreeSet.clear();
		mostProductiveUserTreeSet.clear();
	}

	@Override
	public int getLengthOfLongestTweetAttempted() {
		// TODO Auto-generated method stub
		return lengthOfLongestTweetAttempted;
	}

	@Override
	public String getMostFollowedUser() {
		// TODO Auto-generated method stub
		int mostFollowedUser = 0;
		for (Entry<String, Integer> entry : followerCountHashMap.entrySet()) {
			if (entry.getValue() > mostFollowedUser) {
				mostFollowedUserTreeSet.clear();
				mostFollowedUserTreeSet.add(entry.getKey());
				mostFollowedUser = entry.getValue();
			} else if (entry.getValue() == mostFollowedUser) {
				mostFollowedUserTreeSet.add(entry.getKey());
			}
		}
		// If no users were successfully followed, return null.
		if (mostFollowedUserTreeSet.size() == 0) {
			return null;
		} else {
			return mostFollowedUserTreeSet.first();
		}
	}

	@Override
	public String getMostProductiveUser() {
		// TODO Auto-generated method stub
		int maxLength = 0;
		for (Entry<String, Integer> entry : messageLengthHashMap.entrySet()) {
			if (entry.getValue() > maxLength) {
				mostProductiveUserTreeSet.clear();
				mostProductiveUserTreeSet.add(entry.getKey());
				maxLength = entry.getValue();
			} else if (entry.getValue() == maxLength) {
				mostProductiveUserTreeSet.add(entry.getKey());
			}
		}
		// If no users successfully tweeted, return null.
		if (mostProductiveUserTreeSet.size() == 0) {
			return null;
		} else {
			return mostProductiveUserTreeSet.first();
		}
	}

}
