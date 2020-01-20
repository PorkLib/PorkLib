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

package net.daporkchop.lib.http.util;

import io.netty.util.internal.StringUtil;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.daporkchop.lib.common.pool.handle.Handle;
import net.daporkchop.lib.common.util.PorkUtil;
import net.daporkchop.lib.encoding.Hexadecimal;
import net.daporkchop.lib.http.util.exception.GenericHttpException;
import net.daporkchop.lib.http.util.exception.HttpException;

import java.io.IOException;
import java.util.BitSet;

/**
 * Because the official Java URLEncoder is bad.
 *
 * @author DaPorkchop_
 */
@UtilityClass
public class URLEncoding {
    protected final BitSet OK_CHARS = new BitSet(128);

    static {
        OK_CHARS.set('a', 'z' + 1);
        OK_CHARS.set('A', 'Z' + 1);
        OK_CHARS.set('0', '9' + 1);
        OK_CHARS.set('*');
        OK_CHARS.set('-');
        OK_CHARS.set('.');
        OK_CHARS.set('_');
    }

    public String encode(@NonNull CharSequence text) {
        return encode(text, false);
    }

    public String encode(@NonNull CharSequence text, boolean preserveSlash) {
        try (Handle<StringBuilder> handle = PorkUtil.STRINGBUILDER_POOL.get())  {
            StringBuilder builder = handle.value();
            builder.setLength(0);
            encode(builder, text, preserveSlash);
            return builder.toString();
        } catch (IOException e) {
            //can't happen
            throw new IllegalStateException(e);
        }
    }

    public CharSequence encodeToCharSequence(@NonNull CharSequence text) {
        return encode(text, false);
    }

    public CharSequence encodeToCharSequence(@NonNull CharSequence text, boolean preserveSlash) {
        try {
            StringBuilder builder = new StringBuilder(text.length());
            encode(builder, text, preserveSlash);
            return builder;
        } catch (IOException e) {
            //can't happen
            throw new IllegalStateException(e);
        }
    }

    public void encode(@NonNull Appendable dst, @NonNull CharSequence text) throws IOException {
        encode(dst, text, false);
    }

    public void encode(@NonNull Appendable dst, @NonNull CharSequence text, boolean preserveSlash) throws IOException {
        //all the ternary operators should be optimized away by the JIT compiler
        // (i'd assume that it makes two copies of the method: one for arr == null and one for arr != null)

        final char[] arr = PorkUtil.tryUnwrap(text);
        for (int i = 0, length = arr != null ? arr.length : text.length(); i < length; i++) {
            char c = arr != null ? arr[i] : text.charAt(i);
            if (OK_CHARS.get(c) || (preserveSlash && c == '/')) {
                dst.append(c);
            } else if (c == ' ') {
                dst.append('+');
            } else {
                dst.append('%');
                if (c < 0x80) {
                    Hexadecimal.encode(dst, (byte) c);
                } else if (c < 0x800) {
                    Hexadecimal.encode(dst, (byte) (0xC0 | (c >> 6)));
                    Hexadecimal.encode(dst, (byte) (0x80 | (c & 0x3F)));
                } else if (StringUtil.isSurrogate(c)) {
                    if (Character.isHighSurrogate(c) && ++i < length) {
                        char c2 = arr != null ? arr[i] : text.charAt(i);
                        if (Character.isLowSurrogate(c2)) {
                            int codePoint = Character.toCodePoint(c, c2);
                            Hexadecimal.encode(dst, (byte) (0xF0 | (codePoint >> 18)));
                            Hexadecimal.encode(dst, (byte) (0x80 | ((codePoint >> 12) & 0x3F)));
                            Hexadecimal.encode(dst, (byte) (0x80 | ((codePoint >> 6) & 0x3F)));
                            Hexadecimal.encode(dst, (byte) (0x80 | (codePoint & 0x3F)));
                        } else {
                            Hexadecimal.encode(dst, (byte) '?');
                            i--;
                        }
                    } else {
                        Hexadecimal.encode(dst, (byte) '?');
                    }
                }
            }
        }
    }

    public String decode(@NonNull CharSequence text) throws HttpException {
        try (Handle<StringBuilder> handle = PorkUtil.STRINGBUILDER_POOL.get())  {
            StringBuilder builder = handle.value();
            builder.setLength(0);
            decode(builder, text);
            return builder.toString();
        }
    }

    public CharSequence decodeToCharSequence(@NonNull CharSequence text) throws HttpException {
        StringBuilder builder = new StringBuilder();
        decode(builder, text);
        return builder;
    }

    public void decode(@NonNull StringBuilder to, @NonNull CharSequence text) throws HttpException {
        //all the ternary operators should be optimized away by the JIT compiler
        // (i'd assume that it makes two copies of the method: one for arr == null and one for arr != null)

        final char[] arr = PorkUtil.tryUnwrap(text);
        for (int i = 0, length = arr != null ? arr.length : text.length(); i < length; i++) {
            char c = arr != null ? arr[i] : text.charAt(i);
            if (c == '%') {
                i += 2;
                int b;
                if (i >= length || (b = Hexadecimal.decodeUnsigned(arr != null ? arr[i - 1] : text.charAt(i - 1), arr != null ? arr[i] : text.charAt(i))) < 0) {
                    throw StatusCodes.Bad_Request.exception();
                }
                if ((b & 0xE0) == 0xC0) {
                    i += 2;
                    int b2;
                    if (i >= length || (b2 = Hexadecimal.decodeUnsigned(arr != null ? arr[i - 1] : text.charAt(i - 1), arr != null ? arr[i] : text.charAt(i))) < 0) {
                        throw StatusCodes.Bad_Request.exception();
                    }
                    to.append((char) (((b & 0x1F) << 6) | (b2 & 0x3F)));
                } else if ((b & 0xF0) == 0xF0) {
                    i += 6;
                    int b2, b3, b4;
                    if (i >= length
                            || (b2 = Hexadecimal.decodeUnsigned(arr != null ? arr[i - 5] : text.charAt(i - 5), arr != null ? arr[i - 4] : text.charAt(i - 4))) < 0
                            || (b3 = Hexadecimal.decodeUnsigned(arr != null ? arr[i - 3] : text.charAt(i - 3), arr != null ? arr[i - 2] : text.charAt(i - 2))) < 0
                            || (b4 = Hexadecimal.decodeUnsigned(arr != null ? arr[i - 1] : text.charAt(i - 1), arr != null ? arr[i] : text.charAt(i))) < 0) {
                        throw StatusCodes.Bad_Request.exception();
                    }
                    to.appendCodePoint(((b & 0xF) << 18) | ((b2 & 0x3F) << 12) | ((b3 & 0x3F) << 6) | (b4 & 0x3F));
                } else {
                    to.append((char) b);
                }
            } else if (c == '+') {
                to.append(' ');
            } else {
                to.append(c);
            }
        }
    }
}