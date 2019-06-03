/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2018-2019 DaPorkchop_ and contributors
 *
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it. Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 *
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package http;

import net.daporkchop.lib.logging.LogAmount;
import net.daporkchop.lib.logging.Logging;
import net.daporkchop.lib.network.endpoint.PClient;
import net.daporkchop.lib.network.endpoint.builder.ClientBuilder;
import net.daporkchop.lib.network.tcp.TCPEngine;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author DaPorkchop_
 */
public class TestHTTPGet implements Logging {
    protected static final String HOST = "maven.daporkchop.net";

    public static void main(String... args) {
        logger.enableANSI().setLogAmount(LogAmount.DEBUG).info("Starting client...");

        PClient<HTTPSession> client = ClientBuilder.of(new HTTPProtocol())
                                                   .engine(TCPEngine.defaultInstance())
                                                   .address(new InetSocketAddress(HOST, 443))
                                                   .build();
        logger.success("Client started.").info("Sending request...");

        client.sendFlushAsync("GET / HTTP/1.1\r\n" +
                "Host: " + HOST + "\r\n" +
                "User-Agent: PorkLib\r\n\r\n")
              .addListener(v -> logger.success("Request sent."));

        client.userSession().complete.sync(TimeUnit.SECONDS, 5L);
        logger.info("Headers:\n%s\nBody:\n%s", client.userSession().headers, client.userSession().body);
        if (!client.userSession().complete.isComplete())    {
            logger.warn("Connection not automatically closed by server, forcibly closing it!");
            client.closeAsync().addListener(v -> logger.success("Client closed."));
        }
    }
}
