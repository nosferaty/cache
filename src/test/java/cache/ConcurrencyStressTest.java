package cache;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by georgy.lipatov on 6/3/2015.
 */
@RunWith(Parameterized.class)
public class ConcurrencyStressTest {
    public ConcurrencyStressTest(Cache<String, String> cache, String name) {
        this.cache = cache;
        this.name = name;
    }

    Cache<String, String> cache;
    String name;

    public class Worker implements Callable<Integer> {
        final Cache<String, String> cache;
        final int steps;

        public Worker(Cache<String, String> cache, int steps) {
            this.cache = cache;
            this.steps = steps;
        }

        @Override
        public Integer call() throws Exception {
            final int DEPTH = 100;
            for (int i = 0; i < steps; i++) {
                String key = String.format("value%d", i % DEPTH);
                if (i < DEPTH) {
                    cache.set(key, "42");
                }
                cache.get(key);
            }
            return 0;
        }
    }

    /**
     * Concurrency test. At the end there should be 2 items in cache
     * cache in thread-unsafe mode will have more than 2 elements at the end
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void stressTest() throws ExecutionException, InterruptedException {
        final int WORKERS_COUNT = 5;
        ExecutorService executor = new ScheduledThreadPoolExecutor(WORKERS_COUNT);


        List<Future> list = new ArrayList<>(WORKERS_COUNT);

        for (int i = 0; i < WORKERS_COUNT; i++) {
            Worker worker = new Worker(cache, 1000);
            Future<Integer> future = executor.submit(worker);
            list.add(future);
        }

        for (Future future: list) {
            future.get();
        }

        Assert.assertEquals(2, cache.size());
    }

    @Parameterized.Parameters(name = "Cache: {1}")
    public static Iterable<Object[]> getCache() {
        LRUCache<String, String> lruCache = new LRUCache<>(2, true);

        FIFOCache<String, String> fifoCache = new FIFOCache<>(2, true);

        return Arrays.asList(new Object[][]{
                {lruCache, "LRU"},
                {fifoCache, "FIFO"}
        });
    }
}
