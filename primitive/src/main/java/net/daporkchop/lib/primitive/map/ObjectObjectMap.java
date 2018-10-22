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

package net.daporkchop.lib.primitive.map;

import net.daporkchop.lib.primitive.lambda.consumer.ObjectConsumer;
import net.daporkchop.lib.primitive.lambda.consumer.ObjectConsumer;
import net.daporkchop.lib.primitive.lambda.consumer.bi.ObjectObjectConsumer;
import net.daporkchop.lib.primitive.lambda.function.ObjectToObjectFunction;
import net.daporkchop.lib.primitive.iterator.ObjectIterator;
import net.daporkchop.lib.primitive.iterator.ObjectIterator;
import net.daporkchop.lib.primitive.iterator.bi.ObjectObjectIterator;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.*;

/**
 * A map, using a key type of K and a value type of V.
 * <p>
 * DO NOT EDIT BY HAND! THIS FILE IS SCRIPT-GENERATED!
 *
 * @author DaPorkchop_
 */
public interface ObjectObjectMap<K extends Object, V extends Object>   {
    /**
     * Get an element in the map
     *
     * @param key The key of the element to get
     * @return the element mapped to the given key, or null if no such mapping exists
     */
    V get(K key);

    /**
     * Add a mapping to the map
     *
     * @param key The key of the mapping to add
     * @param value The value of the mapping to add
     * @return the element that was previously mapped to the given key, or null if no such mapping existed
     */
    V put(K key, V value);

    /**
     * Add a number of mappings to the map
     * <p>
     * Both parameter arrays must be the same length!
     *
     * @param keys All the keys that should be added
     * @param values All the values that should be added
     */
    default void putAll(@NonNull K[] keys, @NonNull V[] values)    {
        if (keys.length == 0 || values.length == 0) return;
        if (keys.length != values.length) throw new IllegalArgumentException("Keys and values must have the same length!");

        for (int i = 0; i < keys.length; i++)   {
            this.put(keys[i], values[i]);
        }
    }

    /**
     * Copies the values from another map into this one
     *
     * @param map The map whose elements should be copied
     */
    default void putAll(@NonNull ObjectObjectMap<K, V> map)    {
        if (map.getSize() == 0) return;

        map.forEachEntry(this::put);
    }

    /**
     * Remove a mapping from the map
     *
     * @param key The key of the mapping to remove
     * @return the element that was previously mapped to the given key, or null if no such mapping existed
     */
    V remove(K key);

    /**
     * Remove a number of mappings from the map
     *
     * @param keys All the keys that should be removed
     */
    default void removeAll(@NonNull Collection<K> keys) {
        keys.forEach(this::remove);
    }

    /**
     * Checks if the mapping contains a mapping with a given key
     *
     * @param key The key of the mapping to check for
     * @return true if such a mapping exists, false otherwise
     */
    boolean containsKey(K key);

    /**
     * Checks if the mapping contains a mapping with a given value
     *
     * @param value The value of the mapping to check for
     * @return true if such a mapping exists, false otherwise
     */
    boolean containsValue(V value);

    /**
     * Empties the map completely, resetting it to how it was when it was first instantiated
     */
    void clear();

    /**
     * Get the total number of elements in this map
     *
     * @return the total number of mappings
     */
    int getSize();

    /**
     * Runs a given function on every key in the map
     *
     * @param consumer the function to run
     */
    void forEachKey(ObjectConsumer<K> consumer);

    /**
     * Runs a given function on every value in the map
     *
     * @param consumer the function to run
     */
    void forEachValue(ObjectConsumer<V> consumer);

    /**
     * Runs a given function on every key+value pair in the map
     *
     * @param consumer the function to run
     */
    void forEachEntry(ObjectObjectConsumer<K, V> consumer);

    /**
     * Gets an iterator over all keys in this map
     *
     * @return an iterator over all keys in this map
     */
    ObjectIterator<K> keyIterator();

    /**
     * Gets an iterator over all values in this map
     *
     * @return an iterator over all values in this map
     */
    ObjectIterator<V> valueIterator();

    /**
     * Gets an iterator over all key => value entries in this map
     *
     * @return an iterator over all key => value mappings in this map
     */
    ObjectObjectIterator<K, V> entryIterator();

    /**
     * Checks if this map is empty
     *
     * @return whether or not this map is empty
     */
    default boolean isEmpty()   {
        return this.getSize() == 0;
    }

    /**
     * Gets an array containing all keys in the map
     *
     * @return an array containing all keys in the map
     */
    default Object[] getKeys()  {
        Object[] arr = new Object[this.getSize()];
        AtomicInteger i = new AtomicInteger(0);
        this.forEachKey(k -> arr[i.getAndIncrement()] = k);
        return arr;
    }

    /**
     * Gets an array containing all values in the map
     *
     * @return an array containing all values in the map
     */
    default Object[] getValues()  {
        Object[] arr = new Object[this.getSize()];
        AtomicInteger i = new AtomicInteger(0);
        this.forEachValue(v -> arr[i.getAndIncrement()] = v);
        return arr;
    }

    /**
     * Puts a value to the map if not already present at the given key, does nothing if a mapping
     * with the given key already exists
     *
     * @return the previous value (null if not present previously)
     */
    default V putIfAbsent(K key, V value)  {
        if (this.containsKey(key))  {
            return this.get(key);
        } else {
            this.put(key, value);
            return null;
        }
    }

    /**
     * Gets the current value at a given key.
     * <p>
     * If the given key is not present already, the default value will be put to the map and returned.
     *
     * @return the current value at the given key, or the default value if not present.
     */
    default V getOrDefault(K key, V def)   {
        if (this.containsKey(key))  {
            return this.get(key);
        } else {
            this.put(key, def);
            return def;
        }
    }

    /**
     * Gets the current value at a given key.
     * <p>
     * If the given key is not present already, a new value is computed using the given function, put into the map,
     * and returned.
     *
     * @return the current value, or the value returned by the function if none was found.
     */
    default V computeIfAbsent(K key, @NonNull ObjectToObjectFunction<K, V> func) {
        if (this.containsKey(key))  {
            return this.get(key);
        } else {
            V val = func.apply(key);
            this.put(key, val);
            return val;
        }
    }

    /**
     * Hashes a key
     *
     * @param in the key to be hashed
     * @return a hash of the given key
     */
    default int hashKey(K in)    {
        return java.util.Objects.hashCode(in);
    }
}