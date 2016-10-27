# CoinChange
This is a java service and stand alone API for retrieving the minimum coins as change when
passed in an amount.

##Build the project with Maven
To build the project execute the following maven command from the project root directory
```mvn clean install```

##Self contained service
This will provide an executable jar file in the target folder called ```CoinChange-1.0.0-SNAPSHOT-Spring-Boot.jar```
which you can run from the command line using ```java -jar CoinChange-1.0.0-SNAPSHOT-Spring-Boot.jar```

This service relies on a properties file named ```coin-inventory.properties``` in the format:
```
100=2
50=3
20=3
10=4
5=4
2=0
1=0
```

This provides the service with the available to change to work from.  Currently this file needs to be placed
in the root directory of the project.

When run the command line will prompt you to enter coin amounts which will return and print out
the minimum coins required.  This runs as a stand alone service.

##Using API jar file in your project
The maven build also provides an API jar called ```CoinChange-1.0.0-SNAPSHOT.jar```.  This jar can be added to your classpath
or to your IDE library from where you can call its API methods.

```
Collection<com.example.model.Coin> 	getChangeFor(int amount)
Calculates the minimum number of Coin objects required to make up the supplied amount when coins are available in properties store.
Collection<com.example.model.Coin> 	getOptimalChangeFor(int amount)
Calculates the minimum number of Coin objects required to make up the supplied amount.
```
To generate javadocs run ```mvn javadoc:javadoc```.   API Documentation can be viewed here:
```CoinChange/target/site/apidocs/index.html```

