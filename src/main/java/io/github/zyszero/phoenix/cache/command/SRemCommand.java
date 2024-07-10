package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * SRem Command
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:08
 */
public class SRemCommand implements Command {
    @Override
    public String name() {
        return "SREM";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        String key = getKey(args);
        String[] values = getValues(args);
        return Reply.integer(cache.srem(key, values));
    }
}
