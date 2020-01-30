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

package net.daporkchop.lib.graphics.util;

import lombok.Getter;
import lombok.NonNull;
import net.daporkchop.lib.graphics.bitmap.PBitmap;
import net.daporkchop.lib.graphics.bitmap.PIcon;
import net.daporkchop.lib.graphics.bitmap.PImage;
import net.daporkchop.lib.graphics.interpolation.ImageInterpolator;
import net.daporkchop.lib.math.interpolation.Interpolation;
import net.daporkchop.lib.math.interpolation.LinearInterpolation;

import java.util.Arrays;

/**
 * A thumbnail is a collection of square icons, with the same contents at different scales.
 *
 * @author DaPorkchop_
 */
public final class Thumbnail {
    protected final int[] sizes;
    protected final PBitmap[] icons;
    @Getter
    protected boolean baked = false;

    public Thumbnail(@NonNull int... sizes) {
        this.sizes = sizes = Arrays.stream(sizes)
                .distinct()
                .sorted()
                .toArray();
        this.icons = new PBitmap[sizes.length];
        if (sizes.length == 0) {
            this.baked = true;
        }
    }

    public Thumbnail bake() {
        return this.bake(new ImageInterpolator(LinearInterpolation.instance()));
    }

    public Thumbnail bake(@NonNull Interpolation engine) {
        return this.bake(new ImageInterpolator(engine));
    }

    public Thumbnail bake(@NonNull ImageInterpolator interpolator) {
        if (!this.baked) {
            PBitmap highestRes = null;
            for (int i = this.sizes.length - 1; i >= 0; i--) {
                if (this.icons[i] != null) {
                    highestRes = this.icons[i];
                    break;
                }
            }
            if (highestRes == null) {
                throw new IllegalStateException();
            }
            for (int i = this.sizes.length - 1; i >= 0; i--) {
                if (this.icons[i] == null) {
                    this.icons[i] = interpolator.interp(highestRes, this.sizes[i], this.sizes[i]).immutableSnapshot();
                }
            }
            this.baked = true;
        }
        return this;
    }

    public Thumbnail submit(@NonNull PBitmap icon)    {
        if (icon.empty() || icon.width() != icon.height())  {
            throw new IllegalArgumentException("Icon is not a square!");
        }
        if (!this.baked)    {
            for (int i = this.sizes.length - 1; i >= 0; i--)    {
                if (this.sizes[i] == icon.width())   {
                    this.icons[i] = icon instanceof PImage ? ((PImage) icon).immutableSnapshot() : icon;
                    return this;
                }
            }

            throw new IllegalArgumentException(String.format("Icon with size %d doesn't match any of the thumbnail resolutions!", icon.width()));
        }
        return this;
    }

    public PBitmap[] getIcons() {
        if (this.baked) {
            return this.icons.clone();
        } else {
            throw new IllegalStateException("Not baked!");
        }
    }
}
