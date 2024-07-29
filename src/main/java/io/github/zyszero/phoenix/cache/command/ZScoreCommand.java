package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * ZScore Command
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:08
 */
public class ZScoreCommand implements Command {
    @Override
    public String name() {
        return "ZSCORE";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        String key = getKey(args);
        String value = getValue(args);
        Double zscore = cache.zscore(key, value);
        return Reply.string(zscore == null ? null : zscore.toString());
    }

}
