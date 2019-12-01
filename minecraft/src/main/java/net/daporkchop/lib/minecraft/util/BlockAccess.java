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

package net.daporkchop.lib.minecraft.util;

/**
 * A type which allows accessing block information at specific coordinates.
 *
 * @author DaPorkchop_
 */
public interface BlockAccess {
    int getBlockId(int x, int y, int z);

    int getBlockMeta(int x, int y, int z);

    int getBlockLight(int x, int y, int z);

    int getSkyLight(int x, int y, int z);

    void setBlockId(int x, int y, int z, int id);

    void setBlockMeta(int x, int y, int z, int meta);

    void setBlockLight(int x, int y, int z, int level);

    void setSkyLight(int x, int y, int z, int level);

    int getHighestBlock(int x, int z);

    /**
     * @return the minimum X coordinate (inclusive)
     */
    int minX();

    /**
     * @return the minimum Y coordinate (inclusive)
     */
    int minY();

    /**
     * @return the minimum Z coordinate (inclusive)
     */
    int minZ();

    /**
     * @return the maximum X coordinate (exclusive)
     */
    int maxX();

    /**
     * @return the maximum Y coordinate (exclusive)
     */
    int maxY();

    /**
     * @return the maximum Z coordinate (exclusive)
     */
    int maxZ();
}