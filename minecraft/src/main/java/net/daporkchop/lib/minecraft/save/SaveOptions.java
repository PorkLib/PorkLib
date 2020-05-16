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

package net.daporkchop.lib.minecraft.save;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.daporkchop.lib.common.misc.Cloneable;
import net.daporkchop.lib.minecraft.util.WriteAccess;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * Options used when opening a {@link Save}.
 *
 * @author DaPorkchop_
 */
@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class SaveOptions implements Cloneable<SaveOptions> {
    /**
     * The write access level that the save will be opened with.
     * <p>
     * Defaults to {@link WriteAccess#WRITE_REQUIRED}.
     */
    @NonNull
    protected WriteAccess access = WriteAccess.WRITE_REQUIRED;

    /**
     * The {@link Executor} that will be used for executing async I/O operations.
     * <p>
     * Defaults to {@link ForkJoinPool#commonPool()}.
     */
    @NonNull
    protected Executor ioExecutor = ForkJoinPool.commonPool();

    @Override
    public SaveOptions clone() {
        return new SaveOptions()
                .access(this.access)
                .ioExecutor(this.ioExecutor);
    }
}