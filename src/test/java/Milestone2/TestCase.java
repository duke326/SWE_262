package Milestone2;

import MileStone2.json.JSONObject;
import MileStone2.json.XML;
import org.junit.Assert.*;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

public class TestCase {
    /**
     * Valid XML to JSONObject
     */
    @Test
    public void shouldHandleSimpleXML() {
        String xmlStr =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                        "<addresses xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""+
                        "   xsi:noNamespaceSchemaLocation='test.xsd'>\n"+
                        "   <address>\n"+
                        "       <name>Joe Tester</name>\n"+
                        "       <street>[CDATA[Baker street 5]</street>\n"+
                        "       <NothingHere/>\n"+
                        "       <TrueValue>true</TrueValue>\n"+
                        "       <FalseValue>false</FalseValue>\n"+
                        "       <NullValue>null</NullValue>\n"+
                        "       <PositiveValue>42</PositiveValue>\n"+
                        "       <NegativeValue>-23</NegativeValue>\n"+
                        "       <DoubleValue>-23.45</DoubleValue>\n"+
                        "       <Nan>-23x.45</Nan>\n"+
                        "       <ArrayOfNum>1, 2, 3, 4.1, 5.2</ArrayOfNum>\n"+
                        "   </address>\n"+
                        "</addresses>";

        String expectedStr =
                "{\"id\":1,\"age\":2,\"name\":\"zhang\"}";

        compareReaderToJSONObject(xmlStr, expectedStr);

    }

    // test if new method one could get the correct result with valid input
//    @Test
//    public void mileStoneTest1() throws IOException {
//        String xmlStr =
//                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                        "<addresses xmlns:xsi=\"http://www.w3.org/2020/XMLSchema-instance\"" +
//                        " xsi:noNamespaceSchemaLocation='test.xsd'>\n" +
//                        " <address>\n" +
//                        " <name>[CDATA[Joe &amp; T &gt; e &lt; s &quot; t &apos; er]]</name>\n" +
//                        " <street>Yale street 1224</street>\n" +
//                        " <ArrayOfNum>1.3, 2.4, 3, 4, 5</ArrayOfNum>\n" +
//                        " </address>\n" +
//                        "</addresses>";
//        String expectedStr =
//                "{\"street\":\"Yale street 1224\"," +
//                        "\"name\":\"[CDATA[Joe & T > e < s \\\" t \\\' er]]\"," +
//                        "\"ArrayOfNum\":\"1.3, 2.4, 3, 4, 5\"\n" +
//                        "}";
//        JSONPointer jsonPointer = new JSONPointer("/addresses/address");
//        compareReaderToJSONObject2(xmlStr, expectedStr, jsonPointer);
//    }



    private void compareReaderToJSONObject(String xmlStr, String expectedStr) {
        JSONObject expectedJsonObject = new JSONObject(expectedStr);
        Reader reader = new StringReader(xmlStr);
        JSONObject jsonObject = XML.toJSONObject(reader);
        System.out.println("1111");
        //Util.compareActualVsExpectedJsonObjects(jsonObject,expectedJsonObject);
    }
}
