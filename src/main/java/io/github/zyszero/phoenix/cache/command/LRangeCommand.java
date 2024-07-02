package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * LRange Command
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:08
 */
public class LRangeCommand implements Command {
    @Override
    public String name() {
        return "LRANGE";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        String key = getKey(args);
        String[] values = getParamsNoKey(args);
        int start = Integer.parseInt(values[0]);
        int end = Integer.parseInt(values[1]);
        return Reply.array(cache.lrange(key, start, end));
    }

}
