// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.list.intlist;

import java.util.ArrayList;
import functionalj.list.FuncList;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.stream.intstream.GrowOnlyIntArray;
import lombok.val;

public class IntFuncListHelper {

    static <D> FuncList<IntFuncList> segmentByPercentiles(AsIntFuncList asList, DoubleFuncList percentiles) {
        val list = asList.asIntFuncList();
        val size = list.size();
        DoubleFuncList indexes = percentiles.append(100.0).sorted().map(d -> (int) Math.round(d * size / 100)).toImmutableList();
        val lists = new ArrayList<GrowOnlyIntArray>();
        for (int i = 0; i < indexes.size(); i++) {
            lists.add(new GrowOnlyIntArray());
        }
        int idx = 0;
        for (int i = 0; i < size; i++) {
            if (i >= indexes.get(idx)) {
                idx++;
            }
            val l = lists.get(idx);
            val element = list.get(i);
            l.add(element);
        }
        return FuncList.from(lists.stream().map(each -> each.toFuncList()));
    }
}
