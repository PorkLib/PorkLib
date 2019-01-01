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

import lombok.NonNull;
import net.daporkchop.lib.encoding.compression.Compression;
import net.daporkchop.lib.nbt.NBTInputStream;
import net.daporkchop.lib.nbt.NBTOutputStream;
import net.daporkchop.lib.nbt.tag.Tag;
import net.daporkchop.lib.nbt.tag.notch.CompoundTag;
import net.daporkchop.lib.nbt.tag.notch.ListTag;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.Test;

import java.io.*;

/**
 * @author DaPorkchop_
 */
public class NBTTest {
    public static void printTagRecursive(@NonNull Tag tag, int depth) {
        if (depth == 0) {
            System.out.printf("CompoundTag \"%s\": %d children\n", tag.getName(), tag.getAsCompoundTag().getContents().size());
            tag.getAsCompoundTag().forEach(subTag -> printTagRecursive(subTag, 2));
            return;
        }
        System.out.printf("%s%s\n", space(depth), tag);
        if (tag instanceof CompoundTag) {
            tag.getAsCompoundTag().forEach(subTag -> printTagRecursive(subTag, depth + 2));
        } else if (tag instanceof ListTag) {
            tag.<ListTag<? extends Tag>>getAs().forEach(subTag -> printTagRecursive(subTag, depth + 2));
        }
    }

    public static String space(int count) {
        char[] c = new char[count];
        for (int i = count - 1; i >= 0; i--) {
            c[i] = ' ';
        }
        return new String(c);
    }

    @Test
    public void testWriting() throws IOException {
        byte[] original_uncompressed;
        try (InputStream is = NBTTest.class.getResourceAsStream("bigtest.nbt")) {
            byte[] b = IOUtils.toByteArray(is);
            original_uncompressed = Compression.GZIP_NORMAL.inflate(b);
        }
        CompoundTag tag;
        try (NBTInputStream in = new NBTInputStream(new ByteArrayInputStream(original_uncompressed))) {
            tag = in.readTag();
        }
        byte[] written;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             NBTOutputStream out = new NBTOutputStream(baos)) {
            out.writeTag(tag);
            out.flush();
            written = baos.toByteArray();
        }
        try (NBTInputStream in = new NBTInputStream(new ByteArrayInputStream(written))) {
            tag = in.readTag();
        }
        printTagRecursive(tag, 0);
        File file = new File("test_out/written.nbt");
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (!parent.exists() && !parent.mkdirs()) {
                throw new IllegalStateException(String.format("Couldn't create directory: %s", parent.getAbsolutePath()));
            } else if (!file.createNewFile()) {
                throw new IllegalStateException(String.format("Couldn't create file: %s", file.getAbsolutePath()));
            }
        }
        try (NBTOutputStream out = new NBTOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            out.writeTag(tag);
        }
    }

    @Test
    public void testHelloWorld() throws IOException {
        try (NBTInputStream in = new NBTInputStream(NBTTest.class.getResourceAsStream("hello_world.nbt"))) {
            CompoundTag tag = in.readTag();
            printTagRecursive(tag, 0);
        }
    }

    @Test
    public void testBig() throws IOException {
        try (NBTInputStream in = new NBTInputStream(NBTTest.class.getResourceAsStream("bigtest.nbt"), Compression.GZIP_NORMAL)) {
            CompoundTag tag = in.readTag();
            printTagRecursive(tag, 0);
        }
    }
}
