package Milestone1;

import org.json.JSONObject;
import org.junit.Test;
//import MileStone1.Milestone1;

import java.io.IOException;

import static MileStone1.Milestone1.*;

public class MileStone1Test {


    @Test
    public void writeToDestTest(){
        String filePath = "src/main/resources/xml1.xml";
        JSONObject jsonObj = load2(filePath);
        try{
            System.out.println("Demo Question 1");
            writeToDisk(jsonObj.toString(FACTOR));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void queryTest(){
        String filePath = "src/main/resources/xml1.xml";
        String keyPath = "/catalog/book/0/author";
        JSONObject jsonObj = load2(filePath);
        try{
            System.out.println("Demo Question 2");
            query(keyPath, jsonObj).toString();
            System.out.println(query(keyPath, jsonObj).toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void checkTest(){
        String filePath = "src/main/resources/xml1.xml";
        String keyPath = "/catalog/book/0/author";
        JSONObject jsonObj = load2(filePath);
        try{
            System.out.println("Demo Question 3");
            System.out.println(checkPath(keyPath, jsonObj));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void question4Test(){
        String filePath = "src/main/resources/xml1.xml";
        try{
            System.out.println("Demo Question 4");
            question4(filePath);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void question5Test(){
        String filePath = "src/main/resources/xml1.xml";
        String keyPath = "/catalog/book/0/author";
        try{
            System.out.println("Demo Question 5");
            question5(filePath,keyPath);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
