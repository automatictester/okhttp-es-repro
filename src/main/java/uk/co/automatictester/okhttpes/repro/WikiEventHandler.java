package uk.co.automatictester.okhttpes.repro;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WikiEventHandler implements EventHandler {

    @Override
    public void onOpen() {
        log.info("opened");
    }

    @Override
    public void onClosed() {
        log.info("closed");
    }

    @Override
    public void onMessage(String event, MessageEvent messageEvent) {
        log.info("message received: {}", messageEvent.getData());
    }

    @Override
    public void onComment(String comment) {
        log.info("comment received: {}", comment);
    }

    @Override
    public void onError(Throwable t) {
        log.info("error: " + t);
    }
}
