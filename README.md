# Autogenerated Resource Verifier for Java

[![Maven Central](https://img.shields.io/maven-central/v/com.exasol/autogenerated-resource-verifier-java)](https://search.maven.org/artifact/com.exasol/autogenerated-resource-verifier-java)

This Java library allows you to define autogenerated resources in Java.

## Usage

Define the content of your document in your test source code:

```java
public class MyReportGenerator {

    public static void main(final String[] args) {
        new AutogeneratedResourceVerifier().verifyResource(Path.of(args[0]), new CapabilitiesReport());
    }

    private static class MyReport implements AutogeneratedResource {
        @Override
        public String generateContent() {
            StringBuilder report;

            //build your report here

            return report.toString();
        }

        @Override
        public Path getPathOfGeneratedFile() {
            return Path.of("doc", "generated", "myReport.md");
        }
    }
}
```

### Run From Maven:

You can add the execution of your generator to the maven build using the `maven-execution-plugin`. To do so add the following lines to your `pom.xml`:

```xml

<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>3.0.0</version>
    <executions>
        <execution>
            <id>validate-capabilities-report</id>
            <goals>
                <goal>java</goal>
            </goals>
            <phase>package</phase>
            <configuration>
                <mainClass>com.example.MyReportGenerator</mainClass> <!-- Add your class here -->
                <classpathScope>test</classpathScope>
                <systemProperties>
                    <systemProperty>
                        <key>projectDir</key>
                        <value>${project.basedir}</value>
                    </systemProperty>
                </systemProperties>
            </configuration>
        </execution>
    </executions>
</plugin>
```

Now you can run the validation using `mvn package`.

The verifier can also fix (override) the generated resource. To do so, run:

```shell
mvn package -DfixAutogeneratedResources=true
```

### Run Time Dependencies

Running the Virtual Schema requires a Java Runtime version 11 or later.

| Dependency                                                         | Purpose                                                | License                       |
|--------------------------------------------------------------------|--------------------------------------------------------|-------------------------------|
| [Exasol Error Reporting][error-reporting-java]                     | Common JDBC functions for Virtual Schemas adapters     | MIT License                   |

### Test Dependencies

| Dependency                                                         | Purpose                                                | License                       |
|--------------------------------------------------------------------|--------------------------------------------------------|-------------------------------|
| [Apache Maven](https://maven.apache.org/)                          | Build tool                                             | Apache License 2.0            | 
| [Java Hamcrest](http://hamcrest.org/JavaHamcrest/)                 | Checking for conditions in code via matchers           | BSD License                   |
| [JUnit](https://junit.org/junit5)                                  | Unit testing framework                                 | Eclipse Public License 1.0    |
| [Mockito](http://site.mockito.org/)                                | Mocking framework                                      | MIT License                   |

### Maven Plug-ins

| Plug-in                                                            | Purpose                                                | License                       |
|--------------------------------------------------------------------|--------------------------------------------------------|-------------------------------|
| [Maven Surefire Plugin][maven-surefire-plugin]                     | Unit testing                                           | Apache License 2.0            |
| [Maven Jacoco Plugin][maven-jacoco-plugin]                         | Code coverage metering                                 | Eclipse Public License 2.0    |
| [Maven Compiler Plugin][maven-compiler-plugin]                     | Setting required Java version                          | Apache License 2.0            |
| [Maven Failsafe Plugin][maven-failsafe-plugin]                     | Integration testing                                    | Apache License 2.0            |
| [Sonatype OSS Index Maven Plugin][sonatype-oss-index-maven-plugin] | Checking Dependencies Vulnerability                    | ASL2                          |
| [Versions Maven Plugin][versions-maven-plugin]                     | Checking if dependencies updates are available         | Apache License 2.0            |
| [Maven Enforcer Plugin][maven-enforcer-plugin]                     | Controlling environment constants                      | Apache License 2.0            | |
| [Project Keeper Maven Plugin][project-keeper-maven-plugin]         | Checking project structure                             | MIT License                   |

[maven-surefire-plugin]: https://maven.apache.org/surefire/maven-surefire-plugin/

[maven-jacoco-plugin]: https://www.eclemma.org/jacoco/trunk/doc/maven.html

[maven-compiler-plugin]: https://maven.apache.org/plugins/maven-compiler-plugin/

[maven-assembly-plugin]: https://maven.apache.org/plugins/maven-assembly-plugin/

[maven-failsafe-plugin]: https://maven.apache.org/surefire/maven-failsafe-plugin/

[sonatype-oss-index-maven-plugin]: https://sonatype.github.io/ossindex-maven/maven-plugin/

[versions-maven-plugin]: https://www.mojohaus.org/versions-maven-plugin/

[maven-enforcer-plugin]: http://maven.apache.org/enforcer/maven-enforcer-plugin/

[artifact-ref-checker-plugin]: https://github.com/exasol/artifact-reference-checker-maven-plugin

[project-keeper-maven-plugin]: https://github.com/exasol/project-keeper-maven-plugin

[postgresql-jdbc-driver]: https://jdbc.postgresql.org/

[test-db-builder]: https://github.com/exasol/test-db-builder/

[virtual-schema-common-jdbc]: https://github.com/exasol/virtual-schema-common-jdbc

[exasol-testcontainers]: https://github.com/exasol/exasol-testcontainers

[user-guide]: https://docs.exasol.com/database_concepts/virtual_schemas.htm

[virtual-schemas]: https://github.com/exasol/virtual-schemas

[vs-api]: https://github.com/exasol/virtual-schema-common-java/blob/master/doc/development/api/virtual_schema_api.md

[vs-doc]: https://github.com/exasol/virtual-schemas/tree/master/doc

[error-reporting-java]: https://gihub.com/exasol/error-reporting-java/