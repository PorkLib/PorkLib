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

package example;

import net.daporkchop.lib.common.util.PorkUtil;
import net.daporkchop.lib.math.arrays.grid.Grid2d;
import net.daporkchop.lib.math.interpolation.InterpolationEngine;
import net.daporkchop.lib.math.interpolation.linear.LinearInterpolationEngine;

import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author DaPorkchop_
 */
public class ImageInterpolationExample {
    public static void main(String... args) {
        int size = 17;
        int factor = 32;
        int scaled = (size - 1) * factor;

        int[] orig = new int[size * size];
        for (int i = 0; i < orig.length; i++)   {
            orig[i] = ThreadLocalRandom.current().nextInt();
        }
        InterpolationEngine engine = new LinearInterpolationEngine();
        BufferedImage img = new BufferedImage(scaled, scaled, BufferedImage.TYPE_INT_RGB);
        int[] oneChannel = new int[size * size];
        Grid2d grid = Grid2d.of(oneChannel, size, size);
        for (int i = 0; i < 3; i++) {
            int shift = i << 3;
            for (int j = 0; j < orig.length; j++)   {
                oneChannel[j] = (orig[j] >>> shift) & 0xFF;
            }
            for (int x = scaled - 1; x >= 0; x--)   {
                for (int y = scaled - 1; y >= 0; y--)   {
                    int newVal = engine.getInterpolatedI((double) x / (double) factor, (double) y / (double) factor, grid);
                    img.setRGB(x, y, img.getRGB(x, y) | (newVal << shift));
                }
            }
        }

        PorkUtil.simpleDisplayImage(img);
    }
}