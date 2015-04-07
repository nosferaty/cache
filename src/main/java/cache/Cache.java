package cache;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by georgy.lipatov on 6/3/2015.
 */
public abstract class Cache<Key, Value> {
    final protected Map<Key, Value> map;
    final protected int maxCacheSize;
    protected Cache(int size, boolean threadSafe, boolean accessOrder) {
        maxCacheSize = size;
        final Map<Key, Value> tempMap = new LinkedHashMap<Key, Value>(size + 1, 0.75f, accessOrder) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Key, Value> eldest) {
                return size() > maxCacheSize;
            }
        };
        if (threadSafe) {
            map = Collections.synchronizedMap(tempMap);
        } else {
            map = tempMap;
        }
    }

    public Value get(Key key) {
        return map.get(key);
    }

    public void set(Key key, Value value) {
        map.put(key, value);
    }

    public int size() {
        return map.size();
    }
}
