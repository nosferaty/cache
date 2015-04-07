package cache;

/**
 * Created by George on 06.04.2015.
 */
public class FIFOCache<Key, Value> extends Cache<Key, Value> {
    public FIFOCache(int size, boolean threadSafe) {
        super(size, threadSafe, false);
    }
}
