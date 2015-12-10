package io.inkstand.scribble.http.rules;

import static java.nio.file.FileSystems.newFileSystem;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.util.Collections;
import java.util.Map;

import io.inkstand.scribble.net.NetworkUtils;
import io.inkstand.scribble.rules.ExternalResource;
import io.inkstand.scribble.rules.TemporaryZipFile;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.server.handlers.resource.ResourceManager;
import org.slf4j.Logger;

/**
 * Server rule that starts an embedded http server, that serves static content. The server may be instantiated directly
 * or by using the {@link HttpServerBuilder}. Content may be provided as generated zip, using the {@link io.inkstand
 * .scribble.rules.TemporaryFile} rule or as predefinded zip from the classpath.
 * Created by Gerald Muecke on 04.12.2015.
 */
public class HttpServer extends ExternalResource {

    private static final Logger LOG = getLogger(HttpServer.class);

    private final String hostname;
    private final int port;
    private final Map<String, Object> resources;
    private Undertow server;

    /**
     * Creates a http server on localhost, running on an available tcp port. The server won't server any static content.
     */
    public HttpServer() {

        this("localhost", NetworkUtils.findAvailablePort());
    }

    /**
     * Creates a http server for the specified hostname and tcp port. The server won't server any static content.
     * @param hostname
     *  the hostname the server listens on.
     * @param port
     *  the tcp port the server is accepting incoming connections.
     */
    public HttpServer(String hostname, int port) {

        this(hostname, port, Collections.EMPTY_MAP);
    }

    /**
     * Creates a http server for the specified hostname and tcp port. The server serves the content on the context paths
     * provided in the resource map.
     * @param hostname
     *  the hostname the server listens on.
     * @param port
     *  the tcp port the server is accepting incoming connections.
     * @param resources
     */
    public HttpServer(final String hostname, final int port, final Map<String, Object> resources) {

        this.hostname = hostname;
        this.port = port;
        this.resources = resources;
    }

    @Override
    protected void beforeClass() throws Throwable {

        before();
    }

    @Override
    protected void afterClass() {

        after();
    }

    @Override
    protected void before() throws Throwable {

        LOG.info("Creating http server {}:{}", getHostname(), getPort());
        final PathHandler pathHandler = new PathHandler();
        for (Map.Entry<String, Object> entry : this.resources.entrySet()) {
            final String path = entry.getKey();
            final Object resource = entry.getValue();
            addResource(pathHandler, path, resource);
        }
        this.server = Undertow.builder().addHttpListener(this.port, this.hostname).setHandler(pathHandler).build();
        LOG.info("Starting HTTP server");
        this.server.start();
        LOG.info("HTTP Server running");
    }

    /**
     * Adds a resource to the path handler under the specified context path. Resources may be of various types:
     * <ul>
     *     <li>{@link io.inkstand.scribble.rules.TemporaryZipFile} - zip file that is created for test execution.
     *     All files in the zip are hosted on the specified path as root folder.
     *     </li>
     *     <li>{@link java.net.URL} pointing to a zip resource, same as the TemporaryZipFile but the zip has to
     *     be predined</li>
     * </ul>
     * @param pathHandler
     *  the path handler that is used to dispatch requests to the right resource depending on the path
     * @param path
     *  the path to the resource
     * @param resource
     *  a resource to add. The method can handle various types of resources.
     * @throws IOException
     * @throws URISyntaxException
     */
    private void addResource(final PathHandler pathHandler, final String path, final Object resource)
            throws IOException, URISyntaxException {

        if (resource instanceof TemporaryZipFile) {
            final URI uri = ((TemporaryZipFile) resource).getFile().toURI();
            pathHandler.addPrefixPath(path, createZipResourceHandler(uri));
        } else if (resource instanceof URL) {
            final URI uri = ((URL) resource).toURI();
            if(uri.getPath().endsWith(".zip")) {
                pathHandler.addPrefixPath(path, createZipResourceHandler(uri));
            }
        }
    }

    /**
     * Creates the resource handle for a zip file, specified by the URL.
     * @param zipFile
     *  url to a zip file
     * @return
     *  the resource handler to handle requests to files in the zip
     * @throws IOException
     */
    private ResourceHandler createZipResourceHandler(final URI zipFile) throws IOException {

        final FileSystem fs = newFileSystem(URI.create("jar:" + zipFile), Collections.<String, Object>emptyMap());
        final ResourceManager resMgr = new FileSystemResourceManager(fs);
        return new ResourceHandler(resMgr);
    }

    @Override
    protected void after() {

        LOG.info("Stopping HTTP server");
        this.server.stop();
        LOG.info("HTTP Server stopped");
    }

    /**
     * Provides the hostname of the http server. The server always runs on localhost, but possibly under another
     * alias of it.
     * @return
     *  the hostname of the server
     */
    public String getHostname() {

        return hostname;
    }

    /**
     * The tcp port the server accepts incoming requests.
     * @return
     *  the tcp port.
     */
    public int getPort() {

        return port;
    }

    public GetResponseStubbing onGet(final String resource) {

        return null;
    }

    /**
     * Creates an URL to the root path of the http server, i.e. 'http://localhost:8080/'
     * @return
     *  the base URL to the http server
     */
    public URL getBaseUrl() {

        try {
            return new URL("http", getHostname(), getPort(), "/");
        } catch (MalformedURLException e) {
            throw new AssertionError("Invalid base URL", e);
        }
    }

}
