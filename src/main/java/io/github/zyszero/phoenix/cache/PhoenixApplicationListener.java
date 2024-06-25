package io.github.zyszero.phoenix.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * plugins entrypoint
 *
 * @Author: zyszero
 * @Date: 2024/6/21 1:26
 */
@Component
public class PhoenixApplicationListener implements ApplicationListener<ApplicationEvent> {

    @Autowired
    List<PhoenixPlugin> plugins;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationReadyEvent readyEvent) {
            for (PhoenixPlugin plugin : plugins) {
                plugin.init();
                plugin.startup();
            }
        } else if (event instanceof ContextClosedEvent cce) {
            for (PhoenixPlugin plugin : plugins) {
                plugin.shutdown();
            }
        }
    }
}
