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

package net.daporkchop.lib.binary.io;

import lombok.NonNull;
import net.daporkchop.lib.binary.io.source.ByteSource;
import net.daporkchop.lib.binary.io.source.DataSource;
import net.daporkchop.lib.binary.io.wrapper.WrapperDataAsInput;
import net.daporkchop.lib.binary.io.wrapper.WrapperInputAsData;

import java.io.IOException;
import java.io.InputStream;

/**
 * Extension of {@link DataSource} which can also serve as an {@link InputStream}.
 *
 * @author DaPorkchop_
 */
public interface DataIn extends AutoCloseable, DataSource {
    static DataIn wrap(@NonNull InputStream stream) {
        return wrap(stream, true);
    }

    static DataIn wrap(@NonNull InputStream stream, boolean forwardClose) {
        if (stream instanceof WrapperDataAsInput)   {
            return ((WrapperDataAsInput) stream).delegate();
        } else {
            return new WrapperInputAsData(stream, forwardClose);
        }
    }

    /**
     * Gets this {@link DataIn} instance as a vanilla Java {@link InputStream}. The {@link InputStream} returned
     * by this instance is guaranteed to have access to the same data as this {@link DataIn} instance, and reading
     * N bytes from one of them should have the same effect on the other as if N bytes had been skipped.
     *
     * @return a {@link InputStream} with access to the same data as this stream
     */
    InputStream java();

    @Override
    void close() throws IOException;
}
