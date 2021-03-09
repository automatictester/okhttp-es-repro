package uk.co.automatictester.okhttpes.repro;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class SseTest {

    @Test
    public void worksFine() throws InterruptedException {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        try (EventSource eventSource = getEventSource(loggingInterceptor)) {
            eventSource.start();
            TimeUnit.SECONDS.sleep(3);
        }
    }

    @Test
    public void interceptorInactive() throws InterruptedException {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        // setting level to BODY deactivates EventHandler; all other levels are fine
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        try (EventSource eventSource = getEventSource(loggingInterceptor)) {
            eventSource.start();
            TimeUnit.SECONDS.sleep(3);
        }
    }

    private EventSource getEventSource(HttpLoggingInterceptor loggingInterceptor) {
        EventHandler eventHandler = new WikiEventHandler();
        URI url = URI.create("https://stream.wikimedia.org/v2/stream/recentchange");

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        return new EventSource.Builder(eventHandler, url)
                .client(client)
                .build();
    }
}
