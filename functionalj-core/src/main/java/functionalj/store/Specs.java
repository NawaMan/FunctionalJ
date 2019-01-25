package functionalj.store;

import functionalj.annotations.choice.Self1;
import functionalj.function.Func1;
import functionalj.function.FuncUnit1;
import functionalj.result.Result;

class Specs {
    
//    @functionalj.annotations.Choice
    static interface ChangeResultSpec<D> {
        void NotAllowed(Store<D> store, D originalData, ChangeNotAllowedException reason);
        void Accepted  (Store<D> store, D originalData, D newData);
        void Adjusted  (Store<D> store, D originalData, D proposedData, D adjustedData);
        void Rejected  (Store<D> store, D originalData, D propose, D rollback, ChangeRejectedException reason);
        void Failed    (Store<D> store, D originalData, ChangeFailException problem);
        
        default boolean hasChanged(Self1<D> self) {
            ChangeResult<D> result = self.asMe();
            return result.match()
                    .notAllowed(false)
                    .accepted  (true)
                    .adjusted  (true)
                    .orElse    (false);
        }
        default Store<D> store(Self1<D> self) {
            ChangeResult<D> result = self.asMe();
            return result.match()
                    .notAllowed(n -> n.store())
                    .accepted  (a -> a.store())
                    .adjusted  (a -> a.store())
                    .rejected  (r -> r.store())
                    .failed    (f -> f.store());
        }
        @SuppressWarnings("unchecked")
        default Result<D> result(Self1<D> self) {
            ChangeResult<D> result = self.asMe();
            return result.match()
                    .notAllowed(n -> (Result<D>)Result.ofNotExist())
                    .accepted  (a -> Result.valueOf(a.newData()))
                    .adjusted  (a -> Result.valueOf(a.adjustedData()))
                    .orElse    (     (Result<D>)Result.ofNotExist());
        }
        @SuppressWarnings("unchecked")
        default D value(Self1<D> self) {
            ChangeResult<D> result = self.asMe();
            return result.match()
                    .notAllowed(n -> (Result<D>)Result.ofNotExist())
                    .accepted  (a -> Result.valueOf(a.newData()))
                    .adjusted  (a -> Result.valueOf(a.adjustedData()))
                    .orElse    (     (Result<D>)Result.ofNotExist())
                    .value();
        }
        default ChangeResult<D> change(Self1<D> self, Func1<D, D> changer) {
            ChangeResult<D> result = self.asMe();
            return result.hasChanged()
                    ? store(self).change(changer)
                    : result;
        }
        default ChangeResult<D> use(Self1<D> self, FuncUnit1<D> consumer) {
            ChangeResult<D> result = self.asMe();
            store(self).use(consumer);
            return result;
        }
        default String toString(Self1<D> self) {
            ChangeResult<D> result = self.asMe();
            return result.match()
                    .notAllowed(n -> "NotAllowed(" + n.originalData() + "," + n.reason() + ")")
                    .accepted  (a -> "Accepted("   + a.originalData() + "," + a.newData() + ")")
                    .adjusted  (a -> "Adjusted("   + a.originalData() + "," + a.proposedData() + "," + a.adjustedData() + ")")
                    .rejected  (r -> "Rejected("   + r.originalData() + "," + r.propose() + "," + r.rollback() + "" + r.reason() + ")")
                    .failed    (f -> "Failed("     + f.originalData() + "," + f.problem() + ")");
        }
    }
}
