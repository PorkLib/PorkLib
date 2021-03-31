/*
 * Adapted from The MIT License (MIT)
 *
 * Copyright (c) 2018-2020 DaPorkchop_
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * Any persons and/or organizations using this software must include the above copyright notice and this permission notice,
 * provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package net.daporkchop.lib.binary.stream;

import lombok.NonNull;
import net.daporkchop.lib.common.pool.handle.Handle;
import net.daporkchop.lib.common.system.PlatformInfo;
import net.daporkchop.lib.common.util.PorkUtil;
import net.daporkchop.lib.unsafe.PUnsafe;

import java.io.IOException;
import java.nio.ByteBuffer;

import static java.lang.Math.*;

/**
 * Base implementation of {@link DataOut} for direct-only implementations.
 *
 * @author DaPorkchop_
 */
public abstract class AbstractDirectDataOut extends AbstractDataOut {
    @Override
    protected void write0(@NonNull byte[] src, int start, int length) throws IOException {
        try (Handle<ByteBuffer> handle = PorkUtil.DIRECT_BUFFER_POOL.get()) {
            long addr = PUnsafe.pork_directBufferAddress(handle.get());
            int total = 0;
            do {
                int blockSize = min(length - total, PorkUtil.BUFFER_SIZE);

                //copy to direct buffer
                PUnsafe.copyMemory(src, PUnsafe.ARRAY_BYTE_BASE_OFFSET + start + total, null, addr, blockSize);

                this.write0(addr, blockSize);

                total += blockSize;
            } while (total < length);
        }
    }

    @Override
    public boolean isDirect() {
        return true;
    }

    //
    //
    // primitives
    //
    //

    @Override
    public void writeShort(int v) throws IOException {
        try (Handle<ByteBuffer> handle = PorkUtil.DIRECT_TINY_BUFFER_POOL.get()) {
            ByteBuffer buf = (ByteBuffer) handle.get().position(0).limit(Short.BYTES);
            if (PlatformInfo.IS_BIG_ENDIAN) {
                PUnsafe.putShort(PUnsafe.pork_directBufferAddress(buf), (short) v);
            } else {
                PUnsafe.putShort(PUnsafe.pork_directBufferAddress(buf), Short.reverseBytes((short) v));
            }
            this.write(buf);
        }
    }

    @Override
    public void writeShortLE(int v) throws IOException {
        try (Handle<ByteBuffer> handle = PorkUtil.DIRECT_TINY_BUFFER_POOL.get()) {
            ByteBuffer buf = (ByteBuffer) handle.get().position(0).limit(Short.BYTES);
            if (PlatformInfo.IS_LITTLE_ENDIAN) {
                PUnsafe.putShort(PUnsafe.pork_directBufferAddress(buf), (short) v);
            } else {
                PUnsafe.putShort(PUnsafe.pork_directBufferAddress(buf), Short.reverseBytes((short) v));
            }
            this.write(buf);
        }
    }

    @Override
    public void writeChar(int v) throws IOException {
        try (Handle<ByteBuffer> handle = PorkUtil.DIRECT_TINY_BUFFER_POOL.get()) {
            ByteBuffer buf = (ByteBuffer) handle.get().position(0).limit(Character.BYTES);
            if (PlatformInfo.IS_BIG_ENDIAN) {
                PUnsafe.putChar(PUnsafe.pork_directBufferAddress(buf), (char) v);
            } else {
                PUnsafe.putChar(PUnsafe.pork_directBufferAddress(buf), Character.reverseBytes((char) v));
            }
            this.write(buf);
        }
    }

    @Override
    public void writeCharLE(int v) throws IOException {
        try (Handle<ByteBuffer> handle = PorkUtil.DIRECT_TINY_BUFFER_POOL.get()) {
            ByteBuffer buf = (ByteBuffer) handle.get().position(0).limit(Character.BYTES);
            if (PlatformInfo.IS_LITTLE_ENDIAN) {
                PUnsafe.putChar(PUnsafe.pork_directBufferAddress(buf), (char) v);
            } else {
                PUnsafe.putChar(PUnsafe.pork_directBufferAddress(buf), Character.reverseBytes((char) v));
            }
            this.write(buf);
        }
    }

    @Override
    public void writeInt(int v) throws IOException {
        try (Handle<ByteBuffer> handle = PorkUtil.DIRECT_TINY_BUFFER_POOL.get()) {
            ByteBuffer buf = (ByteBuffer) handle.get().position(0).limit(Integer.BYTES);
            if (PlatformInfo.IS_BIG_ENDIAN) {
                PUnsafe.putInt(PUnsafe.pork_directBufferAddress(buf), v);
            } else {
                PUnsafe.putInt(PUnsafe.pork_directBufferAddress(buf), Integer.reverseBytes(v));
            }
            this.write(buf);
        }
    }

    @Override
    public void writeIntLE(int v) throws IOException {
        try (Handle<ByteBuffer> handle = PorkUtil.DIRECT_TINY_BUFFER_POOL.get()) {
            ByteBuffer buf = (ByteBuffer) handle.get().position(0).limit(Integer.BYTES);
            if (PlatformInfo.IS_LITTLE_ENDIAN) {
                PUnsafe.putInt(PUnsafe.pork_directBufferAddress(buf), v);
            } else {
                PUnsafe.putInt(PUnsafe.pork_directBufferAddress(buf), Integer.reverseBytes(v));
            }
            this.write(buf);
        }
    }

    @Override
    public void writeLong(long v) throws IOException {
        try (Handle<ByteBuffer> handle = PorkUtil.DIRECT_TINY_BUFFER_POOL.get()) {
            ByteBuffer buf = (ByteBuffer) handle.get().position(0).limit(Long.BYTES);
            if (PlatformInfo.IS_BIG_ENDIAN) {
                PUnsafe.putLong(PUnsafe.pork_directBufferAddress(buf), v);
            } else {
                PUnsafe.putLong(PUnsafe.pork_directBufferAddress(buf), Long.reverseBytes(v));
            }
            this.write(buf);
        }
    }

    @Override
    public void writeLongLE(long v) throws IOException {
        try (Handle<ByteBuffer> handle = PorkUtil.DIRECT_TINY_BUFFER_POOL.get()) {
            ByteBuffer buf = (ByteBuffer) handle.get().position(0).limit(Long.BYTES);
            if (PlatformInfo.IS_LITTLE_ENDIAN) {
                PUnsafe.putLong(PUnsafe.pork_directBufferAddress(buf), v);
            } else {
                PUnsafe.putLong(PUnsafe.pork_directBufferAddress(buf), Long.reverseBytes(v));
            }
            this.write(buf);
        }
    }
}
