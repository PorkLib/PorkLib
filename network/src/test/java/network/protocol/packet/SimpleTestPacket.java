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

package network.protocol.packet;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import net.daporkchop.lib.binary.stream.DataIn;
import net.daporkchop.lib.binary.stream.DataOut;
import net.daporkchop.lib.logging.Logging;
import net.daporkchop.lib.network.conn.UnderlyingNetworkConnection;
import net.daporkchop.lib.network.packet.handler.DataPacketHandler;

@AllArgsConstructor
public class SimpleTestPacket {
    @NonNull
    public final String message;

    public static class MessageHandler implements DataPacketHandler<SimpleTestPacket>, Logging {
        @Override
        public void handle(@NonNull SimpleTestPacket packet, @NonNull UnderlyingNetworkConnection connection, int channelId) throws Exception {
            logger.info("[%s] Received test packet on channel %d: %s", connection.getEndpoint().getName(), channelId, packet.message);
        }

        @Override
        public void encode(@NonNull SimpleTestPacket packet, @NonNull DataOut out) throws Exception {
            out.writeUTF(packet.message);
        }

        @Override
        public SimpleTestPacket decode(@NonNull DataIn in) throws Exception {
            return new SimpleTestPacket(
                    in.readUTF()
            );
        }

        @Override
        public Class<SimpleTestPacket> getPacketClass() {
            return SimpleTestPacket.class;
        }
    }
}
