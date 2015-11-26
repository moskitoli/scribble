/*
 * Copyright 2015 Gerald Muecke, gerald.muecke@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.inkstand.scribble.rules;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.junit.rules.TemporaryFolder;

/**
 * A rule for creating an external file in a temporary folder with a specific content. If no content is defined an empty
 * file will be created
 *
 * @author <a href="mailto:gerald.muecke@gmail.com">Gerald M&uuml;cke</a>
 */
public class TemporaryFile extends ExternalResource<TemporaryFolder> {

    /**
     * The URL that points to the resource that provides the content for the file
     */
    private URL contentUrl;
    /**
     * The temporary folder the file will be created in
     */
    private final TemporaryFolder folder;
    /**
     * The name of the file
     */
    private final String filename;
    /**
     * The actual created file
     */
    private File file;
    /**
     * Flag to indicate that content URL must be set
     */
    private boolean forceContent;

    /**
     * Creates an ExternalFile in the specified temporary folder with the specified filename
     *
     * @param folder
     * @param filename
     */
    public TemporaryFile(final TemporaryFolder folder, final String filename) {

        super(folder);
        this.folder = folder;
        this.filename = filename;
    }

    @Override
    protected void before() throws Throwable {

        createTempFile();
    }

    /**
     * The filename of the temporary file
     * @return
     *  the name of the file
     */
    protected String getFilename() {
        return filename;
    }

    /**
     * Creates a new empty file in the temporary folder.
     * @return
     *  the file handle to the empty file
     * @throws IOException
     */
    protected File newFile() throws IOException {

        return folder.newFile(filename);
    }

    /**
     * Creates the file including content. Override this method to implement a custom mechanism to create the temporary
     * file
     * @return
     *  the file handle to the newly created file
     * @throws IOException
     */
    protected File createTempFile() throws IOException {

        final File file = newFile();
        if (forceContent && contentUrl == null) {
            throw new AssertionError("ContentUrl is not set");
        } else if (contentUrl != null) {
            try (InputStream is = contentUrl.openStream();
                 OutputStream os = new FileOutputStream(file)) {
                IOUtils.copy(is, os);
            }

        }
        return file;
    }

    @Override
    protected void after() {

        file.delete(); // NOSONAR

    }

    @Override
    protected void beforeClass() throws Throwable {

        before();
    }

    @Override
    protected void afterClass() {

        after();
    }

    /**
     * Returns the file handle of the external file
     *
     * @return
     */
    public File getFile() {

        return file;
    }

    /**
     * Sets the URL that contains the content for the file. <br>
     * The method must be invoked before the rule is applied.
     *
     * @param contentUrl
     */
    @RuleSetup
    public void setContentUrl(final URL contentUrl) {

        this.contentUrl = contentUrl;
    }

    /**
     * Setting this to true will ensure, the file has content provided by the content url. If set to false the file may
     * not have a content url associated and therefore may be empty. <br>
     * <br>
     * The method must be invoked before the rule is applied.
     *
     * @param forceContent
     *            <code>true</code> if contentURL has to be set
     */
    @RuleSetup
    public void setForceContent(final boolean forceContent) {

        this.forceContent = forceContent;
    }

}
