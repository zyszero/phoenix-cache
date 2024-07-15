package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * SIsmember Command
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:08
 */
public class SIsmemberCommand implements Command {
    @Override
    public String name() {
        return "SISMEMBER";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        String key = getKey(args);
        String value = getValue(args);
        return Reply.integer(cache.sismember(key, value));
    }
}
