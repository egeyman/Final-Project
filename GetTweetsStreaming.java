//Emily Geyman
//Final Project - Part 1
//This program uses twitter4j (a library for the Twitter API) to scrape tweets
//
//GetTweetsStreaming.java
//Version 1.0
//May 8, 2014

// Javadoc for twitter4j: http://twitter4j.org/javadoc/index.html
// Source: I used and modified code from the following link:
// http://stackoverflow.com/questions/24607425/how-to-check-rate-limit-in-streaming-api-in-twitter

import java.io.*;
import java.util.*;
import twitter4j.*;
import twitter4j.StatusDeletionNotice;
import twitter4j.StallWarning;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.GeoLocation;
import twitter4j.FilterQuery;

import com.opencsv.CSVWriter; //for writing to CSV file

public class GetTweetsStreaming
{
   public static void main(String[] args) throws TwitterException, IOException
   {
      
      ConfigurationBuilder cb = new ConfigurationBuilder();
      cb.setDebugEnabled(true);
      //These are the keys I got by setting up an account and registering on Twitter's API website:
      cb.setOAuthConsumerKey("oRIGU4t4UH03ud0pjzWiGC0y0");
      cb.setOAuthConsumerSecret("zA5nxloU9yxcnnKbB7hgA9JIEcsBWFAD1OoMIDI3zgEIsxDCTk");
      cb.setOAuthAccessToken("3185612070-av4UD2wbyONwKYDh9WOfxmHttfY3ICEZnMV0612");
      cb.setOAuthAccessTokenSecret("eTdSz9GHZwa5Ud4BJzkuDKfh9FWBmEsXAvmR2tUp7Mbls");
      
      final String fileName = "May9US.csv";
      
      try
      {
         TwitterStream stream = new TwitterStreamFactory(cb.build()).getInstance();       
         final CSVWriter writer = new CSVWriter(new FileWriter(fileName));               
         StatusListener listener = new StatusListener()
         {
            @Override
            public void onStatus(Status status)
            {
               try
               {
                  String text = status.getText();
                  GeoLocation loc = status.getGeoLocation();
                  if (loc != null)
                  {
                     double lat = loc.getLatitude();
                     double lon = loc.getLongitude();
                     String[] tweet = (String.valueOf(lat) + "," + String.valueOf(lon) + "," + text).split(",");
                     writer.writeNext(tweet);
                     writer.flush();
                     System.out.println(Arrays.toString(tweet));
                  }
//                   String[] tweet = (status.getUser().getScreenName() + "," + text).split(",");
//                   writer.writeNext(tweet);
//                   writer.flush();
                  
                  
               }
               catch (IOException e)
               {
                  System.out.println("Could not print to .csv file");
               }
            }
            @Override
            public void onStallWarning(StallWarning arg0)
            {
               //System.out.println("Stall warning");
            }
            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice)
            {
               //System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }
            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses)
            {
               //System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }
            @Override
            public void onScrubGeo(long userId, long upToStatusId)
            {
               //System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }
            @Override
            public void onException(Exception e)
            {
               e.printStackTrace();
            }
         };
        
        
         stream.addListener(listener);
         FilterQuery filterQuery = new FilterQuery();
         // Source for longitudes and latitudes: http://en.wikipedia.org/wiki/Extreme_points_of_the_United_States
         double[][] boundingbox = { { -174.375000, 21.779905}, { -65.039063,  71.965388} };
         filterQuery.locations(boundingbox);
         String[] lan = {"en"};
         filterQuery.language(lan);
         stream.filter(filterQuery);
         //stream.sample("en"); 
        
      }
      catch (Exception e)
      {
         System.out.println("Could not print to .csv file");
      }
   }
}