package li.moskito.scribble.rules.builder;

import java.net.URL;

import li.moskito.scribble.rules.TemporaryFile;

import org.junit.rules.TemporaryFolder;

/**
 * Builder for creating a temporary file in a temporary folder.
 *
 * @author Gerald Muecke, gerald@moskito.li
 */
public class TemporaryFileBuilder extends Builder<TemporaryFile> {

    private final TemporaryFile temporaryFile;

    public TemporaryFileBuilder(final TemporaryFolder folder, final String fileName) {
        temporaryFile = new TemporaryFile(folder, fileName);
    }

    @Override
    public TemporaryFile build() {
        return temporaryFile;
    }

    /**
     * Defines the classpath resource from where the content of the file should be retrieved
     *
     * @param pathToResource
     *            the path to the classpath resource
     * @return the builder
     */
    public TemporaryFileBuilder fromClasspathResource(final String pathToResource) {
        final ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        URL contentUrl;
        if (ccl != null) {
            contentUrl = ccl.getResource(pathToResource);
        } else {
            contentUrl = getClass().getResource(pathToResource);
        }
        temporaryFile.setContentUrl(contentUrl);
        return this;
    }

    /**
     * Defines the resource by URL from where the content of the file should be retrieved
     *
     * @param resource
     *            the resource whose content will be used for the temporary file as content
     * @return the builder
     */
    public TemporaryFileBuilder fromResource(final URL resource) {
        temporaryFile.setContentUrl(resource);
        return this;
    }

    /**
     * Defines, that the external file must not be empty, which means, the rule enforces, the contentUrl is set. The
     * resource addressed by the URL may be empty nevertheless.
     *
     * @return the builder
     */
    public TemporaryFileBuilder withContent() {
        temporaryFile.setForceContent(true);
        return this;
    }

}