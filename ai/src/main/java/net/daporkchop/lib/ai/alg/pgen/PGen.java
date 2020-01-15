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

package net.daporkchop.lib.ai.alg.pgen;

import lombok.NonNull;
import net.daporkchop.lib.ai.Evaluator;
import net.daporkchop.lib.ai.NeuralNetwork;
import net.daporkchop.lib.ai.alg.MachineLearning;
import net.daporkchop.lib.ai.alg.pgen.evolution.PGenTrainer;

/**
 * Implementation of the PGen (PorkGenetic) neural network training algorithm, kinda inspired by NEAT but not really
 * because I don't really feel like actually reading up on it (i'm lazy lol)
 *
 * @author DaPorkchop_
 */
public class PGen implements MachineLearning<NeuralNetwork, PGenOptions> {
    @Override
    public PGenTrainer beginTraining(@NonNull Evaluator<NeuralNetwork> evaluator, @NonNull PGenOptions options) {
        if (options.inputs <= 0)    {
            throw new IllegalArgumentException("Number of inputs must be set!");
        } else if (options.outputs <= 0)    {
            throw new IllegalArgumentException("Number of outputs must be set!");
        } else {
            return new PGenTrainer(evaluator, options);
        }
    }
}
