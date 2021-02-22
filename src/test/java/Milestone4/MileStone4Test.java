package Milestone4;

import MileStone2.json.*;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import java.util.function.Function;
import java.util.stream.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MileStone4Test {

    @Test
    public void forEachTest(){
        System.out.println("~~~~~~forEachTest~~~~~~~~~~");
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
        //JSONObject obj = XML.toJSONObject("<Books><book><title>AAA</title><author>ASmith</author></book><book><title>BBB</title><author>BSmith</author></book></Books>");
        JSONObject obj = XML.toJSONObject(xml);
        obj.toStream().forEach(node -> System.out.println(node.toString()));
    }

    @Test
    public void forEachTest2(){
        System.out.println("~~~~~~forEachTest2~~~~~~~~~~");
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
        //JSONObject obj = XML.toJSONObject("<Books><book><title>AAA</title><author>ASmith</author></book><book><title>BBB</title><author>BSmith</author></book></Books>");
        JSONObject obj = XML.toJSONObject(xml);
        obj.toStream().forEach(node -> {if(node.has("description"))System.out.println(node.toString());});
    }

    @Test
    public void mapTest(){
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
        //JSONObject obj = XML.toJSONObject("<Books><book><title>AAA</title><author>ASmith</author></book><book><title>BBB</title><author>BSmith</author></book></Books>");
        JSONObject obj = XML.toJSONObject(xml);
        List<String> titles = obj.toStream().filter(node -> node.has("title")).map(node ->(String) node.get("title")
        ).collect(Collectors.toList());
        List<String> expTitles  = new LinkedList<>();
        expTitles.add("XML Developer's Guide");
        expTitles.add("Midnight Rain");
        compareToList(titles,expTitles);
    }

    private void compareToList(List<String> titles, List<String> expTitles) {
        assertTrue("result list lengths should be equal",titles.size()==expTitles.size());
        for(int i = 0 ; i < titles.size() ; i ++){
            assertEquals(expTitles.get(i),titles.get(i));
        }
    }

    @Test
    public void filterTest(){
        System.out.println("~~~~~~~~~~filterTest~~~~~~~~~~");
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
        //JSONObject obj = XML.toJSONObject("<Books><book><title>AAA</title><author>ASmith</author></book><book><title>BBB</title><author>BSmith</author></book></Books>");
        JSONObject obj = XML.toJSONObject(xml);
        obj.toStream().filter(node -> node.has("author")).forEach(node -> System.out.println(node.toString()));
    }


}
