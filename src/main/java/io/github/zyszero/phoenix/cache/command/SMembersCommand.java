package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * SAdd Command
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:08
 */
public class SMembersCommand implements Command {
    @Override
    public String name() {
        return "SMEMBERS";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        String key = getKey(args);
        return Reply.array(cache.smembers(key));
    }
}