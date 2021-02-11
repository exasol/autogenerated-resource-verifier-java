package com.exasol.autogeneratedresourceverifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AutogeneratedResourceVerifierTest {

    private static final String EXAMPLE_CONTENT = "test\n123";
    private static final AutogeneratedResourceVerifier VERIFIER = new AutogeneratedResourceVerifier();
    private static final Path EXAMPLE_PATH = Path.of("doc", "generated", "myGeneratedDoc.md");
    @TempDir
    Path tempDir;

    @BeforeEach
    void beforeEach() {
        System.setProperty(AutogeneratedResourceVerifier.FIX_SYSTEM_PROPERTY, "");
    }

    @Test
    void testDocMissing() {
        final AutogeneratedResource resource = createMockResource();
        final IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> VERIFIER.verifyResource(this.tempDir, resource));
        assertAll(() -> assertThat(exception.getMessage(), containsString("E-ARVJ-2")),
                () -> assertThat(exception.getMessage(), containsString("myGeneratedDoc.md"))//
        );
    }

    @Test
    void testDocPresent() throws IOException {
        writeFile(EXAMPLE_CONTENT);
        assertDoesNotThrow(() -> VERIFIER.verifyResource(this.tempDir, createMockResource()));
    }

    @Test
    void testWrongContent() throws IOException {
        writeFile("other content");
        final AutogeneratedResource mockResource = createMockResource();
        final IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> VERIFIER.verifyResource(this.tempDir, mockResource));
        assertAll(() -> assertThat(exception.getMessage(), containsString("E-ARVJ-4")),
                () -> assertThat(exception.getMessage(), containsString("myGeneratedDoc.md"))//
        );
    }

    @Test
    void testFixMissingFile() throws IOException {
        System.setProperty(AutogeneratedResourceVerifier.FIX_SYSTEM_PROPERTY, "true");
        VERIFIER.verifyResource(this.tempDir, createMockResource());
        assertThat(Files.readString(this.tempDir.resolve(EXAMPLE_PATH)), equalTo(EXAMPLE_CONTENT));
    }

    @Test
    void testFixWrongContent() throws IOException {
        writeFile("other content");
        System.setProperty(AutogeneratedResourceVerifier.FIX_SYSTEM_PROPERTY, "true");
        VERIFIER.verifyResource(this.tempDir, createMockResource());
        assertThat(Files.readString(this.tempDir.resolve(EXAMPLE_PATH)), equalTo(EXAMPLE_CONTENT));
    }

    private void writeFile(final String content) throws IOException {
        if (!this.tempDir.resolve(EXAMPLE_PATH).toFile().getParentFile().mkdirs()) {
            throw new IllegalStateException("Failed to create test directories");
        }
        Files.writeString(this.tempDir.resolve(EXAMPLE_PATH), content);
    }

    private AutogeneratedResource createMockResource() {
        final AutogeneratedResource resource = mock(AutogeneratedResource.class);
        when(resource.generateContent()).thenReturn(EXAMPLE_CONTENT);
        when(resource.getPathOfGeneratedFile()).thenReturn(EXAMPLE_PATH);
        return resource;
    }
}