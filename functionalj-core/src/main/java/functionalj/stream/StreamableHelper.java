package functionalj.stream;

import java.util.ArrayList;
import java.util.List;

import functionalj.list.FuncList;
import lombok.val;

public class StreamableHelper {
    
    static <D> FuncList<FuncList<D>> segmentByPercentiles(FuncList<D> list, FuncList<Double> percentiles) {
        val size    = list.size();
        val indexes = percentiles.sorted().map(d -> (int)Math.round(d*size/100)).toArrayList();
        if (indexes.get(indexes.size() - 1) != size) {
            indexes.add(size);
        }
        val lists   = new ArrayList<List<D>>();
        for (int i = 0; i < indexes.size(); i++) {
            lists.add(new ArrayList<D>());
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
        return FuncList.from(
                lists
                .stream()
                .map(each -> (FuncList<D>)StreamPlus.from(each.stream()).toImmutableList()));
    }
    
}
