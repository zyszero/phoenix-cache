package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * LIndex Command
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:08
 */
public class LIndexCommand implements Command {
    @Override
    public String name() {
        return "LINDEX";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        String key = getKey(args);
        int index = Integer.parseInt(getValue(args));
        return Reply.bulkString(cache.lindex(key, index));
    }
}
