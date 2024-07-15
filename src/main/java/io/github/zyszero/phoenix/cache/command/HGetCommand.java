package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * HGet command.
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:43
 */
public class HGetCommand implements Command {
    @Override
    public String name() {
        return "HGET";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        String key = getKey(args);
        String hkey = getValue(args);
        return Reply.bulkString(cache.hget(key, hkey));
    }




}
