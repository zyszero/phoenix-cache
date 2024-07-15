package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * HGetall command.
 * @Author: zyszero
 * @Date: 2024/7/15 22:26
 */
public class HGetallCommand implements Command {
    @Override
    public String name() {
        return "HGETALL";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        String key = getKey(args);
        return Reply.array(cache.hgetall(key));
    }
}
