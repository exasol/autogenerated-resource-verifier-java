package com.exasol.autogeneratedresourceverifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import com.exasol.errorreporting.ExaError;

/**
 * This class verifies that an {@link AutogeneratedResource} has the expected value. In addition it can fix it.
 */
public class AutogeneratedResourceVerifier {
    public static final String FIX_SYSTEM_PROPERTY = "fixAutogeneratedResources";

    /**
     * Verify that an {@link AutogeneratedResource} has the expected value
     * 
     * @param projectDir            projects root directory
     * @param autogeneratedResource Autogenerated resource to check
     */
    public void verifyResource(final Path projectDir, final AutogeneratedResource autogeneratedResource) {
        final String expected = autogeneratedResource.generateContent();
        final Path resourcePath = projectDir.resolve(autogeneratedResource.getPathOfGeneratedFile());
        if (isFixEnabled()) {
            overrideFile(expected, resourcePath);
        } else {
            validateFile(expected, resourcePath);
        }
    }

    private void validateFile(final String expected, final Path resourcePath) {
        assertResourceExists(resourcePath);
        final String actual = readResource(resourcePath);
        if (!expected.trim().equals(actual.trim())) {
            throw new IllegalStateException(ExaError.messageBuilder("E-ARVJ-4")
                    .message("Autogenerated resource {{resource name}} is outdated.")
                    .parameter("resource name", resourcePath.toString())
                    .mitigation("You can enable automatically update by adding -D" + FIX_SYSTEM_PROPERTY + "=true.")
                    .toString());
        }
    }

    private void overrideFile(final String expected, final Path resourcePath) {
        try {
            createEnclosingDirectoryIfNotExists(resourcePath);
            Files.writeString(resourcePath, expected, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (final IOException exception) {
            throw new IllegalStateException(ExaError.messageBuilder("E-ARVJ-5")
                    .message("Failed to write autogenerated resource {{resource name}}.")
                    .parameter("resource name", resourcePath.toString()).toString(), exception);
        }
    }

    private void createEnclosingDirectoryIfNotExists(final Path resourcePath) {
        final File directory = resourcePath.toFile().getParentFile();
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new IllegalStateException(ExaError.messageBuilder("E-ARVJ-3")
                        .message("Failed to crate directory {{directory name}} for autogenerated resource.")
                        .parameter("directory name", directory.toString()).toString());
            }
        }
    }

    private boolean isFixEnabled() {
        return System.getProperty(FIX_SYSTEM_PROPERTY, "").equals("true");
    }

    private void assertResourceExists(final Path resourcePath) {
        if (!resourcePath.toFile().exists()) {
            throw new IllegalStateException(
                    ExaError.messageBuilder("E-ARVJ-2").message("Autogenerated resource {{resource name}} missing.")
                            .mitigation("Use -D" + FIX_SYSTEM_PROPERTY + "=true to create the missing File.")
                            .parameter("resource name", resourcePath.toString()).toString());
        }
    }

    private String readResource(final Path resourcePath) {
        try {
            return Files.readString(resourcePath);
        } catch (final IOException exception) {
            throw new IllegalStateException(ExaError.messageBuilder("E-ARVJ-1")
                    .message("Failed to read required resource {{resource name}} for verification.")
                    .parameter("resource name", resourcePath.toString()).toString(), exception);
        }
    }
}
