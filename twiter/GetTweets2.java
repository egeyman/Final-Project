// Emily Geyman
// Scrape tweets

package twiter;
import java.io.PrintWriter;
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

public class GetTweets2
{
   public static void main(String[] args)
   {
      System.out.println("asdf");
      try
      {
         System.out.println("hello super smart Emily");
         twitter4j.Twitter twitter =  TwitterFactory.getSingleton();
         Query query = new Query("cupcakes");
         QueryResult result = twitter.search(query);
         for (Status status : result.getTweets()) 
         {
             System.out.println("@" + status.getUser().getScreenName() + " : " + status.getText() + " : " + status.getGeoLocation());
         }
      }
      catch (Exception e)
      {
         System.out.println("Exception");
      }
   }      
}