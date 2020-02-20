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

package net.daporkchop.lib.noise.engine;

import lombok.NonNull;
import net.daporkchop.lib.noise.NoiseSource;
import net.daporkchop.lib.random.PRandom;

import static net.daporkchop.lib.math.primitive.PMath.*;

/**
 * @author DaPorkchop_
 */
public final class PerlinNoiseEngine implements NoiseSource {
    private static double fade(double t) {
        return t * t * t * (t * (t * 6.0d - 15.0d) + 10.0d);
    }

    private static double grad(int hash, double x) {
        int h = hash & 15;
        double grad = 1.0f + (h & 7);
        if ((h & 8) == 0) grad = -grad;
        return (grad * x);
    }

    private static double grad(int hash, double x, double y) {
        int h = hash & 7;
        double u = h < 4 ? x : y;
        double v = h < 4 ? y : x;
        return ((h & 1) == 0 ? -u : u) + ((h & 2) == 0 ? -2.0d * v : 2.0d * v);
    }

    private static double grad(int hash, double x, double y, double z) {
        int h = hash & 15;
        double u = h < 8 ? x : y;
        double v = h < 4 ? y : h == 12 || h == 14 ? x : z;
        return ((h & 1) == 0 ? -u : u) + ((h & 2) == 0 ? -v : v);
    }

    private static double grad(int hash, double x, double y, double z, double t) {
        int h = hash & 31;
        double u = h < 24 ? x : y;
        double v = h < 16 ? y : z;
        double w = h < 8 ? z : t;
        return ((h & 1) == 0 ? -u : u) + ((h & 2) == 0 ? -v : v) + ((h & 4) == 0 ? -w : w);
    }
    
    private final byte[] p;
    
    public PerlinNoiseEngine(@NonNull PRandom random)   {
        this.p = new byte[256 << 1];
        for (int i = 0; i < 256; i++)   {
            this.p[i] = (byte) i;
        }
        for (int i = 0; i < 256; i++)   {
            int j = random.next(8);
            byte v = this.p[j];
            this.p[j] = this.p[i];
            this.p[i] = v;
        }
        System.arraycopy(this.p, 0, this.p, 256, 256);
    }
    
    @Override
    public double get(double x) {
        int ix0 = floorI(x); 
        double fx0 = x - ix0;       
        double fx1 = fx0 - 1.0d;
        int ix1 = (ix0 + 1) & 0xFF;
        ix0 = ix0 & 0xFF;    

        double s = fade(fx0);

        double n0 = grad(this.p[ix0] & 0xFF, fx0);
        double n1 = grad(this.p[ix1] & 0xFF, fx1);
        return lerp(s, n0, n1) * 0.25d;
    }

    @Override
    public double get(double x, double y) {
        int ix0 = floorI(x); 
        int iy0 = floorI(y); 
        double fx0 = x - ix0;        
        double fy0 = y - iy0;        
        double fx1 = fx0 - 1.0d;
        double fy1 = fy0 - 1.0d;
        int ix1 = (ix0 + 1) & 0xFF;  
        int iy1 = (iy0 + 1) & 0xFF;
        ix0 = ix0 & 0xFF;
        iy0 = iy0 & 0xFF;

        double t = fade(fy0);
        double s = fade(fx0);

        double nx0 = grad(this.p[ix0 + this.p[iy0] & 0xFF] & 0xFF, fx0, fy0);
        double nx1 = grad(this.p[ix0 + this.p[iy1] & 0xFF] & 0xFF, fx0, fy1);
        double n0 = lerp(t, nx0, nx1);

        nx0 = grad(this.p[ix1 + this.p[iy0] & 0xFF] & 0xFF, fx1, fy0);
        nx1 = grad(this.p[ix1 + this.p[iy1] & 0xFF] & 0xFF, fx1, fy1);
        double n1 = lerp(t, nx0, nx1);

        return lerp(s, n0, n1) * 0.661703888d;
    }

    @Override
    public double get(double x, double y, double z) {
        int ix0 = floorI(x); 
        int iy0 = floorI(y); 
        int iz0 = floorI(z); 
        double fx0 = x - ix0;        
        double fy0 = y - iy0;        
        double fz0 = z - iz0;        
        double fx1 = fx0 - 1.0d;
        double fy1 = fy0 - 1.0d;
        double fz1 = fz0 - 1.0d;
        int ix1 = (ix0 + 1) & 0xFF; 
        int iy1 = (iy0 + 1) & 0xFF;
        int iz1 = (iz0 + 1) & 0xFF;
        ix0 = ix0 & 0xFF;
        iy0 = iy0 & 0xFF;
        iz0 = iz0 & 0xFF;

        double r = fade(fz0);
        double t = fade(fy0);
        double s = fade(fx0);

        double nxy0 = grad(this.p[ix0 + this.p[iy0 + this.p[iz0] & 0xFF] & 0xFF] & 0xFF, fx0, fy0, fz0);
        double nxy1 = grad(this.p[ix0 + this.p[iy0 + this.p[iz1] & 0xFF] & 0xFF] & 0xFF, fx0, fy0, fz1);
        double nx0 = lerp(r, nxy0, nxy1);

        nxy0 = grad(this.p[ix0 + this.p[iy1 + this.p[iz0] & 0xFF] & 0xFF] & 0xFF, fx0, fy1, fz0);
        nxy1 = grad(this.p[ix0 + this.p[iy1 + this.p[iz1] & 0xFF] & 0xFF] & 0xFF, fx0, fy1, fz1);
        double nx1 = lerp(r, nxy0, nxy1);

        double n0 = lerp(t, nx0, nx1);

        nxy0 = grad(this.p[ix1 + this.p[iy0 + this.p[iz0] & 0xFF] & 0xFF] & 0xFF, fx1, fy0, fz0);
        nxy1 = grad(this.p[ix1 + this.p[iy0 + this.p[iz1] & 0xFF] & 0xFF] & 0xFF, fx1, fy0, fz1);
        nx0 = lerp(r, nxy0, nxy1);

        nxy0 = grad(this.p[ix1 + this.p[iy1 + this.p[iz0] & 0xFF] & 0xFF] & 0xFF, fx1, fy1, fz0);
        nxy1 = grad(this.p[ix1 + this.p[iy1 + this.p[iz1] & 0xFF] & 0xFF] & 0xFF, fx1, fy1, fz1);
        nx1 = lerp(r, nxy0, nxy1);

        double n1 = lerp(t, nx0, nx1);

        return lerp(s, n0, n1) * 0.99009901d;
    }
}
