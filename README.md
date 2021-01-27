# SWE_262

Created by Lai Wang and Yu Sun 
Output file is \SWE262\src\main\resources
Small XML file is \SWE262\src\main\resources

For demo command line, you need to install maven in your computer and active mvn command in your computer System 
Refer:http://maven.apache.org/ 

Example Demo Command Line :
        Q1: mvn exec:java -Dexec.mainClass="MileStone1.Milestone1" -Dexec.args="1 src/main/resources/xml1.xml"
        Q2: mvn exec:java -Dexec.mainClass="MileStone1.Milestone1" -Dexec.args="2 src/main/resources/xml1.xml /catalog/book/0/author"
        Q3: mvn exec:java -Dexec.mainClass="MileStone1.Milestone1" -Dexec.args="3 src/main/resources/xml1.xml /catalog/book/0"
        Q4: mvn exec:java -Dexec.mainClass="MileStone1.Milestone1" -Dexec.args="4 src/main/resources/xml1.xml"
        Q5: mvn exec:java -Dexec.mainClass="MileStone1.Milestone1" -Dexec.args="5 src/main/resources/xml1.xml /catalog/book/0/author"
Cannot process the large file(OutOfMemory:Java Heap Space)		

### MileStone2

- Fork the [JSON org project in Github (Links to an external site.)](https://github.com/stleary/JSON-java) and make it "your own."

- Add an overloaded static method to the XML class with the signature

  ```java
  public static JSONObject toJSONObject(Reader reader, JSONPointer path) throws IOException {
  
          JSONObject jsonObject=toJSONObject2(reader, XMLParserConfiguration.ORIGINAL);
          Object object=path.queryFrom(jsonObject);
          Milestone2.writeToDisk(object.toString());
          return (JSONObject) object;
  
   }
  ```

  which does, inside the library, the same thing that task 2 of milestone 1 did in client code, before writing to disk. Being this done inside the library, you should be able to do it more efficiently. Specifically, you shouldn't need to read the entire XML file, as you can stop parsing it as soon as you find the object in question.

  

  ### In order to improve our program's Performance: we implement the method  parase2 and toJSONObject2

  ```java
  private static boolean parse2(XMLTokener x, JSONObject context,JSONPointer pointer, String name, XMLParserConfiguration config) throws JSONException {
                          .....
                          } else if (token == LT) {
                              // Nested element
                              if (parse(x, jsonObject, tagName, config)) {
                                  if (jsonObject.length() == 0) {
                                      context.accumulate(tagName, "");
                                  } else if (jsonObject.length() == 1
                                          && jsonObject.opt(config.getcDataTagName()) != null) {
                                      context.accumulate(tagName, jsonObject.opt(config.getcDataTagName()));
                                  } else {
                                      context.accumulate(tagName, jsonObject);
                                      if(context!=null&&pointer.queryFrom(context)!=null){
                                          System.out.println("Found it!!!");
                                          throw x.syntaxError("We found the targe and will stop parsing this xml file!!!");
                                      }
                                  }
                                  return false;
                              ......
    }
  ```

  In parse2 method we will judge whether the context (JSONobject) contains the JSONPointer path. if it contains the path, it will throw a Exception( sorry ,we can't find a elegant way to meet this requirement) , while outside logic will catch the Exception and stop reading more xml file which could make parse process more  efficient. 

  ```java
  public static JSONObject toJSONObject2(Reader reader,JSONPointer jsonPointer, XMLParserConfiguration config) throws JSONException {
          JSONObject jo = new JSONObject();
          XMLTokener x = new XMLTokener(reader);
          while (x.more()) {
              x.skipPast("<");
              if(x.more()) {
                  try {
                      parse2(x, jo,jsonPointer, null, config);
                  }catch (Exception exception){
                      System.out.println("We found the targe and will stop parsing this xml file!!!");
                      break;
                  }
              }
          }
          return jo;
      }
  ```

  This method will catch the exception throw from parse2 method when data parse2 method process is enough for the JSONPointer. When it catch the exception, it will quit the while loop (which means reading we will stop parsing it as soon as we find the object in question). 

  

- Add an overloaded static method to the XML class with the signature

  ```java
  public static JSONObject toJSONObject(Reader reader, JSONPointer path,JSONObject replacement) throws IOException {
          JSONObject jsonObj=toJSONObject(reader, XMLParserConfiguration.ORIGINAL);
          JSONObject replaceObj = replacement;
          String strPath = path.toString();
          String[] nodes = strPath.split("/");
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
          try{
              Milestone2.writeToDisk(jsonObj.toString(4));
  //            System.out.println(query(strPath, jsonObj).toString());
          }catch (Exception ex){
          }
          return jsonObj;
      }
  ```

  which does, inside the library, the same thing that task 5 of milestone 1 did in client code, before writing to disk. Are there any possible performance gains from doing this inside the library? If so, implement them in your version of the library.

  We do believe for replacement method, we need to read all the xml file to get the whole  JSONObject.

  

- You can try these methods by following command:

  ```
  mvn exec:java -Dexec.mainClass="MileStone2.MileStone.Milestone2" -Dexec.args="1 src/main/resources/xml1.xml"
  ```

  ```
  mvn exec:java -Dexec.mainClass="MileStone2.MileStone.Milestone2" -Dexec.args="2 src/main/resources/xml1.xml"
  ```

- Write unit tests in "MileStone2Test.java"

- Unit Tests for method:

  ```java
  public static JSONObject toJSONObject(Reader reader, JSONPointer path)
  ```

  ```java
  public void shouldHandleSimpleXML()
  private void compareReaderToJSONObject1(String xmlStr, String expectedStr, JSONPointer jsonPointer)
  ```

- Unit Tests for method:

  ```java
  public static JSONObject toJSONObject(Reader reader, JSONPointer path,JSONObject replacement)
  ```

  ```java
  public void shouldHandleSimpleXML2()
  private void compareReaderToJSONObject2(String xmlStr, String replaceStr, String expectedStr , JSONPointer jsonPointer)
  ```