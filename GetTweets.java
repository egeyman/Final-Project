//Emily Geyman
//Final Project - Part 1
//This program uses twitter4j (a library for the Twitter API) to scrape tweets
//
//GetTweets.java
//Version 1.0
//May 5, 2014

//SOURCE: I used and modified code from the following URL:
//http://stackoverflow.com/questions/23341215/extracting-tweets-of-a-specific-hashtag-using-twitter4j

//Javadoc for twitter4j: http://twitter4j.org/javadoc/index.html

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import twitter4j.GeoQuery;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.*;

public class GetTweets
{
   public static void main(String[] args)
   {
      ConfigurationBuilder cb = new ConfigurationBuilder();
      cb.setDebugEnabled(true);
      //These are the keys I got by setting up an account and registering on Twitter's API website:
      cb.setOAuthConsumerKey("se9FH9ANnGXz6AdCXOtXp7KHw");
      cb.setOAuthConsumerSecret("SYxH5a3Ea89Q0wlRpoBaF0B1WQaHeaP7zYGV9UPiGlHbbDxtku");
      cb.setOAuthAccessToken("3185612070-j4It4chg8dwVDzzSxzuD2yCOAwAbZpY2KqzOe3A");
      cb.setOAuthAccessTokenSecret("gE51nrZi2f14mln3nV7CXORaSkle96V42zOl8jdc1RPmw");
      Twitter twitter = new TwitterFactory(cb.build()).getInstance();
      Query query = new Query("breakfast");
      
      int totalTweets = 500;
      ArrayList<Status> tweetList = new ArrayList<Status>(); //Status is an interface--it represents one single user status
      
      //
      while (tweetList.size() < totalTweets)
      {
         //the max number of tweets you can return per page is 100:
         if (totalTweets - tweetList.size() > 100)
            query.setCount(100);
         else 
            query.setCount(totalTweets - tweetList.size());
         try
         {
            QueryResult result = twitter.search(query);
            tweetList.addAll(result.getTweets());
         }
         catch (TwitterException e)
         {
            System.out.println("Error gathering tweets: " + e);
         }                
      
         for (int i = 0; i < tweetList.size(); i++)
         {
            Status t = (Status) tweetList.get(i);
            String user = t.getUser().getScreenName();
            String msg = t.getText();
            GeoLocation loc = t.getGeoLocation();
            if (loc != null)
            {
               double lat = loc.getLatitude();
               double lon = loc.getLongitude();
               System.out.println(i + " USER: " + user + " lat: " + lat + " lon: " + lon + " wrote: " + msg + "\n");
            }
         }
       }
     } 
}