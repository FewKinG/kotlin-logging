HN Kotlin logging library
=============================

Full docs in Confluence here: :p :p :p

Setup access to S3 maven repo
-----------------------------
Add the S3 access credentials for your development environment (~/.m2/settings.xml for linux):
```
<settings>
  <servers>
    <server>
      <id>maven.paulalex.de</id>
      <username>IAM ID</username>
      <password>IAM SECRET</password>
    </server>
  </servers>
</settings>
```

Use hn-kotlin-logging in Java/Kotlin service
-----------------------------
1. Make sure you have set up access to the S3 repo as described above.

2. Add internal S3 maven repository to project's repositories (pom.xml):
```
<repositories>
  <repository>
    <id>maven.paulalex.de</id>
    <url>s3://maven.paulalex.de/release</url>
  </repository>
</repositories>
```

3. Add hn-kotlin-logging as dependency to pom.xml:
```
<dependency>
  <groupId>app.haspa.alex</groupId>
  <artifactId>hn-kotlin-logging</artifactId>
  <version>1.0.0</version>
</dependency>
```

It is available in your project now.

Usage
------
```
```

Contribute and deploy to Maven
-----------------------------
1. Add your code and push your changes to the repo
1. For Java9 & Java10 compatibility: `export MAVEN_OPTS="--add-modules java.xml.bind"`
2. Increase the version number by `mvn release:prepare`
3. Make sure you have set up access to the S3 repo as described above.
4. Run `mvn release:perform` to push the changes to the S3 maven repo


History
-------

