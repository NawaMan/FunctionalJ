// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.promise;

import functionalj.function.FuncUnit1;
import functionalj.result.Result;

public abstract class StartableAction<DATA> {
    
    public abstract Promise<DATA> getPromise();
    
    public abstract PendingAction<DATA> start();
    
    
    // == Subscription ==
    
    /**
     * Use the promise for this action.
     * 
     * @param  consumer  the consumer for the promise.
     * @return           this action.
     **/
    public abstract UncompletedAction<DATA> use(FuncUnit1<Promise<DATA>> consumer);
    
    /**
     * Subscribe to the successful result.
     * 
     * @param  dataConsumer  the consumer for the resulted data.
     * @return               this action.
     */
    public abstract UncompletedAction<DATA> subscribe(FuncUnit1<DATA> dataConsumer);
    
    /**
     * Subscribe to the successful result with wait setting.
     * 
     * @param  dataConsumer  the consumer for the resulted data.
     * @return               this action.
     */
    public abstract UncompletedAction<DATA> subscribe(Wait wait, FuncUnit1<DATA> dataConsumer);
    
    /**
     * Subscribe to the final result.
     * 
     * @param  resultConsumer  the consumer for the final result.
     * @return                 this action.
     */
    public abstract UncompletedAction<DATA> onCompleted(FuncUnit1<Result<DATA>> resultConsumer);
    
    /**
     * Subscribe to the final result with wait setting.
     * 
     * @param  resultConsumer  the consumer for the final result.
     * @return                 this action.
     */
    public abstract UncompletedAction<DATA> onCompleted(Wait wait, FuncUnit1<Result<DATA>> resultConsumer);
    
    /**
     * Subscribe to the final result with a way store the subscription for later use.
     * 
     * @param  resultConsumer  the consumer for the final result.
     * @return                 this action.
     */
    public abstract UncompletedAction<DATA> onCompleted(FuncUnit1<Result<DATA>> resultConsumer, FuncUnit1<SubscriptionRecord<DATA>> subscriptionConsumer);
    
    /**
     * Subscribe to the final result with wait setting and a way store the subscription for later use.
     * 
     * @param  resultConsumer  the consumer for the final result.
     * @return                 this action.
     */
    public abstract UncompletedAction<DATA> onCompleted(Wait wait, FuncUnit1<Result<DATA>> resultConsumer, FuncUnit1<SubscriptionRecord<DATA>> subscriptionConsumer);
    
    /**
     * Eavesdrop to the final result.
     * 
     * @param  resultConsumer  the consumer for the final result.
     * @return                 this action.
     */
    public abstract UncompletedAction<DATA> eavesdrop(FuncUnit1<Result<DATA>> resultConsumer);
    
    /**
     * Eavesdrop to the final result with wait setting.
     * 
     * @param  resultConsumer  the consumer for the final result.
     * @return                 this action.
     */
    public abstract UncompletedAction<DATA> eavesdrop(Wait wait, FuncUnit1<Result<DATA>> resultConsumer);
    
}
