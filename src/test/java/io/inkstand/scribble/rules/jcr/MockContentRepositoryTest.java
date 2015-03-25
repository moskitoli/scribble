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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.MockUtil;

import javax.jcr.Repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MockContentRepositoryTest {

    private MockContentRepository subject;

    @Before
    public void setUp() throws Exception {
        subject = new MockContentRepository();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateRepository() throws Exception {
        final Repository repository = subject.createRepository();
        assertNotNull(repository);
        assertTrue(new MockUtil().isMock(repository));
    }

    @Test
    public void testMockContentRepository_noWorkingDirectory() throws Throwable {
        // prepare
        BaseRuleHelper.setInitialized(subject);
        // act
        assertNull(subject.getWorkingDirectory());
    }

}