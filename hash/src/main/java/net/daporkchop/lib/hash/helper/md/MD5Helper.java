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

package net.daporkchop.lib.hash.helper.md;

import net.daporkchop.lib.hash.impl.skid.md.MD5Impl;

/**
 * A thread-safe wrapper for the MD5 hashing algorithm
 *
 * @author DaPorkchop_
 */
public class MD5Helper {
    private static final MD5Helper INSTANCE = new MD5Helper();
    private final ThreadLocal<MD5Impl> tl = ThreadLocal.withInitial(MD5Impl::new);

    private MD5Helper() {
    }

    public static byte[] md5(byte[] toHash) {
        return INSTANCE.hash(toHash);
    }

    public static byte[] md5(byte[]... toHash) {
        return INSTANCE.hash(toHash);
    }

    private byte[] hash(byte[] toHash) {
        MD5Impl i = this.tl.get();
        i.update(toHash, 0, toHash.length);
        byte[] hash = i.digest();
        i.reset();
        return hash;
    }

    private byte[] hash(byte[]... toHash) {
        MD5Impl i = this.tl.get();
        for (byte[] b : toHash)
            i.update(b, 0, b.length);
        byte[] hash = i.digest();
        i.reset();
        return hash;
    }
}