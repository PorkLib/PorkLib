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

package example.graphics;

import net.daporkchop.lib.common.util.PorkUtil;
import net.daporkchop.lib.graphics.bitmap.PBitmap;
import net.daporkchop.lib.graphics.bitmap.PImage;
import net.daporkchop.lib.graphics.bitmap.image.DirectImageARGB;
import net.daporkchop.lib.graphics.color.ColorFormat;
import net.daporkchop.lib.graphics.interpolation.ImageInterpolator;
import net.daporkchop.lib.graphics.util.Thumbnail;
import net.daporkchop.lib.math.interpolation.CubicInterpolation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

/**
 * @author DaPorkchop_
 */
public class TestingDisplayingOfImages {
    public static void main(String... args) throws InterruptedException, IOException {
        int size = 20;
        for (ColorFormat format : Stream.of(
                null
                //, ColorFormat.ARGB
                , ColorFormat.RGB
                //, ColorFormat.ABW
                //, ColorFormat.BW
        ).filter(Objects::nonNull).toArray(ColorFormat[]::new)) {
            PImage image = new DirectImageARGB(size, size);
            for (int x = size - 1; x >= 0; x--) {
                for (int y = size - 1; y >= 0; y--) {
                    image.setRaw(x, y, ThreadLocalRandom.current().nextLong() & ((1 << format.encodedBits()) - 1));
                }
            }

            image.renderer().fillPolygon(
                    new int[] {
                            5, 10, 5, 0
                    },
                    new int[] {
                            0, 5, 10, 5
                    },
                    4,
                    0xFFFF0000
            );

            BufferedImage buffered;
            if (false)  {
                buffered = image.asBufferedImage();
            } else {
                buffered = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_ARGB);
                for (int x = 0; x < image.width(); x++) {
                    for (int y = 0; y < image.height(); y++)    {
                        buffered.setRGB(x, y, image.getARGB(x, y));
                    }
                }
            }
            if (!ImageIO.write(buffered, "png", new File(String.format("./test_out/%s.png", PorkUtil.className(format))))) {
                throw new IllegalStateException("Didn't write image!");
            }

            /*ImageInterpolator interpolator = new ImageInterpolator(CubicInterpolation.instance());
            image = interpolator.interp(image, 32.0d);

            Thumbnail thumbnail = new Thumbnail(64, 32, 16, 8, image.getWidth()).submit(image).bake();

            PorkUtil.simpleDisplayImage(true, Arrays.stream(thumbnail.getIcons()).map(PBitmap::asBufferedImage).toArray(BufferedImage[]::new));
            */
        }
    }
}
