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

package net.daporkchop.lib.crypto.sig.asymmetric.impl;

import net.daporkchop.lib.crypto.key.asymmetric.RSAKeyPair;
import net.daporkchop.lib.crypto.sig.HashTypes;
import net.daporkchop.lib.crypto.sig.asymmetric.AsymmetricSignatureHelper;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.signers.RSADigestSigner;

import java.lang.reflect.Field;
import java.util.Hashtable;

public class RSASignatureHelper extends AsymmetricSignatureHelper<RSAKeyPair, RSADigestSigner> {

    static {
        //little hack to prevent an NPE while signing with RSA using algorithms unknown to bouncycastle
        try {
            Field f = RSADigestSigner.class.getDeclaredField("oidMap");
            f.setAccessible(true);
            Hashtable hashtable = (Hashtable) f.get(null);
            hashtable.put("Tiger", PKCSObjectIdentifiers.digestedData);
            hashtable.put("Whirlpool", PKCSObjectIdentifiers.digestedData);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public RSASignatureHelper(HashTypes hash) {
        super(RSADigestSigner::new, hash);
    }
}