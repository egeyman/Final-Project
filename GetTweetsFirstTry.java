//Emily Geyman
//Final Project - Part 1
//This program uses twitter4j (a library for the Twitter API) to scrape tweets
//
//GetTweetsFirstTry.java
//Version 1.0
//May 5, 2014

//SOURCE: I used and modified code from the following URL:
//http://stackoverflow.com/questions/23341215/extracting-tweets-of-a-specific-hashtag-using-twitter4j

//To-do: 
//Use methods "until" and "since" etc to narrow search results of tweets to within a given window of time
//http://twitter4j.org/javadoc/index.html

//package twiter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class GetTweetsFirstTry
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
      Query query = new Query("#food");
      int numberOfTweets = 5000;
      long lastID = Long.MAX_VALUE;
      ArrayList<Status> tweetList = new ArrayList<Status>(); //Status is an interface--it represents one single user status
      while (tweetList.size() < numberOfTweets) 
      {
         if (numberOfTweets - tweetList.size() > 100)
            query.setCount(100);
         else 
            query.setCount(numberOfTweets - tweetList.size());
         try
         {
           QueryResult result = twitter.search(query);
           tweetList.addAll(result.getTweets());
           System.out.println("Gathered " + tweetList.size() + " tweets"+"\n");
           for (Status t: tweetList) 
           {
             if(t.getId() < lastID) 
                 lastID = t.getId();
           }
         }
         catch (TwitterException te)
         {
            System.out.println("Couldn't connect: " + te);
         }
      
         query.setMaxId(lastID-1);
      
         for (int i = 0; i < tweetList.size(); i++) {
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
            System.out.println(i + " USER: " + user + " wrote: " + msg + "\n");
         }
     }
   }     
}

      //Another attempt to set up configuration without using ConfigurationBuilder:
//       Configuration c = cb.build();
//       Authorization a = c.getInstance();
//       Twitter twitter = new TwitterFactory(a);
//       
//       TwitterStreamImpl twitterStream = new TwitterStreamImpl(c, a);
//       StatusListener sl = new StatusAdaptor();
//       twitterStream.addListener(sl);
//       Dispatcher d = twitterStream.getDispatcher();
//       ImputStream i = new InputStream();
//       StatusStreamImpl ss = new StatusStreamImpl(d, i, c);