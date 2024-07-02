package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * Info command
 *
 * @Author: zyszero
 * @Date: 2024/7/2 5:36
 */
public class InfoCommand implements Command {

    private static final String INFO = "PhoenixCache server[v1.0.0], created by zyszero." + CRLF
            + "Mock Redis Server at 2024-07-02 in Guangzhou." + CRLF;

    @Override
    public String name() {
        return "INFO";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        return Reply.bulkString(INFO);
    }
}
