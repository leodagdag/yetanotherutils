# Yet Another Utils

Utility library for Java 8

## Build
Clone the project
```
git clone https://github.com/leodagdag/yetanotherutils.git
``` 

And build it with maven

```bash
mvn clean install
```

## Usage
Add it as dependency in your project


### Maven

#### Repository
```xml
<repositories>
    <repository>
        <id>yetanotherutils-mvn-repo</id>
        <url>https://raw.github.com/leodagdag/yetanotherutils/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```
#### Dependency
```xml
<dependency>
    <groupId>com.yet.another</groupId>
    <artifactId>utils</artifactId>
    <version>version</version>
</dependency>
``` 
