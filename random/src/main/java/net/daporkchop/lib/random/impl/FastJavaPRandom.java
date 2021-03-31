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

package net.daporkchop.lib.random.impl;

import net.daporkchop.lib.random.PRandom;

import java.util.concurrent.atomic.AtomicLong;

/**
 * A fast implementation of {@link PRandom} based on Java's {@link java.util.Random}.
 * <p>
 * This is NOT thread-safe. Attempting to share an instance of this class among multiple threads is likely to result in duplicate values
 * being returned to multiple threads.
 *
 * @author DaPorkchop_
 */
public final class FastJavaPRandom extends AbstractFastPRandom {
    private static final long multiplier = 0x5DEECE66DL;
    private static final long addend     = 0xBL;
    private static final long mask       = (1L << 48) - 1;

    private static final AtomicLong seedUniquifier = new AtomicLong(8682522807148012L);

    private static long initialScramble(long seed) {
        return (seed ^ multiplier) & mask;
    }

    private long seed;

    /**
     * Creates a new {@link FastJavaPRandom} instance using a seed based on the current time.
     */
    public FastJavaPRandom() {
        this(seedUniquifier.updateAndGet(val -> val * 181783497276652981L) ^ System.nanoTime());
    }

    /**
     * Creates a new {@link FastJavaPRandom} instance using the given seed.
     *
     * @param seed the seed to use
     */
    public FastJavaPRandom(long seed) {
        this.seed = initialScramble(seed);
    }

    @Override
    public int nextInt() {
        return (int) (this.seed = (this.seed * multiplier + addend) & mask);
    }

    @Override
    public int next(int bits) {
        return (int) ((this.seed = (this.seed * multiplier + addend) & mask) >>> (48L - bits));
    }

    @Override
    public long nextLong() {
        return (((long) this.nextInt()) << 32L) | (long) this.nextInt();
    }

    @Override
    public long next(long bits) {
        if (bits <= 32L)    {
            return (this.seed = (this.seed * multiplier + addend) & mask) >>> (48L - bits);
        } else {
            return this.nextLong() >>> (bits ^ 0x3F);
        }
    }

    @Override
    public void setSeed(long seed) {
        this.seed = initialScramble(seed);
    }
}
