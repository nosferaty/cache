package cache;

/**
 * Created by George on 06.04.2015.
 */
public class LRUCache<Key, Value> extends Cache<Key, Value> {
    public LRUCache(int size, boolean threadSafe) {
        super(size, threadSafe, true);
    }
}
