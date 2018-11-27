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

package net.daporkchop.lib.network.pork.packet;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.daporkchop.lib.binary.stream.DataIn;
import net.daporkchop.lib.binary.stream.DataOut;
import net.daporkchop.lib.network.packet.Codec;
import net.daporkchop.lib.network.pork.PorkConnection;
import net.daporkchop.lib.network.pork.PorkPacket;

import java.io.IOException;

/**
 * Sent before a connection is closed to inform the remote end of the reason for the disconnection
 *
 * @author DaPorkchop_
 */
@NoArgsConstructor
@AllArgsConstructor
public class DisconnectPacket implements PorkPacket {
    public String reason;

    @Override
    public void read(DataIn in) throws IOException {
        this.reason = in.readUTF();
    }

    @Override
    public void write(DataOut out) throws IOException {
        out.writeUTF(this.reason);
    }

    public static class DisconnectCodec implements Codec.Simple<DisconnectPacket, PorkConnection> {
        @Override
        public void handle(@NonNull DisconnectPacket packet, @NonNull PorkConnection connection) {
            connection.setDisconnectReason(packet.reason);
            connection.getRealConnection().disconnectAtNetworkLevel(); //disconnecting at network level prevents an endless ping/pong of disconnect packets
        }

        @Override
        public DisconnectPacket createInstance() {
            return new DisconnectPacket();
        }
    }
}