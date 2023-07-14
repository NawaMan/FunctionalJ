// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.types.elm.processor;

import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;
import functionalj.types.struct.generator.ILines;

/**
 * This class represents function in Elm for the purpose of generating the elm code.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class ElmFunctionBuilder implements ILines {

    private final String name;

    private final String declaration;

    private final String camelName;

    private final ILines body;

    public ElmFunctionBuilder(String name, String declaration, String camelName, ILines body) {
        this.name = name;
        this.declaration = declaration;
        this.camelName = camelName;
        this.body = body;
    }

    public String name() {
        return name;
    }

    public String declaration() {
        return declaration;
    }

    public String camelName() {
        return camelName;
    }

    public ILines body() {
        return body;
    }

    @Override
    public Stream<String> lines() {
        return ILines.line(name + " : " + declaration).append(name + " " + (camelName + " =").trim() + " ").append(body().indent(1).lines().collect(joining("\n"))).lines();
    }
}
