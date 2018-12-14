/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2018-2018 DaPorkchop_ and contributors
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

package net.daporkchop.lib.network.endpoint.builder;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Getter;
import lombok.NonNull;
import net.daporkchop.lib.common.util.PorkUtil;
import net.daporkchop.lib.network.conn.UserConnection;
import net.daporkchop.lib.network.endpoint.Endpoint;
import net.daporkchop.lib.network.packet.UserProtocol;
import net.daporkchop.lib.network.pork.PorkProtocol;
import net.daporkchop.lib.network.protocol.api.ProtocolManager;
import net.daporkchop.lib.network.protocol.netty.tcp.TcpProtocolManager;

import java.net.InetSocketAddress;
import java.util.ArrayDeque;
import java.util.Collection;

/**
 * Shared code for all endpoint builders
 *
 * @author DaPorkchop_
 * @see ClientBuilder
 * @see ServerBuilder
 */
@Getter
public abstract class AbstractBuilder<E extends Endpoint, B extends AbstractBuilder<E, B>> {
    private static final EventLoopGroup DEFAULT_GROUP = new NioEventLoopGroup(0, PorkUtil.DEFAULT_EXECUTOR);

    private final Collection<UserProtocol> protocols = new ArrayDeque<UserProtocol>() {
        {
            this.add(PorkProtocol.INSTANCE);
        }
    };

    /**
     * The address of the endpoint.
     * <p>
     * For clients, this is the address of the target server.
     * For servers, this is the bind address.
     */
    @NonNull
    private InetSocketAddress address;

    /**
     * The protocol manager to use for creating connections.
     * <p>
     * This defines most behaviors of the connection, for example the transport protocol or packet reliability.
     * <p>
     * Default implementations:
     *
     * @see net.daporkchop.lib.network.protocol.netty.tcp.TcpProtocolManager#INSTANCE
     * @see net.daporkchop.lib.network.protocol.netty.sctp.SctpProtocolManager#INSTANCE
     */
    @NonNull
    private ProtocolManager manager = TcpProtocolManager.INSTANCE;

    /**
     * An {@link EventLoopGroup} for handling threading on connections.
     * <p>
     * Some implementations of {@link ProtocolManager} may ignore this setting.
     */
    @NonNull
    private EventLoopGroup eventGroup = DEFAULT_GROUP;

    @SuppressWarnings("unchecked")
    public <C extends UserConnection> B addProtocol(@NonNull UserProtocol<C> protocol) {
        synchronized (this.protocols) {
            this.protocols.add(protocol);
        }
        return (B) this;
    }

    @SuppressWarnings("unchecked")
    public B setAddress(@NonNull InetSocketAddress address) {
        this.address = address;
        return (B) this;
    }

    @SuppressWarnings("unchecked")
    public B setManager(@NonNull ProtocolManager manager) {
        this.manager = manager;
        return (B) this;
    }

    @SuppressWarnings("unchecked")
    public B setEventGroup(@NonNull EventLoopGroup eventGroup) {
        this.eventGroup = eventGroup;
        return (B) this;
    }

    public E build() {
        if (this.protocols.isEmpty()) {
            throw new IllegalStateException("At least one protocol must be registered!");
        } else if (this.address == null) {
            throw new IllegalStateException("address must be set!");
        }

        return this.doBuild();
    }

    abstract E doBuild();
}
