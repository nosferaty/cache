package cache;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by George on 06.04.2015.
 */
public class FIFOTest {
    /**
     * Just cache with size = 1
     * We try to get element, put it and get again
     */
    @Test
    public void simpleGetAndPut() {
        FIFOCache<String, String> cache = new FIFOCache<>(1, false);
        Assert.assertNull(cache.get("a1"));

        cache.set("a1", "message");
        Assert.assertEquals("message", cache.get("a1"));
    }

    /**
     * We put three items in a cache with max size = 3
     * Then we are trying to put 4th element
     * As a result, element with oldest insertion time (a1) should be kicked out
     */
    @Test
    public void fifoLogicTest() {
        final int CACHE_SIZE = 3;
        FIFOCache<String, String> cache = new FIFOCache<>(CACHE_SIZE, false);

        cache.set("a1", "value1");
        cache.set("a2", "value2");
        cache.set("a3", "value3");

        cache.get("a2");

        cache.get("a3");

        cache.get("a1");

        Assert.assertEquals(CACHE_SIZE, cache.size());


        // we put 4th element, so a1 element should be kicked out
        cache.set("a4", "value4");
        Assert.assertEquals(CACHE_SIZE, cache.size());
        Assert.assertNull(cache.get("a1"));
    }}
