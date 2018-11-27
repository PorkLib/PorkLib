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

package net.daporkchop.lib.db.object.key.impl;

import lombok.Getter;
import net.daporkchop.lib.db.object.key.KeyHasher;

/**
 * Uses a constant-length byte array directly as a key.
 * <p>
 * Can get key back from hash
 *
 * @author DaPorkchop_
 */
public class RawBytesKeyHasher extends KeyHasher<byte[]> {
    @Getter
    private final int length;

    public RawBytesKeyHasher(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Invalid key size! (must be more than or equal to 0)");
        }
        this.length = length;
    }

    @Override
    public boolean canGetKeyFromHash() {
        return true;
    }

    @Override
    public int getKeyLength() {
        return this.length;
    }

    @Override
    public void hash(byte[] key, byte[] hash) {
        if (key.length != this.length) {
            throw new IllegalArgumentException("Invalid key length! (must be " + this.length + ", given: " + key.length + ')');
        }
        System.arraycopy(key, 0, hash, 0, this.length);
    }

    @Override
    public byte[] getKeyFromHash(byte[] hash) {
        return hash.clone();
    }
}