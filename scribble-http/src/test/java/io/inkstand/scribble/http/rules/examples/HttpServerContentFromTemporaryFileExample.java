package io.inkstand.scribble.http.rules.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import io.inkstand.scribble.http.rules.HttpServer;
import io.inkstand.scribble.http.rules.HttpServerBuilder;
import io.inkstand.scribble.rules.TemporaryFile;
import io.inkstand.scribble.rules.builder.TemporaryFileBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TemporaryFolder;

/**
 * Created by Gerald Muecke on 04.12.2015.
 */
public class HttpServerContentFromTemporaryFileExample {

    public TemporaryFolder folder = new TemporaryFolder();
    public TemporaryFile file = new TemporaryFileBuilder(folder, "index.html").fromClasspathResource("index.html").build();

    public HttpServer server = new HttpServerBuilder().contentFrom("/index.html", file).build();
    @Rule
    public RuleChain rule = RuleChain.outerRule(folder).around(file).around(server);

    @Test
    public void testHttpServerGet() throws Exception {
        //prepare

        //act
        try (final WebClient webClient = new WebClient()) {

            final HtmlPage page = webClient.getPage(server.getBaseUrl() + "/index.html");
            final String pageAsXml = page.asXml();
            final String pageAsText = page.asText();

            //assert
            assertEquals("Test Content", page.getTitleText());
            assertTrue(pageAsXml.contains("<body>"));
            assertTrue(pageAsText.contains("Test Content Body"));
        }
    }
}
