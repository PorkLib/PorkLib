/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2018-2020 DaPorkchop_ and contributors
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

package net.daporkchop.lib.binary.oio;

import java.io.IOException;

/**
 * An extension of {@link Appendable} that's better.
 *
 * @author DaPorkchop_
 */
public interface PAppendable extends Appendable {
    /**
     * Appends the given {@link CharSequence}, followed by a platform-dependent newline sequence.
     *
     * @param seq the {@link CharSequence} to append. If {@code null}, then {@code "null"} will be appended.
     * @return this {@link PAppendable} instance
     * @throws IOException if an IO exception occurs you dummy
     * @see #append(CharSequence)
     */
    PAppendable appendLn(CharSequence seq) throws IOException;

    /**
     * Appends the given {@link CharSequence}s, followed by a platform-dependent newline sequence.
     *
     * @param seq1 the first {@link CharSequence} to append. If {@code null}, then {@code "null"} will be appended.
     * @param seq2 the second {@link CharSequence} to append. If {@code null}, then {@code "null"} will be appended.
     * @return this {@link PAppendable} instance
     * @throws IOException if an IO exception occurs you dummy
     * @see #appendLn(CharSequence)
     */
    PAppendable appendLn(CharSequence seq1, CharSequence seq2) throws IOException;

    /**
     * Appends the given {@link CharSequence}s, followed by a platform-dependent newline sequence.
     *
     * @param seq1 the first {@link CharSequence} to append. If {@code null}, then {@code "null"} will be appended.
     * @param seq2 the second {@link CharSequence} to append. If {@code null}, then {@code "null"} will be appended.
     * @param seq3 the third {@link CharSequence} to append. If {@code null}, then {@code "null"} will be appended.
     * @return this {@link PAppendable} instance
     * @throws IOException if an IO exception occurs you dummy
     * @see #appendLn(CharSequence)
     */
    PAppendable appendLn(CharSequence seq1, CharSequence seq2, CharSequence seq3) throws IOException;

    /**
     * Appends the given {@link CharSequence}s, followed by a platform-dependent newline sequence.
     *
     * @param sequences the {@link CharSequence}s to append. If any value is {@code null}, then {@code "null"} will be appended.
     * @return this {@link PAppendable} instance
     * @throws IOException if an IO exception occurs you dummy
     * @see #appendLn(CharSequence)
     */
    PAppendable appendLn(CharSequence... sequences) throws IOException;

    /**
     * Appends a range the given {@link CharSequence}, followed by a platform-dependent newline sequence.
     *
     * @param seq   the {@link CharSequence} to append. If {@code null}, then {@code "null"} will be appended.
     * @param start the index of the first character to append (inclusive)
     * @param end   the index of the last character to append (exclusive)
     * @return this {@link PAppendable} instance
     * @throws IOException               if an IO exception occurs you dummy
     * @throws IndexOutOfBoundsException if the given start or end indices are out of bounds of the given {@link CharSequence}
     * @see #append(CharSequence, int, int)
     */
    PAppendable appendLn(CharSequence seq, int start, int end) throws IOException, IndexOutOfBoundsException;

    /**
     * Appends text formatted as if by {@link String#format(String, Object...)}.
     *
     * @param format the format to apply
     * @param args   the arguments to the formatter
     * @return this {@link PAppendable} instance
     * @throws IOException if an IO exception occurs you dummy
     */
    PAppendable appendFmt(String format, Object... args) throws IOException;
}
