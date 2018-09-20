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

package net.daporkchop.lib.network.packet.encapsulated;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.daporkchop.lib.binary.stream.DataIn;
import net.daporkchop.lib.binary.stream.DataOut;

import java.io.IOException;

/**
 * @author DaPorkchop_
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LargeDataPacket implements EncapsulatedPacket {
    public boolean first;
    public int totalLength;
    public int offset;
    public byte[] data;

    @Override
    public void read(DataIn in) throws IOException {
        if (this.first = in.readBoolean())  {
            this.totalLength = in.readInt();
        }
        this.offset = in.readInt();
        this.data = in.readBytesSimple();
    }

    @Override
    public void write(DataOut out) throws IOException {
        if (this.first) {
            out.writeBoolean(true);
            out.writeInt(this.totalLength);
        } else {
            out.writeBoolean(false);
        }
        out.writeInt(this.offset);
        out.writeBytesSimple(this.data);
    }

    @Override
    public EncapsulatedType getType() {
        return EncapsulatedType.LARGE_DATA;
    }
}
