package testcase;

import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

public class Case {

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
                "{\"addresses\":{\"address\":{\"street\":\"[CDATA[Baker street 5]\","+
                        "\"name\":\"Joe Tester\",\"NothingHere\":\"\",TrueValue:true,\n"+
                        "\"FalseValue\":false,\"NullValue\":null,\"PositiveValue\":42,\n"+
                        "\"NegativeValue\":-23,\"DoubleValue\":-23.45,\"Nan\":-23x.45,\n"+
                        "\"ArrayOfNum\":\"1, 2, 3, 4.1, 5.2\"\n"+
                        "},\"xsi:noNamespaceSchemaLocation\":"+
                        "\"test.xsd\",\"xmlns:xsi\":\"http://www.w3.org/2001/"+
                        "XMLSchema-instance\"}}";

        System.out.println("111");
        //compareStringToJSONObject(xmlStr, expectedStr);
        //compareReaderToJSONObject(xmlStr, expectedStr);
        //compareFileToJSONObject(xmlStr, expectedStr);
    }

//    private void compareReaderToJSONObject(String xmlStr, String expectedStr) {
//        JSONObject expectedJsonObject = new JSONObject(expectedStr);
//        Reader reader = new StringReader(xmlStr);
//        System.out.println("111");
//        JSONObject jsonObject = XML.toJSONObject(reader);
//        Util.compareActualVsExpectedJsonObjects(jsonObject,expectedJsonObject);
//    }


}
