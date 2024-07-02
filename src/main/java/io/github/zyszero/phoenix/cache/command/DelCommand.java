package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * Del command.
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:43
 */
public class DelCommand implements Command {
    @Override
    public String name() {
        return "DEL";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        String[] keys = getParams(args);
        return Reply.integer(cache.del(keys));
    }
}
