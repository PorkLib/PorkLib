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

package net.daporkchop.lib.primitive.map.tree;

import net.daporkchop.lib.primitive.lambda.consumer.FloatConsumer;
import net.daporkchop.lib.primitive.lambda.consumer.IntegerConsumer;
import net.daporkchop.lib.primitive.lambda.consumer.bi.FloatIntegerConsumer;
import net.daporkchop.lib.primitive.iterator.FloatIterator;
import net.daporkchop.lib.primitive.iterator.IntegerIterator;
import net.daporkchop.lib.primitive.iterator.bi.FloatIntegerIterator;
import net.daporkchop.lib.primitive.map.FloatIntegerMap;

import java.util.ConcurrentModificationException;

import lombok.*;

/**
 * A tree map, using a key type of float and a value type of int.
 * This works similarly to a hash map, except rather than storing indexes in a giant array that's indexed by hash,
 * it stores indexes in a 4-dimensional array of hashes which in turn point to the index in the main array.
 * Although this map WORKS, it's not really particularly optimized. I'd advise using {@link net.daporkchop.lib.primitive.map.tree.index.FloatIntegerIndexedTreeMap},
 * as it works in nearly the same manner without being nearly as memory-inefficient.
 * <p>
 * DO NOT EDIT BY HAND! THIS FILE IS SCRIPT-GENERATED!
 *
 * @author DaPorkchop_
 */
public class FloatIntegerTreeMap implements FloatIntegerMap    {
    protected float[] keys;
    protected int[] values;

    /**
     * A mapping of keys hashes to indexes
     * Since an int is signed anyway, we use the sign bit as a flag for whether the index is empty or full
     * < 0 means a value is present, >= 0 means there is no value for the hash
     */
    protected int[][][][] states = new int[256][][][];
    protected int size = 0;

    /**
     * The length of the keys/value arrays
     */
    protected int len = 64;

    protected int indexCounter = 0;

    /**
     * Stores the total number of removed entries since the last cleanup
     */
    protected int removedCount = 0;

    public FloatIntegerTreeMap()    {
        this.clear();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int get(float key)    {
        int hash = hashKey(key);
        if (!containsKeyHash(hash)) return 0; //no mapping
        return values[(getState(hash) + 1) & 0x7fffffff]; //discard sign bit
    }

    @Override
    @SuppressWarnings("unchecked")
    public int put(float key, int value)   {
        int hash = hashKey(key);
        if (containsKeyHash(hash)) {
            int state = getState(hash) * -1 - 1; //discard sign bit
            keys[state] = key;
            int old = values[state];
            values[state] = value;
            return old;
        } else {
            //no mapping
            checkResize(false);
            size++;
            removedCount--; //decrement this to prevent things breaking
            int state = (indexCounter++ + 1) * -1;
            mark(hash, state);
            state = state * -1 - 1; //discard sign bit
            keys[state] = key;
            values[state] = value;
            return 0;
        }
    }

    @Override
    public int remove(float key)    {
        return remove(key, true);
    }

    @SuppressWarnings("unchecked")
    protected int remove(float key, boolean shrink) {
        int hash = hashKey(key);
        if (!containsKeyHash(hash)) return 0; //no mapping
        int state = getState(hash);
        mark(hash, 0);
        size--;
        removedCount++;
        int val = values[state * -1 - 1];
        if (shrink) checkResize(true);
        return val;
    }

    @Override
    public boolean containsKey(float key)    {
        return containsKeyHash(hashKey(key));
    }

    @Override
    public boolean containsValue(int value)    {
        //TODO: faster way of doing this, maybe maintain an index of valid keys?
        //recurse through all state arrays to see if value at index matches
        for (int w = 0; w < 256; w++)   {
            int[][][] a = states[w];
            if (a == null) continue;
            for (int x = 0; x < 256; x++)   {
                int[][] b = a[x];
                if (b == null) continue;
                for (int y = 0; y < 256; y++)   {
                    int[] c = b[y];
                    if (c == null) continue;
                    for (int z = 0; z < 256; z++)   {
                        int d = c[z];
                        if (d < 0 && values[d * -1 - 1] == value) return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.len = 64;
        this.removedCount = 0;
        this.keys = new float[len];
        this.values = new int[len];
        this.states = new int[256][][][];
    }

    @Override
    public int getSize()    {
        return size;
    }

    @Override
    public void forEachKey(@NonNull FloatConsumer consumer)   {
        if (consumer == null) throw new IllegalArgumentException("Consumer may not be null!");
        //recurse through all state arrays to see if value at index matches
        for (int w = 0; w < 256; w++)   {
            int[][][] a = states[w];
            if (a == null) continue;
            for (int x = 0; x < 256; x++)   {
                int[][] b = a[x];
                if (b == null) continue;
                for (int y = 0; y < 256; y++)   {
                    int[] c = b[y];
                    if (c == null) continue;
                    for (int z = 0; z < 256; z++)   {
                        int d = c[z];
                        if (d < 0) consumer.accept( keys[d * -1 - 1]);
                    }
                }
            }
        }
    }

    @Override
    public void forEachValue(@NonNull IntegerConsumer consumer)   {
        if (consumer == null) throw new IllegalArgumentException("Consumer may not be null!");
        //recurse through all state arrays to see if value at index matches
        for (int w = 0; w < 256; w++)   {
            int[][][] a = states[w];
            if (a == null) continue;
            for (int x = 0; x < 256; x++)   {
                int[][] b = a[x];
                if (b == null) continue;
                for (int y = 0; y < 256; y++)   {
                    int[] c = b[y];
                    if (c == null) continue;
                    for (int z = 0; z < 256; z++)   {
                        int d = c[z];
                        if (d < 0) consumer.accept( values[d * -1 - 1]);
                    }
                }
            }
        }
    }

    @Override
    public void forEachEntry(@NonNull FloatIntegerConsumer consumer)   {
        if (consumer == null) throw new IllegalArgumentException("Consumer may not be null!");
        //recurse through all state arrays to see if value at index matches
        for (int w = 0; w < 256; w++)   {
            int[][][] a = states[w];
            if (a == null) continue;
            for (int x = 0; x < 256; x++)   {
                int[][] b = a[x];
                if (b == null) continue;
                for (int y = 0; y < 256; y++)   {
                    int[] c = b[y];
                    if (c == null) continue;
                    for (int z = 0; z < 256; z++)   {
                        int d = c[z];
                        if (d < 0)  {
                            d = d * -1 - 1;
                            consumer.accept( keys[d], values[d]);
                        }
                    }
                }
            }
        }
    }

    @Override
    public FloatIterator keyIterator()   {
        //see {@link net.daporkchop.lib.primitive.map.tree.index.FloatIntegerIndexedTreeMap} for an implementation of this method
        return null;
    }

    @Override
    public IntegerIterator valueIterator()   {
        //see {@link net.daporkchop.lib.primitive.map.tree.index.FloatIntegerIndexedTreeMap} for an implementation of this method
        return null;
    }

    @Override
    public FloatIntegerIterator entryIterator()   {
        //see {@link net.daporkchop.lib.primitive.map.tree.index.FloatIntegerIndexedTreeMap} for an implementation of this method
        return null;
    }

    protected boolean containsKeyHash(int hash) {
        return getState(hash) < 0;
    }

    protected boolean containsState(int state)  {
        return state < 0;
    }

    protected void checkResize(boolean shrink)    {
        if (indexCounter + 1 == len) {
            if (removedCount >= len >> 8)    {
                //if one 8th or more of the contained elements have been removed, then compact the array instead of shrinking it
                doCompact();
            } else {
                doResize(len, len << 1);
            }
        } else if (shrink && size < len >> 1)    {
            //if down to half size, shrink the darn thing!
            doResize(len, len >> 1);
        }
    }

    protected void doCompact()  {
        doResize(len, len);
    }

    @SuppressWarnings("unchecked")
    protected void doResize(int oldSize, int newSize) {
        System.out.println((oldSize > newSize ? "Shrinking" : "Growing") + "! " + oldSize + " " + newSize);
        indexCounter = 0;
        removedCount = 0;
        len = newSize;
        float[] newKeys = new float[len];
        int[] newValues = new int[len];
        int[][][][] newStates = new int[256][][][];
        //AtomicInteger m = new AtomicInteger(0);
        forEachEntry((key, value) -> {
            int hash = hashKey(key);
            int index = indexCounter++;
            newKeys[index] = key;
            newValues[index] = value;

            //finally, mark state
            int[][][] a = newStates[hash & 0xFF];
            if (a == null) a = newStates[hash & 0xFF] = new int[256][][];
            int[][] b = a[(hash >> 8) & 0xFF];
            if (b == null) b = a[(hash >> 8) & 0xFF] = new int[256][];
            int[] c = b[(hash >> 16) & 0xFF];
            if (c == null) c = b[(hash >> 16) & 0xFF] = new int[256];
            //if (c[(hash >> 24) & 0xFF] != 0)
            //    System.out.println("Overwriting value at index " + index  + ": " + c[(hash >> 24) & 0xFF]);
            c[(hash >> 24) & 0xFF] = (index + 1) * -1;
            //if (c[(hash >> 24) & 0xFF] == -1)
            //    System.out.println("Wrote -1 at " + index);
            //m.getAndIncrement();
        });
        //System.out.println("Copied " + m.get() + " elements, next index: " + indexCounter);
        keys = newKeys;
        values = newValues;
        states = newStates;
    }

    protected void mark(int hash, int state)  {
        int[][][] a = states[hash & 0xFF];
        if (a == null) a = states[hash & 0xFF] = new int[256][][];
        int[][] b = a[(hash >> 8) & 0xFF];
        if (b == null) b = a[(hash >> 8) & 0xFF] = new int[256][];
        int[] c = b[(hash >> 16) & 0xFF];
        if (c == null) c = b[(hash >> 16) & 0xFF] = new int[256];
        c[(hash >> 24) & 0xFF] = state;
    }

    protected int getState(int hash)  {
        int[][][] a = states[hash & 0xFF];
        if (a == null) return 0;
        int[][] b = a[(hash >> 8) & 0xFF];
        if (b == null) return 0;
        int[] c = b[(hash >> 16) & 0xFF];
        if (c == null) return 0;
        return c[(hash >> 24) & 0xFF];
    }
}