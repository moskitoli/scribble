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

package io.inkstand.scribble.rules.jcr;

import io.inkstand.scribble.rules.BaseRuleHelper;
import org.apache.jackrabbit.core.RepositoryImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import javax.jcr.Repository;
import java.net.URL;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class StandaloneContentRepositoryTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private final URL configUrl = getClass().getResource("StandaloneContentRepositoryTest_repository.xml");
    private StandaloneContentRepository subject;

    private RepositoryImpl repositorySpy;

    @Before
    public void setUp() throws Exception {
        subject = new StandaloneContentRepository(folder) {
            @Override
            public Repository getRepository() {
                repositorySpy = spy((RepositoryImpl) super.getRepository());
                return repositorySpy;
            }
        };
    }

    @Test
    public void testCreateRepository_noConfigUrl_useDefaultConfig() throws Exception {
        // act
        final Repository repository = subject.createRepository();

        // assert
        assertNotNull(repository);
    }

    @Test
    public void testCreateRepository_withConfigUrl() throws Exception {
        // prepare
        subject.setConfigUrl(configUrl);

        // act
        final Repository repository = subject.createRepository();

        // assert
        assertNotNull(repository);

    }

    @Test
    public void testDestroyRepository() throws Throwable {
        // prepare
        subject.before();
        BaseRuleHelper.setInitialized(subject);
        // act
        subject.destroyRepository();
        // assert
        assertNotNull(repositorySpy);
        verify(repositorySpy).shutdown();

    }

}
