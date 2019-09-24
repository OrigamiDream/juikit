# JUIKit

Java builder style UI library

Simple!

[Here](https://github.com/OrigamiDream/juikit/blob/master/src/test/java/studio/avis/juikit/test) are samples.

### Gradle/Maven
##### Maven
```xml
<repositories>
    <repository>
        <id>ipdetection4j</id>
        <url>https://maven.pkg.github.com/OrigamiDream</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>studio.avis</groupId>
        <artifactId>juikit</artifactId>
        <version>2.0</version>
    </dependency>
</dependencies>
```
##### Gradle
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://maven.pkg.github.com/OrigamiDream' }
    }
}

dependencies {
    implementation 'studio.avis:juikit:v2.10.0'
}
```


### Compile
for OS X/Linux:
```
./mvnw clean package
```

for Windows:
```
mvnw.cmd clean package
```
