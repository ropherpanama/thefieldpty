package com.otacm.thefieldpty.utils;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterConfiguration {

	public Twitter twitter; 
	private String username;
	
	public Twitter getInterface() {
		if(twitter == null) {
			if(!config())
				return null;
		}
		
		return twitter;
	}
	
	public String getUserName() {
		if(twitter != null)
			return username;
		else 
			return "Anonimo";
	}
	
	private boolean config() {
		try {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
					.setOAuthConsumerKey("In8JLfasOslH2ab7qEV0n1Sbt")
					.setOAuthConsumerSecret("JbDfFc1OzTSVZ8lLlIDGxUWvJ1fJB4elPplrhegCDbO6Wp89zL")
					.setOAuthAccessToken("2450753456-Ig26IrxHFbM9DsgDBc3MPFNAKgpujKCO0ibaXOV")
					.setOAuthAccessTokenSecret("QxOiyXBenpfKr8AuXjq9kSfLfTo3icsjuUvwCTihxsLGm");

			twitter = new TwitterFactory(cb.build()).getInstance();
			User user = twitter.verifyCredentials();
			username = user.getName();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
