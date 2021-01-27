package Milestone2;

import MileStone2.json.JSONObject;
import MileStone2.json.JSONPointer;
import MileStone2.json.XML;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;


import java.io.*;


public class MileStone2Test {
    public static int FACTOR = 4;

    @Test
    public void test() throws IOException {
        String filename = "src/main/resources/xml1.xml";
        Reader reader = new FileReader(new File(filename));
        String path = "/catalog/book/0";
        JSONPointer jsonPointer =new JSONPointer(path);

        System.out.println(jsonPointer.toString());
        JSONObject object = XML.toJSONObject(reader,jsonPointer);
        System.out.println(object.toString(FACTOR));
    }

    @Test
    public void test1() throws IOException {
        try{
            String filename = "src/main/resources/xml1.xml";
            Reader reader = new FileReader(new File(filename));
            String path = "/catalog/book/0/author";
            JSONPointer jsonPointer =new JSONPointer(path);

            System.out.println(jsonPointer.toString());
            JSONObject object = XML.toJSONObject(reader,jsonPointer);
            System.out.println(object.toString(FACTOR));
        }catch (Exception ex){
            String result = ex.getClass().getName();
            System.out.println(result);
            Assertions.assertEquals("java.lang.ClassCastException",result);
        }

    }

    @Test
    public void test2() throws IOException {
        String filename = "src/main/resources/xml1.xml";
        Reader reader = new FileReader(new File(filename));
        String path = "/catalog/book/0";
        JSONPointer jsonPointer =new JSONPointer(path);
        String jsonStr = "{\"id\":1,\"age\":2,\"name\":\"zhang\"}";
        JSONObject replaceObj = new JSONObject(jsonStr);
        System.out.println(jsonPointer.toString());
        JSONObject object = XML.toJSONObject(reader,jsonPointer,replaceObj);
        System.out.println(object.toString(FACTOR));
    }

    /**
     * Valid XML to JSONObject
     */
    @Test
    public void shouldHandleSimpleXML() throws IOException {
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
                "{\"street\":\"[CDATA[Baker street 5]\","+
                        "\"name\":\"Joe Tester\",\"NothingHere\":\"\",TrueValue:true,\n"+
                        "\"FalseValue\":false,\"NullValue\":null,\"PositiveValue\":42,\n"+
                        "\"NegativeValue\":-23,\"DoubleValue\":-23.45,\"Nan\":-23x.45,\n"+
                        "\"ArrayOfNum\":\"1, 2, 3, 4.1, 5.2\"\n"+
                        "}";

        String path = "/addresses/address";
        JSONPointer jsonPointer =new JSONPointer(path);
        compareReaderToJSONObject1(xmlStr, expectedStr,jsonPointer);
    }

    @Test
    public void shouldHandleSimpleXML2() throws IOException {
        String xmlStr =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                        "<addresses xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""+
                        " xsi:noNamespaceSchemaLocation='test.xsd'>\n"+
                        " <address>\n"+
                        " <ArrayOfNum>1, 2, 3, 4.1, 5.2</ArrayOfNum>\n"+
                        " </address>\n"+
                        "</addresses>";
        String replaceStr = "{\"ArrayOfNum\":\"1, 2, 3, 4.1, 5.2\"}";
        String expectedStr =
                "{\"addresses\":{\"address\":{\"ArrayOfNum\":\"1, 2, 3, 4.1, 5.2\"\n"+
                        "},\"xsi:noNamespaceSchemaLocation\":"+
                        "\"test.xsd\",\"xmlns:xsi\":\"http://www.w3.org/2001/"+
                        "XMLSchema-instance\"}}";
        JSONPointer jsonPointer = new JSONPointer("/root/item");
        compareReaderToJSONObject2(xmlStr, replaceStr, expectedStr, jsonPointer);
    }


    private void compareReaderToJSONObject1(String xmlStr, String expectedStr, JSONPointer jsonPointer) throws IOException {
        JSONObject expectedJsonObject = new JSONObject(expectedStr);
        Reader reader = new StringReader(xmlStr);
        JSONObject jsonObject = XML.toJSONObject(reader,jsonPointer);
        Util.compareActualVsExpectedJsonObjects(jsonObject,expectedJsonObject);
        System.out.println(jsonObject.toString(FACTOR));

    }

    private void compareReaderToJSONObject2(String xmlStr, String replaceStr, String expectedStr , JSONPointer jsonPointer) throws IOException {
        JSONObject expectedJsonObject = new JSONObject(expectedStr);
        JSONObject replaceObj = new JSONObject(replaceStr);
        Reader reader = new StringReader(xmlStr);
        JSONObject jsonObject = XML.toJSONObject(reader,jsonPointer,replaceObj);
        System.out.println(jsonObject.toString(FACTOR));
        Util.compareActualVsExpectedJsonObjects(jsonObject,expectedJsonObject);
    }




}
