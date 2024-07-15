package io.github.zyszero.phoenix.cache.core;

/**
 * Common interface。
 *
 * @Author: zyszero
 * @Date: 2024/7/2 5:19
 */
public interface Command {

    String CRLF = "\r\n";

    String OK = "OK";

    String name();

    Reply<?> exec(PhoenixCache cache, String[] args);

    /**
     * 获取key
     *
     * @param args
     * @return
     */
    default String getKey(String[] args) {
        return args[4];
    }


    /**
     * 获取value
     *
     * @param args
     * @return
     */
    default String getValue(String[] args) {
        return args[6];
    }


    /**
     * 从命令行参数中提取特定参数键。
     * <p>
     * 此方法假设命令行参数以特定方式排列：先是一个标识符，然后是键值对的序列，
     * 每个键值对由一个键和一个值组成，之间用分隔符隔开。这个方法的目的是从这些参数中提取出所有的键。
     *
     * @param args 命令行参数数组，按照特定顺序排列。
     * @return 一个字符串数组，包含从命令行参数中提取的所有键。
     */
    default String[] getParams(String[] args) {
        // 计算参数键的数量。参数数组从第四个元素开始包含键值对，每对占用两个位置。
        int len = (args.length - 3) / 2;

        // 初始化一个字符串数组用于存储提取的键。
        String[] keys = new String[len];

        // 遍历参数数组，提取键并存储到keys数组中。
        for (int i = 0; i < len; i++) {
            // 根据键值对的排列规律，计算并获取每个键在args数组中的位置。
            keys[i] = args[4 + i * 2];
        }

        // 返回包含所有键的字符串数组。
        return keys;
    }

    default String[] getParamsNoKey(String[] args) {
        // 计算参数键的数量。参数数组从第四个元素开始包含键值对，每对占用两个位置。
        int len = (args.length - 5) / 2;

        // 初始化一个字符串数组用于存储提取的键。
        String[] keys = new String[len];

        // 遍历参数数组，提取键并存储到keys数组中。
        for (int i = 0; i < len; i++) {
            // 根据键值对的排列规律，计算并获取每个键在args数组中的位置。
            keys[i] = args[6 + i * 2];
        }

        // 返回包含所有键的字符串数组。
        return keys;
    }


    /**
     * 从命令行参数中提取键值对中的键。
     * 假设命令行参数按照特定的格式传递，其中每四个参数组成一组键值对，
     * 第一个参数是键，后三个参数是与该键相关的值。这个方法的目的是从这些参数中提取出所有的键。
     *
     * @param args 命令行参数数组，按照特定格式排列，每四个参数为一组键值对。
     * @return 一个字符串数组，包含所有键值对中的键。
     */
    default String[] getKeys(String[] args) {
        // 计算键的数量。每四个参数有一个键，因此通过(args.length - 3) / 4计算键的数量。
        int len = (args.length - 3) / 4;
        // 初始化一个字符串数组用于存储提取的键。
        String[] keys = new String[len];

        // 遍历所有键值对组，提取键。
        for (int i = 0; i < len; i++) {
            // 每组键值对中，键位于第一个参数，通过索引计算找到每个键。
            keys[i] = args[4 + i * 4];
        }
        // 返回包含所有键的数组。
        return keys;
    }


    /**
     * 根据给定的命令行参数获取特定位置的值数组。
     * 此方法假设命令行参数按照特定的间隔排列，每个值占据四个参数位置。
     * 例如，如果args是[arg0, arg1, arg2, value0, arg3, arg4, value1, arg5, arg6, value2, ...]，
     * 则此方法将返回[value0, value1, value2, ...]。
     *
     * @param args 命令行参数数组，按照特定间隔排列值。
     * @return 一个字符串数组，包含从args中提取的值，每个值占据四个参数位置。
     */
    default String[] getValues(String[] args) {
        // 计算args数组中值的数量。每个值占据四个参数位置，起始位置为第6个参数。
        int len = (args.length - 3) / 4;
        // 初始化一个字符串数组，用于存储提取的值。
        String[] values = new String[len];
        // 遍历数组，按照每个值占据四个参数位置的规则，提取值并存储到values数组中。
        for (int i = 0; i < len; i++) {
            // 根据索引计算每个值在args数组中的位置，并将其赋值给values数组。
            values[i] = args[6 + i * 4];
        }
        // 返回包含提取的值的数组。
        return values;
    }


    default String[] getHKeys(String[] args) {
        int len = (args.length - 5) / 4;
        String[] keys = new String[len];
        for (int i = 0; i < len; i++) {
            keys[i] = args[6 + i * 4];
        }
        return keys;
    }

    default String[] getHVals(String[] args) {
        int len = (args.length - 5) / 4;
        String[] vals = new String[len];
        for (int i = 0; i < len; i++) {
            vals[i] = args[8 + i * 4];
        }
        return vals;
    }

}
