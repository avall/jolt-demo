# joltdemo
JOLT demo source code inspired by https://jolt-demo.appspot.com.

You can try this application here http://dkf.blazes.ru:8080/joltdemo.

# building

## AppEngine
Run server locally:
```
mvn spring-boot:run -P gcp
```
Deploy to Google AppEngine
```
gcloud app deploy
```

## Tomcat WAR
```
mvn install -P tomcat
```
copy target/joltdemo.war to webapps directory
