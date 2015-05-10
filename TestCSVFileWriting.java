import java.io.*;
import java.util.*;
import com.opencsv.CSVWriter;

public class TestCSVFileWriting
{
   public static void main(String[] args)
   {   
      String fileName = "practice.csv";
      
      CSVWriter writer = null;
      
      try
      {
         writer = new CSVWriter(new FileWriter(fileName));
         
         String[] tweet1 = "0.0,0.0,Blah blah blah".split(",");
         String[] tweet2 = "1.0,1.0,Blah blah".split(",");
         
         writer.writeNext(tweet1);
         writer.writeNext(tweet2);
         writer.close();
      }
      catch (Exception e)
      {
         System.out.println("Could not print to .csv file");
      }
   }
}