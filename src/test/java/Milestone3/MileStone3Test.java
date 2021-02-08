package Milestone3;

import MileStone2.json.JSONObject;
import MileStone2.json.XML;
import Milestone2.Util;
import org.junit.Test;

import java.io.*;

public class MileStone3Test {
    public static int FACTOR = 4;

    @Test
    public void prefixFuncTest() throws IOException {
        class TestFunc implements XML.XMLFunction{
            public String run(String tagname) {
                String add = "SWE262_";
                return add+tagname;
            }
        };

        String xmlStr =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                        "<addresses xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""+
                        "   xsi:noNamespaceSchemaLocation='test.xsd'>\n"+
                        "   <address>\n"+
                        "       <name>Joe Tester</name>\n"+
                        "       <street>[CDATA[Baker street 5]</street>\n"+
                        "       <NothingHere></NothingHere>\n"+
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

        String expStr ="{\"SWE262_addresses\": {\n" +
                "    \"xsi:noNamespaceSchemaLocation\": \"test.xsd\",\n" +
                "    \"xmlns:xsi\": \"http://www.w3.org/2001/XMLSchema-instance\",\n" +
                "    \"SWE262_address\": {\n" +
                "        \"SWE262_FalseValue\": false,\n" +
                "        \"SWE262_NullValue\": null,\n" +
                "        \"SWE262_street\": \"[CDATA[Baker street 5]\",\n" +
                "        \"SWE262_name\": \"Joe Tester\",\n" +
                "        \"SWE262_Nan\": \"-23x.45\",\n" +
                "        \"SWE262_PositiveValue\": 42,\n" +
                "        \"SWE262_TrueValue\": true,\n" +
                "        \"SWE262_NothingHere\": \"\",\n" +
                "        \"SWE262_DoubleValue\": -23.45,\n" +
                "        \"SWE262_ArrayOfNum\": \"1, 2, 3, 4.1, 5.2\",\n" +
                "        \"SWE262_NegativeValue\": -23\n" +
                "    }\n" +
                "}}";
        TestFunc func = new TestFunc();

        compareReaderToJSONObject(xmlStr,expStr,func);
    }

    @Test
    public void reverseFuncTest() throws IOException {
        class TestFunc implements XML.XMLFunction{
            public String run(String tagname) {
                StringBuilder sb = new StringBuilder(tagname);
                String res = sb.reverse().toString();;
                return res;
            }
        };

        String xmlStr =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                        "<addresses xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""+
                        "   xsi:noNamespaceSchemaLocation='test.xsd'>\n"+
                        "   <address>\n"+
                        "       <name>Joe Tester</name>\n"+
                        "       <street>[CDATA[Baker street 5]</street>\n"+
                        "       <NothingHere></NothingHere>\n"+
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

        String expStr ="{\"sesserdda\": {\n" +
                "    \"xsi:noNamespaceSchemaLocation\": \"test.xsd\",\n" +
                "    \"xmlns:xsi\": \"http://www.w3.org/2001/XMLSchema-instance\",\n" +
                "    \"sserdda\": {\n" +
                "        \"muNfOyarrA\": \"1, 2, 3, 4.1, 5.2\",\n" +
                "        \"eulaVeslaF\": false,\n" +
                "        \"eulaVlluN\": null,\n" +
                "        \"eulaVeurT\": true,\n" +
                "        \"eman\": \"Joe Tester\",\n" +
                "        \"naN\": \"-23x.45\",\n" +
                "        \"teerts\": \"[CDATA[Baker street 5]\",\n" +
                "        \"eulaVevitisoP\": 42,\n" +
                "        \"eulaVevitageN\": -23,\n" +
                "        \"eulaVelbuoD\": -23.45,\n" +
                "        \"ereHgnihtoN\": \"\"\n" +
                "    }\n" +
                "}}";
        TestFunc func = new TestFunc();

        compareReaderToJSONObject(xmlStr,expStr,func);
    }

    private void compareReaderToJSONObject(String xmlStr, String expectedStr , XML.XMLFunction func) throws IOException {
        JSONObject expectedJsonObject = new JSONObject(expectedStr);
        Reader reader = new StringReader(xmlStr);
        JSONObject jsonObject = XML.toJSONObject(reader,func);
        System.out.println(jsonObject.toString(FACTOR));
        Util.compareActualVsExpectedJsonObjects(jsonObject,expectedJsonObject);
    }

}
