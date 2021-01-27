package MileStone2.MileStone;


import MileStone2.json.*;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;



import java.io.*;
import java.util.*;

public class Milestone2 {
    public static int FACTOR = 4;

    public static void main(String[] args) throws IOException {
        //mvn exec:java -Dexec.mainClass="MileStone2.MileStone.Milestone2" -Dexec.args="1 src/main/resources/xml1.xml"
        //mvn exec:java -Dexec.mainClass="MileStone2.MileStone.Milestone2" -Dexec.args="2 src/main/resources/xml1.xml"
        //demo(args);
        test();
    }

    public static void test() throws IOException{
        Reader reader = new FileReader(new File("src/main/resources/xml1.xml"));
        String path = "/catalog/book/0";
        JSONPointer jsonPointer =new JSONPointer(path);
        String jsonStr = "{\"id\":1,\"age\":2,\"name\":\"zhang\"}";
        JSONObject replaceObj = new JSONObject(jsonStr);
        System.out.println(jsonPointer.toString());
        //JSONObject object = XML.toJSONObject(reader,jsonPointer,replaceObj);
        JSONObject object2 = XML.toJSONObject(reader,jsonPointer);
        System.out.println(object2.toString(FACTOR));
    }

    public static void demo(String[] args) throws IOException{
        if(args.length==0){
            System.out.println("No file Path enter");
            return;
        }
        String cmd = readArg(args).get(0);
        String filePath= readArg(args).get(1);

        Reader reader = new FileReader(new File(filePath));
        String path = "/catalog/book/0";
        JSONPointer jsonPointer =new JSONPointer(path);
        String jsonStr = "{\"id\":1,\"age\":2,\"name\":\"zhang\"}";
        JSONObject replaceObj = new JSONObject(jsonStr);
        JSONObject object;
        switch(cmd)
        {
            case "1" :
                System.out.println("Demo Question 1");
                object = XML.toJSONObject(reader,jsonPointer);
                System.out.println(object.toString(FACTOR));
                break;
            case "2" :
                System.out.println("Demo Question 2");
                object = XML.toJSONObject(reader,jsonPointer,replaceObj);
                System.out.println(object.toString(FACTOR));
                break;
            default :
                System.out.println("Default Demo");
                break;
        }
    }


    public static void  writeToDisk (String jsonObject) throws IOException {
        File file=new File("src/main/resources/output.json");
        BufferedWriter bw=new BufferedWriter(new FileWriter(file));
        bw.write(jsonObject);
        bw.close();

    }


    public static List<String> readArg(String[] args){
        List<String > res=new ArrayList<>();
        for(String str:args){
            res.add(str);
        }
        return res;
    }


    }
