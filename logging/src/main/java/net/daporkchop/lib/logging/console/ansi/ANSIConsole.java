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

package net.daporkchop.lib.logging.console.ansi;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.daporkchop.lib.logging.console.Console;
import net.daporkchop.lib.logging.impl.DefaultLogger;

import java.awt.Color;
import java.io.PrintStream;

/**
 * @author DaPorkchop_
 */
@RequiredArgsConstructor
@Getter
public class ANSIConsole implements ANSI, Console {
    @NonNull
    protected final PrintStream printer;

    public ANSIConsole()    {
        this(DefaultLogger.stdOut);
    }

    @Override
    public void setTitle(@NonNull String title) {
        this.printer.printf("%c]0;%s%c", ESC, title, BEL);
    }

    @Override
    public void setTextColor(Color color) {
        this.setTextColor(VGAColor.closestTo(color));
    }

    public void setTextColor(@NonNull VGAColor color)   {
        this.printer.printf("%c[%dm", ESC, color.fg);
    }

    @Override
    public void setBackgroundColor(Color color) {
        this.setBackgroundColor(VGAColor.closestTo(color));
    }

    public void setBackgroundColor(@NonNull VGAColor color)   {
        this.printer.printf("%c[%dm", ESC, color.bg);
    }
}
