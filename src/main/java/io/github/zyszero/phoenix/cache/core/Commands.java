package io.github.zyszero.phoenix.cache.core;

import io.github.zyszero.phoenix.cache.command.*;

import java.util.HashMap;
import java.util.Map;

/**
 * register commands
 *
 * @Author: zyszero
 * @Date: 2024/7/2 5:38
 */
public class Commands {
    public static Map<String, Command> ALL = new HashMap<>();


    static {
        initCommands();
    }

    private static void initCommands() {
        // common commands
        register(new PingCommand());
        register(new InfoCommand());
        register(new CommandCommand());

        // string commands
        register(new GetCommand());
        register(new SetCommand());
        register(new StrlenCommand());
        register(new DelCommand());
        register(new ExistsCommand());
        register(new IncrCommand());
        register(new DecrCommand());
        register(new MGetCommand());
        register(new MSetCommand());


        // list commands
        // Lpush, Rpush, Lpop, Rpop, Llen, Lindex, Lrange
        register(new LPushCommand());
        register(new LPopCommand());
        register(new RPushCommand());
        register(new RPopCommand());
        register(new LLenCommand());
        register(new LIndexCommand());
        register(new LRangeCommand());


        // set
        register(new SAddCommand());
        register(new SMembersCommand());
        register(new SRemCommand());
        register(new SCardCommand());
        register(new SPopCommand());
        register(new SIsmemberCommand());

        // hash：hset hget hlen hgetall  hmset hmget
        register(new HSetCommand());
        register(new HGetCommand());
        register(new HGetallCommand());
        register(new HLenCommand());
        register(new HDelCommand());
        register(new HExistsCommand());
        register(new HMGetCommand());

        // zset
        register(new ZAddCommand());
        register(new ZCardCommand());
        register(new ZScoreCommand());
        register(new ZCountCommand());
        register(new ZRankCommand());
        register(new ZRemCommand());
    }

    public static void register(Command command) {
        ALL.put(command.name(), command);
    }


    public static Command get(String name) {
        return ALL.get(name);
    }


    public static String[] getCommandNames() {
        return ALL.keySet().toArray(new String[0]);
    }
}
