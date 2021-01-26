package Milestone2;

import MileStone2.json.JSONObject;
import MileStone2.json.JSONPointer;
import MileStone2.json.XML;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

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
}
