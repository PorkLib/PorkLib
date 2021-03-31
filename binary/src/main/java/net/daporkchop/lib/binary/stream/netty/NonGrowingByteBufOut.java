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

package net.daporkchop.lib.binary.stream.netty;

import io.netty.buffer.ByteBuf;
import lombok.NonNull;

import java.io.IOException;

import static net.daporkchop.lib.common.util.PValidation.*;

/**
 * Variant of {@link DirectByteBufOut} which doesn't allow the destination buffer to be grown.
 *
 * @author DaPorkchop_
 */
public class NonGrowingByteBufOut extends ByteBufOut {
    public NonGrowingByteBufOut(@NonNull ByteBuf delegate) {
        super(delegate);
    }

    @Override
    protected void write0(int b) throws IOException {
        checkIndex(this.delegate.isWritable());
        super.write0(b);
    }

    @Override
    protected void write0(@NonNull byte[] src, int start, int length) throws IOException {
        checkIndex(this.delegate.isWritable(length));
        super.write0(src, start, length);
    }

    @Override
    protected void write0(long addr, long length) throws IOException {
        checkIndex(this.delegate.isWritable(toInt(length, "length")));
        super.write0(addr, length);
    }
}
