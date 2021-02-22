

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

Or you can try the unit Test in MileStone1Test.java

### MileStone2

- Fork the [JSON org project in Github (Links to an external site.)](https://github.com/stleary/JSON-java) and make it "your own."

- Add an overloaded static method to the XML class with the signature (marked with // Author: Lai Wang Yu Sun)

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



### MileStone3

- Add an overloaded static method to the XML class with the signature

  ```
  static JSONObject toJSONObject(Reader reader, YOURTYPEHERE keyTransformer) 
  ```

  which does, inside the library, the kinds of things you did in task 4 of milestone 1, but in a much more general manner, for any transformations of keys. Specifically, YOURTYPEHERE should be a function (or "functional" in Java) that takes as input a String denoting a key and returns another String that is the transformation of the key. For example:

  ```
  "foo" --> "swe262_foo" 
  "foo" --> "oof"
  ```

  Remember, these are functions provided by the client code, so they can be quite powerful and include all sorts of string matching and transformation logic.

  The goal here is that you do the transformation during the parsing of the XML file, not in another pass afterwards. 

- In a README file, comment on the performance implications of doing this inside the library vs. doing it in client code, as you did in Milestone 1. 

- Write unit tests for your new function.

#### **Our implementation:** 

```java
    public interface YOURTYPEHERE{
        public String run(String tagname);
    }
   
    public static JSONObject toJSONObject(Reader reader, YOURTYPEHERE keyTransformer) throws IOException{
        JSONObject object=toJSONObject(reader, XMLParserConfiguration.ORIGINAL, keyTransformer);
        Milestone2.writeToDisk(object.toString());
        return object;
    }
```

Interface for tag-name functions.

```java
private static boolean parse3(XMLTokener x, JSONObject context, String name, XMLParserConfiguration config ,YOURTYPEHERE tagfunc) throws JSONException {
		//..............
						  // Nested element
                       		if (parse3(x, jsonObject, tagName, config,tagfunc)) {
                                if (jsonObject.length() == 0) {
                                    tagName = tagfunc.run(tagName);
                                    context.accumulate(tagName, "");
                                } else if (jsonObject.length() == 1
                                        && jsonObject.opt(config.getcDataTagName()) != null) {
                                    tagName = tagfunc.run(tagName);
                                    context.accumulate(tagName, jsonObject.opt(config.getcDataTagName()));
                                } else {
                                    tagName = tagfunc.run(tagName);
                                    context.accumulate(tagName, jsonObject);
                                }
                                return false;
                            }
       //...................
}
```

In milestone3 our group implement a special parse method (parse3). A function parameter will pass into this method, which indicates what change we will make on tag like reverse or add prefix. Most important thing is we need use tag function before we pass the tagName into accumulate method and we need call parse3 method in if judgment to make this change is apply to every line of jsonObject.

**Comments on performance implications**:

In milestone1, in order to meet this requirement, we need to produce the jsonObject through xml file and then we can process with DFS to go through the whole jsonObject and process the tag-name. 

In milestone3, through parse method in XML, we can process the tag-name when we are building the JsonObject, which ensure the performance.

#### **Unit Test**: 

```java
public class MileStone3Test {
    @Test
    public void prefixFuncTest() throws IOException {
      	class TestFunc implements XML.YOURTYPEHERE{
            public String run(String tagname) {
                String add = "SWE262_";
                return add+tagname;
            }
        };
        
        String xmlStr ="...";
        String expStr ="...";
        TestFunc func = new TestFunc();
        compareReaderToJSONObject(xmlStr,expStr,func);
    }
    
    private void compareReaderToJSONObject(String xmlStr, String expectedStr , XML.YOURTYPEHERE func) throws IOException {
        JSONObject expectedJsonObject = new JSONObject(expectedStr);
        Reader reader = new StringReader(xmlStr);
        JSONObject jsonObject = XML.toJSONObject(reader,func);
        System.out.println(jsonObject.toString(FACTOR));
        Util.compareActualVsExpectedJsonObjects(jsonObject,expectedJsonObject);
    }
}
```

If you want to implement a new tag-name function, you could overwrite the run methods declared in YOURTYPEHERE interface.



### MileStone4

- Add streaming methods to the library that allow the client code to chain operations **on JSON nodes**. For example:

  ```
  // in client space
  JSONObject obj = XML.toJSONObject("<Books><book><title>AAA</title><author>ASmith</author></book><book><title>BBB</title><author>BSmith</author></book></Books>");
  obj.toStream().forEach(node -> do some transformation, possibly based on the path of the node);
  List<String> titles = obj.toStream().map(node -> extract value for key "title").collect(Collectors.toList());
  obj.toStream().filter(node -> node with certain properties).forEach(node -> do some transformation);
  ```

  These stream operations apply to JSONObject, and are started by transforming those objects into streams with the new toStream() method, that you should write. Given that XML and JSON are hierarchical structures, you need to think about the type of stream you want to support. E.g. top-level elements only, every element independent of nesting, etc. There can be many options. The critical thing here is that, unless the client code explicitly collects the data into an object, the data should simply flow in small parts to the next operation.

- Write unit tests for your new function(s)

#### **Our implementation:** 

For the Stream type, we choose JSONObject of every leaf node. 

We implement a toStream method in JSONObject.java to meet this requirement.

It will return a Stream<JSONObject> obj as we call toStream() method. 

```java
    public Stream<JSONObject> toStream(){
        List<JSONObject> list = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        analysisJson2(this,list,sb);
        JSONObject[] objArr = list.toArray(new JSONObject[list.size()]);
        Stream<JSONObject> stream = Stream.of(objArr);
        return stream;
    }
```

We used dfs to search every leaf node and for each leaf we get from JSONObject, we will record its path which can indicate father node it belongs to.

```java
    public void  analysisJson(Object objJson,List<JSONObject> list,StringBuilder sb){
        //if obj is jsonarray
        Set<String> deleteSet = new HashSet();
        if(objJson instanceof JSONArray){
            JSONArray objArray = (JSONArray)objJson;
            for (int i = 0; i < objArray.length(); i++) {
                sb.append("/"+i);
                analysisJson(objArray.get(i),list,sb);
                sb.delete(sb.length()-2,sb.length());
            }
        }
        //if obj is jsonobject
        else if(objJson instanceof JSONObject){
            JSONObject jsonObject = (JSONObject)objJson;
            Iterator it = jsonObject.keys();
            while(it.hasNext()){
                String key = it.next().toString();
                Object object = jsonObject.get(key);

                //if we get array
                if(object instanceof JSONArray){
                    //list.add(object);
                    JSONArray objArray = (JSONArray)object;
                    sb.append("/"+key);
                    analysisJson(objArray,list,sb);
                    sb.delete(sb.length()-1-key.length(),sb.length());
                }
                //if we get the jsonobjet
                else if(object instanceof JSONObject){
                    //list.add((JSONObject)object);
                    sb.append("/"+key);
                    analysisJson((JSONObject)object,list,sb);
                    sb.delete(sb.length()-1-key.length(),sb.length());
                }
                //String elements
                else{
                    deleteSet.add(key);
                }
            }
            for (String key : deleteSet){
                sb.append("/"+key);
                JSONObject jsonObj = new JSONObject();
                Object object = jsonObject.get(key);
                jsonObj.put(key,object);
                jsonObj.put("path",sb.toString());
                list.add(jsonObj);
                sb.delete(sb.length()-1-key.length(),sb.length());
            }
        }
    }
```

#### **Unit Test**: 

Stream function we test includes foreach, map , filter:

```java
JSONObject obj = XML.toJSONObject(xml);
obj.toStream().forEach(node -> {if(node.has("description"))System.out.println(node.toString());});
obj.toStream().filter(node -> node.has("author")).forEach(node -> System.out.println(node.toString()));
List<String> titles = obj.toStream().filter(node -> node.has("title")).map(node ->(String) node.get("title")
        ).collect(Collectors.toList());
obj.toStream().filter(node -> ((String)node.get("path")).contains("/catalog/book/0/")).forEach(node -> System.out.println(node.toString()));
```

