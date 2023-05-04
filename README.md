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

#### Installation
##### Easy way
- Create a directory for kirtika, and clone this repository in it.
- Open the diretory in a shell, and type
```
java -jar kirtika.jar
```
