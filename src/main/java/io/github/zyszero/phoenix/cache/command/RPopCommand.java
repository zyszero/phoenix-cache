package io.github.zyszero.phoenix.cache.command;

import io.github.zyszero.phoenix.cache.core.Command;
import io.github.zyszero.phoenix.cache.core.PhoenixCache;
import io.github.zyszero.phoenix.cache.core.Reply;

/**
 * LPush Command
 *
 * @Author: zyszero
 * @Date: 2024/7/2 6:08
 */
public class RPopCommand implements Command {
    @Override
    public String name() {
        return "RPOP";
    }

    @Override
    public Reply<?> exec(PhoenixCache cache, String[] args) {
        if (args.length < 1) {
            return Reply.error("Insufficient arguments or invalid format.");
        }

        String key = getKey(args);
        int count = 1;

        if (args.length > 6) {
            String value = getValue(args);
            try {
                count = Integer.parseInt(value);
                if (count <= 0) {
                    return Reply.error("The count value must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                return Reply.error("Invalid count value: " + value + ". Please provide a valid integer.");
            }
            return Reply.array(cache.rpop(key, count));
        }
        String[] lpopResult = cache.rpop(key, count);
        if (lpopResult == null || lpopResult.length == 0) {
            return Reply.bulkString(null);
        }
        return Reply.bulkString(lpopResult[0]);
    }

}
