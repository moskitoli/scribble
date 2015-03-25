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

import io.inkstand.scribble.rules.jcr.InMemoryContentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryContentRepositoryBuilderTest {

    @Mock
    private TemporaryFolder folder;

    private InMemoryContentRepositoryBuilder subject;

    @Before
    public void setUp() throws Exception {

        subject = new InMemoryContentRepositoryBuilder(folder);
    }

    @Test
    public void testBuild() throws Exception {

        final InMemoryContentRepository rule = subject.build();
        assertNotNull(rule);
    }

}