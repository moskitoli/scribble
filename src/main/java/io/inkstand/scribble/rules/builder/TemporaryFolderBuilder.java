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
 * limitations under the License
 */

package io.inkstand.scribble.rules.builder;

import io.inkstand.scribble.rules.TemporaryFile;
import io.inkstand.scribble.rules.jcr.InMemoryContentRepository;
import io.inkstand.scribble.rules.jcr.StandaloneContentRepository;
import org.junit.rules.TemporaryFolder;

/**
 * Builder for the Temporary Folder.
 *
 * @author <a href="mailto:gerald.muecke@gmail.com">Gerald M&uuml;cke</a>
 */
public class TemporaryFolderBuilder extends Builder<TemporaryFolder> {

    private final TemporaryFolder temporaryFolder;

    public TemporaryFolderBuilder() {
        temporaryFolder = new TemporaryFolder();
    }

    /**
     * Creates a builder for an {@link StandaloneContentRepository} that is chained inside the {@link TemporaryFolder}
     *
     * @return an {@link StandaloneContentRepositoryBuilder}
     */
    public StandaloneContentRepositoryBuilder aroundStandaloneContentRepository() {
        return new StandaloneContentRepositoryBuilder(build());
    }

    /**
     * Creates a builder for an {@link InMemoryContentRepository} that is chained inside the {@link TemporaryFolder}
     *
     * @return an {@link InMemoryContentRepositoryBuilder}
     */
    public InMemoryContentRepositoryBuilder aroundInMemoryContentRepository() {
        return new InMemoryContentRepositoryBuilder(build());
    }

    /**
     * Creates a builder for a {@link TemporaryFile} that is chained inside the {@link TemporaryFolder}
     *
     * @param filename
     *            the name of the temporary file
     * @return a {@link TemporaryFileBuilder}
     */
    public TemporaryFileBuilder aroundTempFile(final String filename) {
        return new TemporaryFileBuilder(build(), filename);
    }

    @Override
    public TemporaryFolder build() {
        return temporaryFolder;
    }
}