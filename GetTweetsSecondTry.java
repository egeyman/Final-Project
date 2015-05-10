//Emily Geyman
//Final Project - Part 1
//This program uses twitter4j (a library for the Twitter API) to scrape tweets
//
//GetTweetsSecondTry.java
//Version 1.0
//May 5, 2014

//SOURCE: I used and modified code from the following URL:
//http://stackoverflow.com/questions/23341215/extracting-tweets-of-a-specific-hashtag-using-twitter4j

//Javadoc for twitter4j: http://twitter4j.org/javadoc/index.html

package twitter4j;
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

public class GetTweetsSecondTry
{
   public static void main(String[] args)
   {
      ConfigurationBuilder cb = new ConfigurationBuilder();
      cb.setDebugEnabled(true);
      //These are the keys I got by setting up an account and registering on Twitter's API website:
      cb.setOAuthConsumerKey("oRIGU4t4UH03ud0pjzWiGC0y0");
      cb.setOAuthConsumerSecret("zA5nxloU9yxcnnKbB7hgA9JIEcsBWFAD1OoMIDI3zgEIsxDCTk");
      cb.setOAuthAccessToken("3185612070-av4UD2wbyONwKYDh9WOfxmHttfY3ICEZnMV0612");
      cb.setOAuthAccessTokenSecret("eTdSz9GHZwa5Ud4BJzkuDKfh9FWBmEsXAvmR2tUp7Mbls");
      Twitter twitter = new TwitterFactory(cb.build()).getInstance();
      
      //Query query = new Query().geoCode(new GeoLocation(39.8282, -98.5795), 3000.0, "Query.MILES"); //lat lon is geographic center of U.S.
      Query query = new Query().lang("en");
//       query.setSince("2015-05-06");
//       query.setUntil("2015-05-07");
      
      int totalTweets = 500;
      long lastID = Long.MAX_VALUE;
      ArrayList<Status> tweetList = new ArrayList<Status>(); //Status is an interface--it represents one single user status
      
      FileWriter fileWriter = null;
      
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
            for (Status t: tweetList) 
            {
               if(t.getId() < lastID) 
                  lastID = t.getId();
            }
            try
            {
//                fileWriter = new FileWriter("CollectedTweets.csv");
               for (int i = 0; i < tweetList.size(); i++)
               {
                  Status t = (Status) tweetList.get(i);
                  String msg = t.getText();
                  GeoLocation loc = t.getGeoLocation();
                  if (loc != null)
                  {
                     double lat = loc.getLatitude();
                     double lon = loc.getLongitude();
                     System.out.println(" lat: " + lat + " lon: " + lon + " wrote: " + msg + "\n");
//                      fileWriter.append(String.valueOf(lat));
//                      fileWriter.append(",");
//                      fileWriter.append(String.valueOf(lon));
//                      fileWriter.append(",");
//                      fileWriter.append(msg);
//                      fileWriter.append("\n");
                  }
               }
            }
            catch (Exception e)
            {
               System.out.println("Could not print to .csv file");
            }
            
            //Update lastId for collecting the next batch of tweets:
            query.setMaxId(lastID-1);
         }
         catch (TwitterException e)
         {
            System.out.println("Error gathering tweets: " + e);
         }
      }
   }
}