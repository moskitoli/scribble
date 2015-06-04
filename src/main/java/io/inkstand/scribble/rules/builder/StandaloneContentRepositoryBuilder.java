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

package io.inkstand.scribble.rules.builder;

import java.net.URL;
import org.junit.rules.TemporaryFolder;

import io.inkstand.scribble.rules.jcr.StandaloneContentRepository;

/**
 * A Builder for an {@link StandaloneContentRepository}. The {@link StandaloneContentRepository} requires a
 * {@link TemporaryFolder} as outer rule.
 *
 * @author <a href="mailto:gerald.muecke@gmail.com">Gerald M&uuml;cke</a>
 */
public class StandaloneContentRepositoryBuilder extends ContentRepositoryBuilder<StandaloneContentRepository> {

    /**
     * The working directory for the standalone repository.
     */
    private transient final TemporaryFolder workingDirectory;

    private transient URL configUrl;

    public StandaloneContentRepositoryBuilder(final TemporaryFolder workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    /**
     * Defines a resource that provides the configuration for the standalone repository. Without specifying a
     * configuration file, the repository will be configured using a default configuration using in-memory persistence
     * and no security, which is similar to use an {@link InMemoryContentRepositoryBuilder} without security.
     * @param configUrl
     *  the url to the configuration file
     * @return
     *  this builder
     */
    public StandaloneContentRepositoryBuilder withConfiguration(final URL configUrl) {
        this.configUrl = configUrl;
        return this;
    }

    /**
     * The resource that defines the configuration for the repository that should be created.
     * @return
     *  the url to the configuration file
     */
    protected URL getConfigUrl() {
        return configUrl;
    }

    @Override
    public StandaloneContentRepository build() {

        StandaloneContentRepository repository = new StandaloneContentRepository(workingDirectory);
        if(configUrl != null) {
            repository.setConfigUrl(configUrl);
        }
        repository.setCndUrl(getCndModelResource());
        return repository;
    }

}
