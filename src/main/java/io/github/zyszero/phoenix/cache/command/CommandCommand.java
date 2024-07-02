package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.Commands;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * Command command
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:01
 */
public class CommandCommand implements Command {
    @Override
    public String name() {
        return "COMMAND";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        return Reply.array(Commands.getCommandNames());
    }
}
