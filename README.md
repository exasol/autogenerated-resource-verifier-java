# Autogenerated Resource Verifier for Java

[![Build Status](https://github.com/exasol/autogenerated-resource-verifier-java/actions/workflows/ci-build.yml/badge.svg)](https://github.com/exasol/autogenerated-resource-verifier-java/actions/workflows/ci-build.yml)
[![Maven Central – Autogenerated resource verifier](https://img.shields.io/maven-central/v/com.exasol/autogenerated-resource-verifier-java)](https://search.maven.org/artifact/com.exasol/autogenerated-resource-verifier-java)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aautogenerated-resource-verifier-java&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.exasol%3Aautogenerated-resource-verifier-java)

[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aautogenerated-resource-verifier-java&metric=security_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aautogenerated-resource-verifier-java)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aautogenerated-resource-verifier-java&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aautogenerated-resource-verifier-java)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aautogenerated-resource-verifier-java&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aautogenerated-resource-verifier-java)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aautogenerated-resource-verifier-java&metric=sqale_index)](https://sonarcloud.io/dashboard?id=com.exasol%3Aautogenerated-resource-verifier-java)

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aautogenerated-resource-verifier-java&metric=code_smells)](https://sonarcloud.io/dashboard?id=com.exasol%3Aautogenerated-resource-verifier-java)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aautogenerated-resource-verifier-java&metric=coverage)](https://sonarcloud.io/dashboard?id=com.exasol%3Aautogenerated-resource-verifier-java)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aautogenerated-resource-verifier-java&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=com.exasol%3Aautogenerated-resource-verifier-java)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aautogenerated-resource-verifier-java&metric=ncloc)](https://sonarcloud.io/dashboard?id=com.exasol%3Aautogenerated-resource-verifier-java)

This Java library allows you to automatically create resources in Java.

## Usage

Define the content of your document in your test source code:

```java
public class MyReportGenerator {

    public static void main(final String[] args) {
        new AutogeneratedResourceVerifier().verifyResource(Path.of(args[0]), new MyReport());
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

The verifier can also fix the generated resource. To do so, run:

```shell
mvn package -DfixAutogeneratedResources=true
```

## Additional Information

* [Changelog](doc/changes/changelog.md)
* [Dependencies](dependencies.md)
