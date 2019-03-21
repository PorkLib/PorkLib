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

package net.daporkchop.lib.collections;

import lombok.NonNull;
import net.daporkchop.lib.collections.util.exception.IterationCompleteException;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A simplification of {@link java.util.Iterator}.
 * <p>
 * Implementations of this class are expected to be thread-safe at a minimum, optionally supporting full concurrency.
 *
 * @param <V> the type to be used as a value
 * @author DaPorkchop_
 */
public interface PIterator<V> {
    /**
     * Checks whether or not this iterator has values remaining to be read.
     *
     * @return whether or not this iterator has values remaining to be read
     */
    boolean hasNext();

    /**
     * Increments the iterator's position, and returns the next value.
     * <p>
     * If no values remain to be iterated (i.e. {@link #hasNext()} returns {@code false}), an {@link IterationCompleteException}
     * will be thrown.
     *
     * @return the next value
     * @throws IterationCompleteException if the iterator is complete
     */
    V next();

    /**
     * Gets the iterator's current value without incrementing it.
     *
     * @return the current value
     * @throws IterationCompleteException if the iterator is complete
     */
    V peek();

    /**
     * Removes the value last returned by this iterator.
     * <p>
     * May only be invoked once per call to {@link #next()}.
     */
    void remove();

    /**
     * Gets the next value in the iterator, or {@code null} if none remain.
     *
     * @return the next value in the iterator, or {@code null} if none remain
     */
    default V nextOrNull() {
        synchronized (this) {
            return this.hasNext() ? this.next() : null;
        }
    }

    /**
     * Gets the next value in the iterator, or throw an exception if none remain.
     *
     * @param exceptionSupplier an instance of {@link Supplier} that will provide an exception instance to be thrown
     * @param <E>               the exception type
     * @return the next value in the iterator
     * @throws E if no values remain
     */
    default <E extends Throwable> V nextOrThrow(@NonNull Supplier<E> exceptionSupplier) throws E {
        synchronized (this) {
            if (this.hasNext()) {
                return this.next();
            } else {
                throw exceptionSupplier.get();
            }
        }
    }

    /**
     * Replaces the current value in the iterator
     *
     * @param value the new value
     * @throws UnsupportedOperationException if this iterator doesn't support setting values
     */
    default void set(V value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Replaces the current value in the iterator, by using a function to compute the next one
     *
     * @param mappingFunction a function that will compute the new value based on the old one
     * @throws UnsupportedOperationException if this iterator doesn't support setting values
     */
    default void recompute(@NonNull Function<V, V> mappingFunction) {
        this.set(mappingFunction.apply(this.peek()));
    }

    /**
     * Checks if this iterator supports setting values
     *
     * @return whether or not this iterator supports setting values
     */
    default boolean setSupported() {
        return false;
    }
}