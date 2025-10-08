package scarlett.notification.org.main.multieWorker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class QueueConfiguration {
    @Value("${processing.messages.executor.threadCount:2}")
    private int threadCount;
    @Value("${processing.messages.executor.maxPoolSize:30}")
    private int maxPoolSize;

    @Bean
    public ThreadPoolExecutor priorityExecutor() {
        return new ThreadPoolExecutor(threadCount, maxPoolSize, 2000L, TimeUnit.MILLISECONDS,
                                      new PriorityBlockingQueue<>());
    }
}
