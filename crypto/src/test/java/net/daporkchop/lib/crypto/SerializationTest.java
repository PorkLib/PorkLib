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

package net.daporkchop.lib.crypto;

import net.daporkchop.lib.binary.stream.DataIn;
import net.daporkchop.lib.binary.stream.DataOut;
import net.daporkchop.lib.crypto.key.EllipticCurveKeyPair;
import net.daporkchop.lib.crypto.key.KeySerialization;
import net.daporkchop.lib.crypto.keygen.KeyGen;
import net.daporkchop.lib.crypto.sig.ec.CurveType;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SerializationTest {
    @Test
    public void testEC() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (CurveType type : CurveType.values()) {
            EllipticCurveKeyPair keyPair = KeyGen.gen(type);
            KeySerialization.encodeEC(DataOut.wrap(baos), keyPair);
            EllipticCurveKeyPair decoded = KeySerialization.decodeEC(DataIn.wrap(new ByteArrayInputStream(baos.toByteArray())));
            baos.reset();
            if (!keyPair.equals(decoded)) {
                throw new IllegalStateException(String.format("Decoded key pair was different from original on curve type %s", type.name));
            }
            System.out.printf("Successful test of %s\n", type.name);
        }
    }
}
