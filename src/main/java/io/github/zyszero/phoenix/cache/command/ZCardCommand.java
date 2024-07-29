package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

import java.util.Arrays;

/**
 * ZCard Command
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:08
 */
public class ZCardCommand implements Command {
    @Override
    public String name() {
        return "ZCARD";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        String key = getKey(args);
        return Reply.integer(cache.zcard(key));
    }

}
