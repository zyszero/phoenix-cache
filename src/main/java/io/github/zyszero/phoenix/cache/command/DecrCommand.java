package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * Decr command.
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:43
 */
public class DecrCommand implements Command {
    @Override
    public String name() {
        return "DECR";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        String key = getKey(args);
        try {
            return Reply.integer(cache.decr(key));
        } catch (NumberFormatException nfe) {
            return Reply.error("NFE " + key + " value[" + cache.get(key) + "] is not an integer.");
        }
    }
}
