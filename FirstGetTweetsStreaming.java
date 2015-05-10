//Emily Geyman
//Final Project - Part 1
//This program uses twitter4j (a library for the Twitter API) to scrape tweets
//
//FirstGetTweetsStreaming.java
//Version 1.0
//May 8, 2014

//SOURCE: I used and modified code from the following URL:
//http://stackoverflow.com/questions/23341215/extracting-tweets-of-a-specific-hashtag-using-twitter4j

//Javadoc for twitter4j: http://twitter4j.org/javadoc/index.html



import java.io.*;
import java.util.*;
import twitter4j.*;
// import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StallWarning;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
// import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;


public class FirstGetTweetsStreaming
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
      
      TwitterStream stream = new TwitterStreamFactory(cb.build()).getInstance();
      
      ArrayList<Status> tweetList = new ArrayList<Status>(); //Status is an interface--it represents one single user status
            
      StatusListener listener = new StatusListener()
      {
         @Override
         public void onStatus(Status status)
         {
            System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
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
     stream.sample("en");
     
     //Optional filtering:
//      FilterQuery fq = new FilterQuery();
//      String keywords[] = {"food", "breakfast", "lunch", "dinner"};
//      fq.track(keywords);   
//      twitterStream.filter(fq); 
     
     
   }
}