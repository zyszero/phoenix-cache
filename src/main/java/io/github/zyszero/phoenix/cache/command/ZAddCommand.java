package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

import java.util.Arrays;

/**
 * ZAdd Command
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:08
 */
public class ZAddCommand implements Command {
    @Override
    public String name() {
        return "ZADD";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        String key = getKey(args);
        String[] scores = getHKeys(args);
        String[] values = getHVals(args);
        return Reply.integer(cache.zadd(key, values, toDouble(scores)));
    }

    private double[] toDouble(String[] scores) {
        return Arrays.stream(scores).mapToDouble(Double::parseDouble).toArray();
    }
}
