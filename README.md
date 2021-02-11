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
                <arguments>
                    <argument>${project.basedir}</argument>
                </arguments>
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