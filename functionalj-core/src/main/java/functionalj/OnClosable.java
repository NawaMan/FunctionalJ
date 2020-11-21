package functionalj;

import java.io.Closeable;

public class OnClosable<TYPE> implements Closeable {

    private volatile boolean isClosed = false;
    private volatile Runnable onClose = () -> {
        synchronized (this) {
            isClosed = true;
        }
    };

    public TYPE onClose(Runnable closeHandler) {
        Runnable oldOnClose = this.onClose;
        Runnable newOnClose = () -> {
            oldOnClose.run();
            closeHandler.run();
        };
        synchronized (this) {
            this.onClose = newOnClose;
        }
        return null;
    }
    public void close() {
        if (!isClosed) {
            synchronized (this) {
                if (!isClosed) {
                    onClose.run();
                    return;
                }
            }
        }
        throw new IllegalStateException("stream has already been operated upon or closed");
    }
}
