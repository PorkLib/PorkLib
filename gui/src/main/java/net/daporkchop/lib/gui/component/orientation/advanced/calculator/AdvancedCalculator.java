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

package net.daporkchop.lib.gui.component.orientation.advanced.calculator;

import lombok.Getter;
import lombok.NonNull;
import net.daporkchop.lib.gui.component.Component;
import net.daporkchop.lib.gui.component.Container;
import net.daporkchop.lib.gui.component.orientation.advanced.Axis;
import net.daporkchop.lib.gui.component.orientation.advanced.Calculator;
import net.daporkchop.lib.gui.util.math.BoundingBox;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;

/**
 * @author DaPorkchop_
 */
@Getter
public class AdvancedCalculator<T extends Component> implements Calculator<T> {
    protected final Collection<Calculator<T>> mins = new HashSet<>();
    protected final Collection<Calculator<T>> maxes = new HashSet<>();
    protected Calculator<T> between = NullCalculator.getInstance();

    @Override
    public int get(BoundingBox bb, Container parent, T component, int[] dims) {
        int between = this.between.get(bb, parent, component, dims);
        int min;
        if (this.mins.isEmpty()) {
            min = 0;
        } else {
            min = Integer.MIN_VALUE;
            for (Calculator<T> calculator : this.mins) {
                min = Math.max(min, calculator.get(bb, parent, component, dims));
            }
        }
        int max;
        if (this.maxes.isEmpty()) {
            max = Integer.MAX_VALUE;
        } else {
            max = Integer.MAX_VALUE;
            for (Calculator<T> calculator : this.maxes) {
                max = Math.min(max, calculator.get(bb, parent, component, dims));
            }
        }
        /*if (dims[0] != -1 && dims[1] == -1) {
            int j = 0;
        }*/ //debugger time!
        return Math.max(min, Math.min(max, between));
    }

    public AdvancedCalculator<T> min(@NonNull Calculator<T> calculator) {
        this.mins.add(calculator);
        return this;
    }

    public AdvancedCalculator<T> max(@NonNull Calculator<T> calculator) {
        this.maxes.add(calculator);
        return this;
    }

    public AdvancedCalculator<T> ease(Calculator<T> calculator) {
        this.between = calculator;
        return this;
    }

    public AdvancedCalculator<T> max(@NonNull Consumer<SumCalculator<T>> initializer) {
        SumCalculator<T> calculator = new SumCalculator<>();
        initializer.accept(calculator);
        return this.max(calculator);
    }

    public AdvancedCalculator<T> min(@NonNull Consumer<SumCalculator<T>> initializer) {
        SumCalculator<T> calculator = new SumCalculator<>();
        initializer.accept(calculator);
        return this.min(calculator);
    }

    public AdvancedCalculator<T> ease(@NonNull Consumer<SumCalculator<T>> initializer) {
        SumCalculator<T> calculator = new SumCalculator<>();
        initializer.accept(calculator);
        return this.ease(calculator);
    }

    //convenience methods
    public AdvancedCalculator<T> max(@NonNull Object... args) {
        return this.max(this.parse(args));
    }

    public AdvancedCalculator<T> min(@NonNull Object... args) {
        return this.min(this.parse(args));
    }

    public AdvancedCalculator<T> ease(@NonNull Object... args) {
        return this.ease(this.parse(args));
    }

    @SuppressWarnings("unchecked")
    protected Calculator<T> parse(@NonNull Object... args) {
        SumCalculator<T> calculator = new SumCalculator<>();
        if (args.length != 0) {
            DistUnit unit = null;
            double dVal = Double.NaN;
            Axis axis = null;
            String relative = null;
            for (Object o : args) {
                if (o instanceof DistUnit) {
                    if (unit != null) {
                        calculator = calculator.plus(unit.create(dVal, axis, relative));
                    }
                    unit = (DistUnit) o;
                    dVal = Double.NaN;
                    axis = null;
                    relative = null;
                } else if (o instanceof Calculator) {
                    if (unit != null) {
                        calculator = calculator.plus(unit.create(dVal, axis, relative));
                        unit = null;
                        dVal = Double.NaN;
                        axis = null;
                        relative = null;
                    }
                    calculator = calculator.plus((Calculator) o);
                } else if (unit == null) {
                    throw new IllegalStateException("DistUnit not set!");
                } else if (o instanceof Number) {
                    dVal = ((Number) o).doubleValue();
                } else if (o instanceof Axis) {
                    axis = (Axis) o;
                } else if (o instanceof String) {
                    relative = (String) o;
                }
            }
            if (unit != null) {
                calculator = calculator.plus(unit.create(dVal, axis, relative));
            }
        }
        return calculator.build();
    }
}