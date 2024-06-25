package io.github.zyszero.phoenix.cache;

/**
 * phoenix cache plugin
 *
 * @Author: zyszero
 * @Date: 2024/6/21 1:24
 */
public interface PhoenixPlugin {

    void init();

    void startup();

    void shutdown();
}
