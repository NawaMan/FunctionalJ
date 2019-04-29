package functionalj.list;

import java.util.ArrayList;
import java.util.List;

public class FuncListBuilder<DATA> {
    private final List<DATA> entries;
    private final int count;
    
    public FuncListBuilder() {
        this.entries = new ArrayList<DATA>();
        this.count = 0;
    }
    private FuncListBuilder(List<DATA> entries) {
        this.entries = entries;
        this.count = this.entries.size();
    }
    
    public FuncListBuilder<DATA> with(DATA value) {
        this.entries.add(value);
        return new FuncListBuilder<>(this.entries);
    }
    
    public ImmutableList<DATA> build() {
        return ImmutableList.from(this.entries.stream().limit(count));
    }
}