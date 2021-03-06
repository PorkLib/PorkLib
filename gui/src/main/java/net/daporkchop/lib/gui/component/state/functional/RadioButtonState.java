/*
 * Adapted from The MIT License (MIT)
 *
 * Copyright (c) 2018-2020 DaPorkchop_
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * Any persons and/or organizations using this software must include the above copyright notice and this permission notice,
 * provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package net.daporkchop.lib.gui.component.state.functional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.daporkchop.lib.gui.component.state.ElementState;
import net.daporkchop.lib.gui.component.type.functional.CheckBox;
import net.daporkchop.lib.gui.component.type.functional.RadioButton;

/**
 * @author DaPorkchop_
 */
@AllArgsConstructor
@Getter
public enum RadioButtonState implements ElementState<RadioButton, RadioButtonState> {
    ENABLED(true, true, false, false),
    ENABLED_HOVERED(true, true, true, false),
    ENABLED_SELECTED(true, true, false, true),
    ENABLED_HOVERED_SELECTED(true, true, true, true),
    DISABLED(true, false, false, false),
    DISABLED_HOVERED(true, false, true, false),
    DISABLED_SELECTED(true, false, false, true),
    DISABLED_HOVERED_SELECTED(true, false, true, true),
    HIDDEN(false, false, false, false),
    ;

    protected boolean visible;
    protected boolean enabled;
    protected boolean hovered;
    protected boolean selected;
}
