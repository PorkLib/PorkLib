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

package net.daporkchop.lib.compression.zstd.options;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import net.daporkchop.lib.compression.option.DeflaterOptions;
import net.daporkchop.lib.compression.zstd.Zstd;
import net.daporkchop.lib.compression.zstd.ZstdProvider;
import net.daporkchop.lib.compression.zstd.ZstdStrategy;

import static net.daporkchop.lib.common.util.PValidation.*;

/**
 * @author DaPorkchop_
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Accessors(fluent = true)
public final class ZstdDeflaterOptions implements DeflaterOptions<ZstdDeflaterOptions, ZstdProvider> {
    protected final ZstdProvider provider;

    protected final ZstdStrategy strategy;

    protected final int level;

    protected final int workers;

    public ZstdDeflaterOptions(@NonNull ZstdProvider provider) {
        this(provider, ZstdStrategy.DEFAULT, Zstd.LEVEL_DEFAULT, 0);
    }

    public ZstdDeflaterOptions withStrategy(@NonNull ZstdStrategy strategy) {
        if (strategy == this.strategy) {
            return this;
        }
        //return new ZstdDeflaterOptions(this.provider, strategy, this.level, this.workers);
        throw new UnsupportedOperationException("strategy");
    }

    public ZstdDeflaterOptions withLevel(int level) {
        if (Zstd.checkLevel(level) == this.level) {
            return this;
        }
        return new ZstdDeflaterOptions(this.provider, this.strategy, level, this.workers);
    }

    public ZstdDeflaterOptions withWorkers(int workers) {
        notNegative(workers, "workers");
        if (workers == this.workers) {
            return this;
        }
        //return new ZstdDeflaterOptions(this.provider, this.strategy, this.level, workers);
        throw new UnsupportedOperationException("workers");
    }
}