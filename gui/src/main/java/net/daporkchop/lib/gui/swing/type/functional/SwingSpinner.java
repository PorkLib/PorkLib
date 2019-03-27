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

package net.daporkchop.lib.gui.swing.type.functional;

import lombok.NonNull;
import net.daporkchop.lib.gui.component.state.functional.SpinnerState;
import net.daporkchop.lib.gui.component.type.functional.Spinner;
import net.daporkchop.lib.gui.swing.impl.SwingComponent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.IntConsumer;

/**
 * @author DaPorkchop_
 */
public class SwingSpinner extends SwingComponent<Spinner, JSpinner, SpinnerState> implements Spinner {
    protected int value = 0;
    protected int max = Integer.MAX_VALUE;
    protected int min = Integer.MIN_VALUE;
    protected int step = 1;

    protected final Map<String, IntConsumer> listeners = new HashMap<>();

    public SwingSpinner(String name) {
        super(name, new JSpinner());

        this.updateModel();

        this.swing.addChangeListener(new SwingSpinnerChangeListener());
    }

    @Override
    public int getValue() {
        return (int) this.swing.getValue();
    }

    @Override
    public Spinner setValue(int val) {
        this.swing.setValue(val);
        return this;
    }

    @Override
    public Spinner setMaxValue(int val) {
        if (this.max != val)    {
            this.max = val;
            this.updateModel();
        }
        return this;
    }

    @Override
    public Spinner setMinValue(int val) {
        if (this.min != val)    {
            this.min = val;
            this.updateModel();
        }
        return this;
    }

    @Override
    public Spinner setLimits(int min, int max) {
        if (this.min != min || this.max != max) {
            this.min = min;
            this.max = max;
            this.updateModel();
        }
        return this;
    }

    @Override
    public Spinner setValAndLimits(int val, int min, int max) {
        if (this.value != val || this.min != min || this.max != max) {
            this.value = val;
            this.min = min;
            this.max = max;
            this.updateModel();
        }
        return this;
    }

    @Override
    public Spinner setStep(int step) {
        if (this.step != step)    {
            this.step = step;
            this.updateModel();
        }
        return this;
    }

    @Override
    public Spinner addChangeListener(@NonNull String name, @NonNull IntConsumer callback) {
        this.listeners.put(name, callback);
        return this;
    }

    @Override
    public Spinner removeChangeListener(@NonNull String name) {
        this.listeners.remove(name);
        return this;
    }

    protected void updateModel()    {
        this.swing.setModel(new SpinnerNumberModel(this.value, this.min, this.max, this.step));
    }

    protected class SwingSpinnerChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            if (SwingSpinner.this.value != (int) SwingSpinner.this.swing.getValue())   {
                int val = SwingSpinner.this.value = (int) SwingSpinner.this.swing.getValue();
                SwingSpinner.this.listeners.values().forEach(callback -> callback.accept(val));
            }
        }
    }
}