package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * HSet command.
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:43
 */
public class HSetCommand implements Command {
    @Override
    public String name() {
        return "HSET";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        String key = getKey(args);
        String[] hkeys = getHKeys(args);
        String[] hvals = getHVals(args);
        return Reply.integer(cache.hset(key, hkeys, hvals));
    }




}
