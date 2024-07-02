package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * LPush Command
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:08
 */
public class LPushCommand implements Command {
    @Override
    public String name() {
        return "LPUSH";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        String key = getKey(args);
        String[] values = getParamsNoKey(args);
        return Reply.integer(cache.lpush(key, values));
    }
}