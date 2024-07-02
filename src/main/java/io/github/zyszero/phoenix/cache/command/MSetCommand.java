package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * MGet command.
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:43
 */
public class MSetCommand implements Command {
    @Override
    public String name() {
        return "MSET";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        String[] keys = getKeys(args);
        String[] values = getValues(args);
        cache.mset(keys, values);
        return Reply.string(OK);
    }
}
