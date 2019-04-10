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

package net.daporkchop.lib.gui.component.type.functional;

import lombok.NonNull;
import net.daporkchop.lib.gui.GuiEngine;
import net.daporkchop.lib.gui.component.Component;
import net.daporkchop.lib.gui.component.Container;
import net.daporkchop.lib.gui.component.NestedContainer;
import net.daporkchop.lib.gui.component.state.functional.TableState;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author DaPorkchop_
 */
public interface Table extends Component<Table, TableState> {
    static Renderer<Object, Label> defaultTextRenderer()  {
        return (engine, value, oldComponent) -> {
            if (oldComponent == null)   {
                oldComponent = engine.label();
            }
            oldComponent.setText(Objects.toString(value));
            return oldComponent;
        };
    }

    static <V, C extends Component> Renderer<V, C> updateRenderer(@NonNull Function<GuiEngine, C> componentCreator, @NonNull BiConsumer<V, C> updater)  {
        return (engine, value, oldComponent) -> {
            if (oldComponent == null)   {
                oldComponent = componentCreator.apply(engine);
            }
            updater.accept(value, oldComponent);
            return oldComponent;
        };
    }

    @Override
    default TableState getState() {
        return this.isVisible() ?
                this.isEnabled() ?
                        this.isHovered() ? TableState.ENABLED_HOVERED : TableState.ENABLED
                        : this.isHovered() ? TableState.DISABLED_HOVERED : TableState.DISABLED
                : TableState.HIDDEN;
    }

    int getColumns();
    int getRows();

    Table removeColumn(int col);
    Table removeRow(int row);

    <V> Column<V> addAndGetColumn(String name, @NonNull Class<V> clazz);
    Row addAndGetRow();
    Row insertAndGetRow(int index);
    default Table addColumn(String name, @NonNull Class<?> clazz)    {
        this.addAndGetColumn(name, clazz);
        return this;
    }
    default Table addRow()  {
        this.addAndGetRow();
        return this;
    }
    default Table insertRow(int index)  {
        this.insertAndGetRow(index);
        return this;
    }

    Column getColumn(int index);
    Row getRow(int index);

    default String getColumnName(int index)   {
        return this.getColumn(index).getName();
    }
    default Table setColumnName(int index, String name)    {
        this.getColumn(index).setName(name);
        return this;
    }

    default <V> V getValue(int row, int col)    {
        return this.getRow(row).getValue(col);
    }
    default Table setValue(int row, int col, @NonNull Object val)   {
        this.getRow(row).setValue(col, val);
        return this;
    }

    interface Column<V>    {
        Table getParent();

        String getName();
        Column<V> setName(String name);

        int index();
        Column<V> setIndex(int dst);
        Column<V> swap(int dst);

        Class<V> getValueClass();
        Renderer<V, ? extends Component> getValueRenderer();
        <T> Column<T> setValueType(@NonNull Class<T> clazz, @NonNull Renderer<T, ? extends Component> renderer);
    }

    interface Row   {
        Table getParent();

        int index();
        Row setIndex(int dst);
        Row swap(int dst);

        <V> V getValue(int col);
        Row setValue(int col, @NonNull Object val);

        default Column getColumn(int index) {
            return this.getParent().getColumn(index);
        }
    }

    interface Renderer<V, C extends Component>  {
        C update(@NonNull GuiEngine engine, @NonNull V value, C oldComponent);
    }
}
