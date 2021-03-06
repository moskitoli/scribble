/*
 * Copyright 2015-2016 DevCon5 GmbH, info@devcon5.ch
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

package io.inkstand.scribble.http.rules.examples;

import static org.junit.Assert.assertEquals;

import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;
import io.inkstand.scribble.http.rules.HttpServer;
import io.inkstand.scribble.http.rules.HttpServerBuilder;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Gerald Muecke on 04.12.2015.
 */
public class HttpServerResponseStubbingExample {


    @Rule
    public HttpServer server = new HttpServerBuilder().port(55555).build();

    @Test
    public void testHttpServerGet() throws Exception {
        //prepare
        server.onGet("/index.html").respond("someContent");

        //act
        try (final WebClient webClient = new WebClient()) {

            final TextPage page = webClient.getPage(server.getBaseUrl() + "/index.html");
            final String pageAsText = page.getContent();

            //assert
            assertEquals("someContent", pageAsText);
        }

    }
}
