package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * Incr command.
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:43
 */
public class IncrCommand implements Command {
    @Override
    public String name() {
        return "INCR";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        String key = getKey(args);
        try {
            return Reply.integer(cache.incr(key));
        } catch (NumberFormatException nfe) {
            return Reply.error("NFE " + key + " value[" + cache.get(key) + "] is not an integer.");
        }
    }
}
