package nawaman.functionalj.compose;

import java.util.function.Function;

public abstract class MayBe<TYPE> implements IHaveMap<TYPE>, IHaveFMap<TYPE>, IHaveChain<TYPE> {


    public static <T> MayBe<T> empty() {
        return Nothing.instance;
    }
    
    MayBe() {
        
    }
    
    public boolean isPresent() {
        return false;
    }

    public <RESULT, IHAVEMAP extends IHaveMap<RESULT>> IHAVEMAP map(Function<TYPE, RESULT> f) {
        return (IHAVEMAP) empty();
    }
    
    @Override
    public <RESULT, IHAVEFMAP extends IHaveFMap<RESULT>> IHAVEFMAP fmap(Function<TYPE, IHAVEFMAP> f) {
        return (IHAVEFMAP) empty();
    }
    
    
//    public void concat() {
//        
//    }
    
//    Nothing.prototype.concat = function(b) {
//        return b;
//      }
//      Just.prototype.concat = function(b) {
//        if(notThere(b.val)) return this;
//        return Maybe(this.val.concat(b.val));
//      };
//    Nothing.prototype.ap = function(m) {
//        return Nothing();
//      }
//      Just.prototype.ap = function(m) {
//        return fmap(this.val, m);
//      }
    
    public static <TYPE> MayBe<TYPE> of(TYPE data) {
        return (data != null) ? new Just(data) : Nothing.instance;
    }
    
    public static class Just<TYPE> extends MayBe<TYPE> {
        private TYPE data;
        Just(TYPE data) { this.data = data; }
        
        public boolean isPresent() {
            return true;
        }
        public <RESULT, IHAVEMAP extends IHaveMap<RESULT>> IHAVEMAP map(Function<TYPE, RESULT> f) {
            return (IHAVEMAP) MayBe.of(f.apply(this.data));
        }
        
        @Override
        public <RESULT> RESULT chain(Function<TYPE, RESULT> f) {
            return f.apply(this.data);
        }
        
        @Override
        public <RESULT, IHAVEFMAP extends IHaveFMap<RESULT>> IHAVEFMAP fmap(Function<TYPE, IHAVEFMAP> f) {
            return f.apply(this.data);
        }
//        public MayBe<TYPE> concat(MayBe<TYPE> another) {
//            if ((another == null) || !another.isPresent())
//                return this;
//            return MayBe.of(this.data)
//        }
//      public MayBe<TYPE> ap(MayBe<TYPE> another) {
//      if ((another == null) || !another.isPresent())
//          return this;
//      return MayBe.of(this.data)
//  }
        
        public String toString() {
            return "MayBe(" + this.data + ")";
        }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((data == null) ? 0 : data.hashCode());
            return result;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Just other = (Just) obj;
            if (data == null) {
                if (other.data != null)
                    return false;
            } else if (!data.equals(other.data))
                return false;
            return true;
        }
    }
    
    public static class Nothing<TYPE> extends MayBe<TYPE> {
        
        static final Nothing instance = new Nothing<>();
        
        private Nothing() { super(); }
        
        @Override
        public <RESULT> RESULT chain(Function<TYPE, RESULT> f) {
            return null;
        }
        
        
        public String toString() {
            return "MayBe(null)";
        }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result;
            return result;
        }
        @Override
        public boolean equals(Object obj) {
            return (this == obj);
        }
    }
    
}
