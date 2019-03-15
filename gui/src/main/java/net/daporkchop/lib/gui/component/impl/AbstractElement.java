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

package net.daporkchop.lib.gui.component.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.daporkchop.lib.gui.component.Element;
import net.daporkchop.lib.gui.component.state.ElementState;
import net.daporkchop.lib.gui.util.event.handler.StateListener;
import net.daporkchop.lib.gui.util.math.BoundingBox;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author DaPorkchop_
 */
@RequiredArgsConstructor
@Getter
@SuppressWarnings("unchecked")
public abstract class AbstractElement<Impl extends Element, State extends ElementState<Impl, State>> implements Element<Impl, State> {
    @NonNull
    protected final String name;

    protected BoundingBox bounds;

    protected String tooltip;
    protected boolean visible = false;

    @NonNull
    private State state; //we don't want people changing this without triggering state listeners
    protected final Map<String, StateListener<Impl, State>> stateListeners = new LinkedHashMap<>();

    @Override
    public Impl setTooltip(String tooltip) {
        this.tooltip = tooltip;
        return (Impl) this;
    }

    @Override
    public Impl setVisible(boolean state) {
        this.visible = state;
        return (Impl) this;
    }

    public boolean fireStateChange(@NonNull State state)    {
        if (state != this.state)  {
            this.state = state;
            this.stateListeners.forEach((name, listener) -> listener.onStateChange(this.state));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Impl addStateListener(@NonNull String name, @NonNull StateListener<Impl, State> listener) {
        if (this.stateListeners.putIfAbsent(name, listener) != null)    {
            throw new IllegalArgumentException(String.format("Listener name \"%s\" is already occupied!", name));
        }
        return (Impl) this;
    }

    @Override
    public Impl removeStateListener(@NonNull String name) {
        this.stateListeners.remove(name);
        return (Impl) this;
    }
}