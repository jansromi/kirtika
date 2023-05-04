# Kirtika
### Application for fetching and annotating books
#### Overview
Kirtika is a Java(FX)-based book library app that allows users to manage their book collection.
In addition to basic CRUD-functionalities, Kirtika has two main webscraping functionalities:
- The app uses the Finna API to fetch book information using ISBN numbers.
- Users can fetch all genres in the YKL-library classification system.

#### Dependiencies
- JSoup (for webscraping)
- org.Json (for handling json-objects)
- JYU's ali.jar (from http://users.jyu.fi/~vesal/kurssit/ohj2/ali/)
- JYU's fxgui.jar (https://kurssit.it.jyu.fi/TIEP111/ohj2/FXExamples/FXGui/doxygen/annotated.html)

Needed libraries are included in the libs-directory.

#### Installation
##### Easy way
- Create a directory for kirtika, and clone this repository in it.
- Open the diretory in a shell, and type
```
java -jar kirtika.jar
```
##### Do-It-Yourself (Windows)
- Clone this repository in an empty directory.
- Compile project using the following command in shell:
```
javac -cp "libs/*;src" src/fxKirtika/*.java src/kirtika/*.java src/utils/*.java src/webscraping/*.java
```
- Create a JAR file with the custom manifest provided, run:
```
jar cvfm kirtika.jar libs/MANIFEST.MF -C src .
```
- Finally run
```
java -jar kirtika.jar
```

