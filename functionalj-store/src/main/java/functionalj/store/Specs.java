package functionalj.store;

import functionalj.annotations.Choice;

class Specs {
    
    @Choice
    static interface ResultStatusSpec<D> {
        void NotAllowed(ChangeNotAllowedException reason);
        void Accepted  (D newData);
        void Adjusted  (D proposedData, D adjustedData);
        void Rejected  (D propose, D rollback, ChangeRejectedException reason);
        void Failed    (ChangeFailException problem);
    }
    
}
