package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * ZRank Command
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:08
 */
public class ZRankCommand implements Command {
    @Override
    public String name() {
        return "ZRANK";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        String key = getKey(args);
        String value = getValue(args);
        Integer zrank = cache.zrank(key, value);
        return Reply.integer(zrank == null ? -1 : zrank);
    }

}
