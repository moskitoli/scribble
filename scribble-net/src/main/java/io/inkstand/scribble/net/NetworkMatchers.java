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

package io.inkstand.scribble.net;

/**
 * Created by Gerald M&uuml;cke on 11.03.2015.
 *
 * @author <a href="mailto:gerald.muecke@gmail.com">Gerald M&uuml;cke</a>
 */
public final class NetworkMatchers {

    private NetworkMatchers() {

    }

    /**
     * Matcher to verify if a {@link io.inkstand.scribble.net.TcpPort} is available to be used as server port.
     *
     * @return a matcher to verify the avilability of a port
     */
    public static ResourceAvailabilityMatcher isAvailable() {

        return new ResourceAvailabilityMatcher();
    }

    /**
     * Matcher to verify if a {@link io.inkstand.scribble.net.TcpPort} is available to be used as server port.
     *
     * @return a matcher to verify the avilability of a port
     */
    public static EndpointMatcher isReachable() {

        return new EndpointMatcher();
    }

    /**
     * Creates a type-safe tcp port to be verified using matchers
     *
     * @param port
     *         the tcp port number to be wrapped
     *
     * @return a {@link TcpPort} instance describing the tcp port
     */
    public static TcpPort port(int port) {

        return new TcpPort(port);
    }

    /**
     * Creates a type-safe tcp port pointing ot a remote host and port.
     *
     * @param hostname
     *         the hostname of the remote host
     * @param port
     *         the port of the remote host
     *
     * @return a {@link TcpPort} instance describing the tcp port
     */
    public static TcpPort remotePort(String hostname, int port){
        return new RemoteTcpPort(hostname, port);
    }

}
