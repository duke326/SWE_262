package Milestone5;

import MileStone2.json.JSONException;
import MileStone2.json.JSONObject;
import MileStone2.json.XML;
import static MileStone1.Milestone1.*;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MileStone5Test {
    final int FACTOR = 4;
    @Test
    public void futureSmallFileTest(){
//        Future<JSONObject> future;
        String xml = "<?xml version=\"1.0\"?>\n" +
                "<catalog>\n" +
                "   <book id=\"bk101\">\n" +
                "      <author>Gambardella, Matthew</author>\n" +
                "      <title>XML Developer's Guide</title>\n" +
                "      <genre>Computer</genre>\n" +
                "      <price>44.95</price>\n" +
                "      <publish_date>2000-10-01</publish_date>\n" +
                "      <description>An in-depth look at creating applications \n" +
                "      with XML.</description>\n" +
                "   </book>\n" +
                "   <book id=\"bk102\">\n" +
                "      <author>Ralls, Kim</author>\n" +
                "      <title>Midnight Rain</title>\n" +
                "      <genre>Fantasy</genre>\n" +
                "      <price>5.95</price>\n" +
                "      <publish_date>2000-12-16</publish_date>\n" +
                "      <description>A former architect battles corporate zombies, \n" +
                "      an evil sorceress, and her own childhood to become queen \n" +
                "      of the world.</description>\n" +
                "   </book>\n"+
                "</catalog>\n";
        Reader reader = new StringReader(xml);
        try{
            Future<JSONObject> future = XML.toJSONObjectFuture(reader);
            while(!future.isDone()){
                System.out.println("not yet");
                Thread.sleep(1000);
            }
            writeToDisk(future.get().toString(FACTOR));
            System.out.println(future.get().toString(FACTOR));
        }catch (JSONException ex){
            System.out.println("There is something wrong with XML.toJsonObjectFuture method");
        }catch (InterruptedException ex){
            System.out.println("There is something wrong with Thread.sleep method");
        }catch (ExecutionException ex){
            System.out.println("There is something wrong with future.get method");
        }catch (IOException ex){
            System.out.println("There is something wrong with writeToDisk method");
        }
    }

    @Test
    public void futureBigFileTest() throws Exception{
        // with hard code path
        File file = new File("D:/uci/SWE262/src/enwiki-20210220-abstract3.xml");
        FileReader reader = new FileReader(file);
        try{
            Future<JSONObject> future = XML.toJSONObjectFuture(reader);
            while(!future.isDone()){
                System.out.println("not yet");
                Thread.sleep(1000);
            }
            writeToDisk(future.get().toString(FACTOR));
            System.out.println(future.get().toString(FACTOR));
        }catch (JSONException ex){
            System.out.println("There is something wrong with XML.toJsonObjectFuture method");
        }catch (InterruptedException ex){
            System.out.println("There is something wrong with Thread.sleep method");
        }catch (ExecutionException ex){
            System.out.println("There is something wrong with future.get method");
        }catch (IOException ex){
            System.out.println("There is something wrong with writeToDisk method");
        }
    }

}
