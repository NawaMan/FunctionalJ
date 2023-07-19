// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.data;

import static functionalj.function.Func.f;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import functionalj.list.FuncList;
import functionalj.result.Result;

public class FuncData {
    
    // == Read text file ==
    /**
     * Read lines from file. This method will read lines in lazily and ensure the file is closed.
     *
     * This has been tested     with 2GB text file.
     *
     * @param path
     *          the path to the text file.
     * @return  the functional list containing the lines of text from the class.
     */
    public static Result<FuncList<String>> readLines(String path) {
        return readLines(Paths.get(path), StandardCharsets.UTF_8);
    }
    
    /**
     * Read lines from file. This method will read lines in lazily and ensure the file is closed.
     *
     * This has been tested with 2GB text file.
     *
     * @param path
     *          the path to the text file.
     * @return  the functional list containing the lines of text from the class.
     */
    public static Result<FuncList<String>> readLines(String path, Charset charset) {
        return readLines(Paths.get(path), charset);
    }
    
    /**
     * Read lines from file. This method will read lines in lazily and ensure the file is closed.
     *
     * This has been tested with 2GB text file.
     *
     * @param path
     *          the path to the text file.
     * @return  the functional list containing the lines of text from the class.
     */
    public static Result<FuncList<String>> readLines(Path path) {
        return readLines(path, StandardCharsets.UTF_8);
    }
    
    /**
     * Read lines from file. This method will read lines in lazily and ensure the file is closed.
     *
     * This has been tested with 2GB text file.
     *
     * @param path
     *          the path to the text file.
     * @return  the functional list containing the lines of text from the class.
     */
    public static Result<FuncList<String>> readLines(Path path, Charset charset) {
        return Result.from(() -> FuncList.from(f(() -> Files.lines(path, charset))));
    }
    // TODO - Write text file
    // TODO - Read CSV
    // TODO - Write CSV
    // TODO - Read SQL
    // TODO - Write SQL
}
