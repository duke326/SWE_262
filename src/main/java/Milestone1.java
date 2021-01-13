import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.json.*;

import java.io.*;
import java.util.*;

public class Milestone1 {
    public static void main(String[] args) throws IOException {
        //mvn exec:java -Dexec.mainClass="Milestone1" -Dexec.args="1 src/main/resources/xml1.xml"
        //mvn exec:java -Dexec.mainClass="Milestone1" -Dexec.args="2 src/main/resources/xml1.xml /catalog/book/0/author"
        //mvn exec:java -Dexec.mainClass="Milestone1" -Dexec.args="3 src/main/resources/xml1.xml /catalog/book/0"
        //mvn exec:java -Dexec.mainClass="Milestone1" -Dexec.args="4 src/main/resources/xml1.xml"
        //mvn exec:java -Dexec.mainClass="Milestone1" -Dexec.args="5 src/main/resources/xml1.xml /catalog/book/0/author"
        demo(args);
    }

    public static void demo(String[] args) throws IOException{
        if(args.length==0){
            System.out.println("No file Path enter");
            return;
        }
        String cmd = readArg(args).get(0);
        String filePath= readArg(args).get(1);
        String keyPath = null;
        if (args.length==3){
            keyPath=readArg(args).get(2);
        }
        JSONObject jsonObj = load2(filePath);

        switch(cmd)
        {
            case "1" :
                System.out.println("Demo Question 1");
                writeToDisk(jsonObj.toString());
                break;
            case "2" :
                System.out.println("Demo Question 2");
                query(keyPath, jsonObj).toString();
                System.out.println(query(keyPath, jsonObj).toString());
                break;
            case "3" :
                System.out.println("Demo Question 3");
                System.out.println(checkPath(keyPath, jsonObj));
                break;
            case "4" :
                System.out.println("Demo Question 4");
                question4(filePath);
                break;
            case "5" :
                System.out.println("Demo Question 5");
                question5(filePath,keyPath);
                break;
            default :
                System.out.println("Default Demo");
                break;
        }
    }

    ///Load XML file, transfer XML to String
    public static JSONObject load(String filename) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(filename)); // 读取XML文件,获得document对象
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JSONObject jsonObject = XML.toJSONObject(document.asXML());
        return jsonObject;
    }

    // load without using dom4j
    public static JSONObject load2(String filename){
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String curLine;
            StringBuilder sb = new StringBuilder();
            while((curLine=bufferedReader.readLine())!=null){
                sb.append(curLine);
                sb.append("\n");
            }
            String text = sb.toString();
            //System.out.println(text);
            JSONObject jsonObject = XML.toJSONObject(text);
            return jsonObject;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //Question 1 ///
    public static void  writeToDisk (String jsonObject) throws IOException {
        File file=new File("src/main/resources/output.json");
        BufferedWriter bw=new BufferedWriter(new FileWriter(file));
        bw.write(jsonObject.toString());
        bw.close();

    }
    ////////////question2///////////////
    public static Object query (String path, Object document) throws IOException {
        JSONPointer jsonPointer =new JSONPointer(path);
        Object object=jsonPointer.queryFrom(document);
        writeToDisk(object.toString());
        return object;
    }


    ////Question3 ////
    public static boolean checkPath (String path, Object document) throws IOException {
        JSONPointer jsonPointer =new JSONPointer(path);
        Object object=jsonPointer.queryFrom(document);
        if(object!=null) writeToDisk(object.toString());
        return jsonPointer.queryFrom(document)!=null;
    }

    // Question4
    public static void question4(String filename){
        try {
            JSONObject jsonObj = load(filename);
            analysisJson(jsonObj);
            if(jsonObj!=null) writeToDisk(jsonObj.toString());
            System.out.println(jsonObj);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    // Question 5
    public static void question5(String filename, String path) throws IOException {
        JSONObject jsonObj = load2(filename);
        String[] nodes = path.split("/");
        //get replace obj
        String jsonStr = "{\"id\":1,\"age\":2,\"name\":\"zhang\"}";
        JSONObject replaceObj = new JSONObject(jsonStr);

        String key = nodes[nodes.length-1];
        System.out.println(key);
        StringBuilder rootPath = new StringBuilder();
        for(int i = 1; i<nodes.length-1;i++ ){
            rootPath.append("/"+nodes[i]);
        }
        System.out.println(rootPath);
        JSONPointer jsonPointer =new JSONPointer(rootPath.toString());
        Object object = jsonPointer.queryFrom(jsonObj);
        if(object instanceof JSONArray){
            JSONArray objArray = (JSONArray)object;
            int index = Integer.parseInt(key);
            objArray.put(index,replaceObj);
        }else if(object instanceof JSONObject){
            JSONObject jsonObject = (JSONObject)object;
            jsonObject.put(key,replaceObj);
        }
        System.out.println(query(path, jsonObj).toString());
    }



    public static void  analysisJson(Object objJson){
        //如果obj为json数组
        Set<String> deleteSet = new HashSet();
        if(objJson instanceof JSONArray){
            JSONArray objArray = (JSONArray)objJson;
            for (int i = 0; i < objArray.length(); i++) {
                analysisJson(objArray.get(i));
            }
        }
        //如果为json对象
        else if(objJson instanceof JSONObject){
            JSONObject jsonObject = (JSONObject)objJson;
            Iterator it = jsonObject.keys();
            while(it.hasNext()){
                String key = it.next().toString();
                Object object = jsonObject.get(key);

                //如果得到的是数组
                if(object instanceof JSONArray){
                    jsonObject.put("swe262_"+key,object);
                    jsonObject.remove(key);
                    JSONArray objArray = (JSONArray)object;
                    analysisJson(objArray);
                }
                //如果key中是一个json对象
                else if(object instanceof JSONObject){
                    jsonObject.put("swe262_"+key,object);
                    jsonObject.remove(key);
                    analysisJson((JSONObject)object);
                }
                //如果key中是其他
                else{
                    deleteSet.add(key);
                }
            }
            for (String key : deleteSet){
                Object object = jsonObject.get(key);
                jsonObject.put("swe262_"+key,object);
                jsonObject.remove(key);
            }
        }
    }
    public static List<String> readArg(String[] args){
        List<String > res=new ArrayList<>();
        for(String str:args){
            res.add(str);
        }
        return res;
    }


    }
