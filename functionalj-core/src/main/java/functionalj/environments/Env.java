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
package functionalj.environments;

import functionalj.ref.Ref;

public final class Env {
    
    public static final class refs {
        
        public static final Ref<AsyncRunner> async = Ref.ofValue(AsyncRunner.threadFactory);
        
        public static final Ref<Console.Instance> console = Ref.ofValue(Console.System.instance);
        
        public static final Ref<Log.Instance> log = Ref.ofValue(Log.Instance.instance);
        
        public static final Ref<Time.Instance> time = Ref.ofValue(Time.System.instance);
    }
    
    // TODO - File lister, reader - bytes,string,line. -> no-idea about seekable
    // TODO - Network
    // TODO - TimeFormatter
    // TODO - Error handling
    // TODO - Random
    // TODO - Runtime
    public static Time.Instance time() {
        return Env.refs.time.orElse(Time.System.instance);
    }
    
    public static AsyncRunner async() {
        return Env.refs.async.orElse(AsyncRunner.threadFactory);
    }
    
    public static Console.Instance console() {
        return Env.refs.console.orElse(Console.System.instance);
    }
    
    public static Log.Instance log() {
        return Env.refs.log.orElse(Log.Instance.instance);
    }
}
