/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2018-2020 DaPorkchop_ and contributors
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

import net.daporkchop.lib.http.impl.netty.server.NettyHttpServer;
import net.daporkchop.lib.http.impl.netty.util.NettyHttpUtil;
import net.daporkchop.lib.http.request.query.Query;
import net.daporkchop.lib.http.server.HttpServer;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author DaPorkchop_
 */
public class HttpServerExample {
    public static void main(String... args) {
        if (true)   {
            String[] arr = {
                    "/",
                    "/lol/",
                    "/lol",
                    "/lol?jeff",
                    "/lol/?jeff&lol&ok",
                    "/lol/?jeff=ok",
                    "/lol/?jeff&nyef=jef",
                    "/lol?jeff=ok&nyef=jef&lol&my=name",
                    "/lol/?jeff=ok&nyef=j%EF%20%1f03",
                    "/lol/?jeff=ok&nyef=jef&lol&my=name",
                    "/lol/#section",
                    "/lol/?jeff=ok&nyef=jef&lol&my=name#section",
                    "/lol?jeff#section",
                    "/lol/?jeff#section",
                    "/lol?jeff=ok#section",
                    "/lol/?jeff=ok#section",
                    "/lol?jeff&nyef#section",
                    "/lol/?jeff&nyef#section",
                    "/lol?jeff=ok&nyef#section",
                    "/lol/?jeff=ok&nyef#section"
            };
            for (String line : arr) {
                NettyHttpUtil.parseQuery(line);
            }
            return;
        }

        HttpServer server = new NettyHttpServer();

        server.handler((query, headers, response) -> {
            System.out.println(query);
            throw new UnsupportedOperationException();
        });

        System.out.println("Binding to port 8080...");
        server.bind(new InetSocketAddress(8080)).syncUninterruptibly();
        System.out.println("Bound to port!");

        try (Scanner scanner = new Scanner(System.in))  {
            scanner.nextLine();
        }

        System.out.println("Closing...");
        server.close().syncUninterruptibly();
        System.out.println("Closed!");
    }
}