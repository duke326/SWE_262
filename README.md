# SWE_262
Created by Lai Wang and Yu Sun </br>
Output file is \SWE262\src\main\resources</br>
Small XML file is \SWE262\src\main\resources</br>
Example Demo Command Line :</br>
        Q1: mvn exec:java -Dexec.mainClass="Milestone1" -Dexec.args="1 src/main/resources/xml1.xml"</br>
        Q2: mvn exec:java -Dexec.mainClass="Milestone1" -Dexec.args="2 src/main/resources/xml1.xml /catalog/book/0/author"</br>
        Q3: mvn exec:java -Dexec.mainClass="Milestone1" -Dexec.args="3 src/main/resources/xml1.xml /catalog/book/0"</br>
        Q4: mvn exec:java -Dexec.mainClass="Milestone1" -Dexec.args="4 src/main/resources/xml1.xml"</br>
        Q5: mvn exec:java -Dexec.mainClass="Milestone1" -Dexec.args="5 src/main/resources/xml1.xml /catalog/book/0/author"</br>
Cannot process the large file(OutOfMemory:Java Heap Space)
