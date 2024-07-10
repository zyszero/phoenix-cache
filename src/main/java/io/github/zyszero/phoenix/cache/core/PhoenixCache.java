package io.github.zyszero.phoenix.cache.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cache.Cache;

import java.util.*;

/**
 * cache entries
 *
 * @Author: zyszero
 * @Date: 2024/6/24 23:12
 */
public class PhoenixCache {
    Map<String, CacheEntry<?>> map = new HashMap<>();


    // ========================== 1. String ==========================
    public String get(String key) {
        CacheEntry<?> entry = map.get(key);
        if (entry != null && entry.getValue() instanceof String) {
            CacheEntry<String> typedEntry = (CacheEntry<String>) entry;
            // 使用typedEntry
            return typedEntry.getValue();
        } else {
            // TODO 处理类型不匹配的情况
            return null;
        }

    }


    public void set(String key, String value) {
        map.put(key, new CacheEntry<>(value));
    }

    public int del(String... keys) {
        return keys == null ? 0
                : (int) Arrays.stream(keys).map(map::remove).filter(Objects::nonNull).count();
    }

    public int exists(String... keys) {
        return keys == null ? 0
                : (int) Arrays.stream(keys).map(map::containsKey).filter(x -> x).count();
    }


    public String[] mget(String... keys) {
        return keys == null ? new String[0]
                : Arrays.stream(keys).map(this::get).toArray(String[]::new);
    }


    public void mset(String[] keys, String[] values) {
        if (keys == null || keys.length == 0) {
            return;
        }

        for (int i = 0; i < keys.length; i++) {
            set(keys[i], values[i]);
        }
    }


    public int incr(String key) {
        String str = get(key);
        int val = 0;
        if (str != null) {
            val = Integer.parseInt(str);
        }
        val++;
        set(key, String.valueOf(val));
        return val;
    }

    public int decr(String key) {
        String str = get(key);
        int val = 0;
        if (str != null) {
            val = Integer.parseInt(str);
        }
        val--;
        set(key, String.valueOf(val));
        return val;
    }


    public int strlen(String key) {
        String str = get(key);
        return str == null ? 0 : str.length();
    }

    // ========================== 1. String end ==========================

    // ========================== 2. list ==========================
    public Integer lpush(String key, String... values) {
        CacheEntry<List<String>> entry = (CacheEntry<List<String>>) map.get(key);
        if (entry == null) {
            entry = new CacheEntry<>(new LinkedList<>());
            this.map.put(key, entry);
        }
        LinkedList<String> exist = (LinkedList<String>) entry.getValue();
        Arrays.stream(values).forEach(exist::addFirst);
        return values.length;
    }

    public String[] lpop(String key, int count) {
        CacheEntry<List<String>> entry = (CacheEntry<List<String>>) map.get(key);
        if (entry == null) {
            return null;
        }
        LinkedList<String> exist = (LinkedList<String>) entry.getValue();
        if (exist == null) {
            return null;
        }
        int len = Math.min(count, exist.size());
        String[] ret = new String[len];
        int index = 0;
        while (index < len) {
            ret[index++] = exist.removeFirst();
        }
        return ret;
    }

    public Integer rpush(String key, String[] values) {
        CacheEntry<List<String>> entry = (CacheEntry<List<String>>) map.get(key);
        if (entry == null) {
            entry = new CacheEntry<>(new LinkedList<>());
            this.map.put(key, entry);
        }
        LinkedList<String> exist = (LinkedList<String>) entry.getValue();
        exist.addAll(List.of(values));
        return values.length;
    }

    public String[] rpop(String key, int count) {
        CacheEntry<List<String>> entry = (CacheEntry<List<String>>) map.get(key);
        if (entry == null) {
            return null;
        }
        LinkedList<String> exist = (LinkedList<String>) entry.getValue();
        if (exist == null) {
            return null;
        }
        int len = Math.min(count, exist.size());
        String[] ret = new String[len];
        int index = 0;
        while (index < len) {
            ret[index++] = exist.removeLast();
        }
        return ret;
    }


    public String[] lrange(String key, int start, int end) {
        CacheEntry<List<String>> entry = (CacheEntry<List<String>>) map.get(key);
        if (entry == null) {
            return null;
        }
        LinkedList<String> exist = (LinkedList<String>) entry.getValue();
        if (exist == null) {
            return null;
        }
        if (start < 0 || end < start) {
            return null;
        }

        int size = exist.size();
        if (start >= size) {
            return null;
        }

        if (end >= size) {
            end = size - 1;
        }

        int len = Math.min(size, end - start + 1);
        String[] ret = new String[len];

        for (int i = 0; i < ret.length; i++) {
            ret[i] = exist.get(start + i);
        }
        return ret;
    }

    public Integer llen(String key) {
        CacheEntry<List<String>> entry = (CacheEntry<List<String>>) map.get(key);
        if (entry == null || entry.getValue() == null) {
            return 0;
        }
        List<String> exist = entry.getValue();
        return exist.size();
    }

    public String lindex(String key, int index) {
        CacheEntry<List<String>> entry = (CacheEntry<List<String>>) map.get(key);
        if (entry == null) {
            return null;
        }
        List<String> exist = entry.getValue();
        if (exist == null || exist.isEmpty()) {
            return null;
        }
        if (index >= exist.size() || index < 0) {
            return null;
        }
        return exist.get(index);
    }

    // ========================== 2. list end ==========================


    public Integer sadd(String key, String[] values) {
        CacheEntry<LinkedHashSet<String>> entry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        if (entry == null) {
            entry = new CacheEntry<>(new LinkedHashSet<>());
            this.map.put(key, entry);
        }
        LinkedHashSet<String> exist = entry.getValue();
        return exist.addAll(List.of(values)) ? values.length : 0;
    }

    public String[] smembers(String key) {
        CacheEntry<LinkedHashSet<String>> entry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        if (entry == null) {
            return null;
        }
        LinkedHashSet<String> exist = entry.getValue();
        return exist.toArray(String[]::new);
    }

    Random random = new Random();

    public String[] spop(String key, int count) {
        CacheEntry<LinkedHashSet<String>> entry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        if (entry == null) {
            return null;
        }
        LinkedHashSet<String> exist = entry.getValue();
        if (exist == null) {
            return null;
        }
        int len = Math.min(count, exist.size());
        String[] ret = new String[len];
        int index = 0;
        while (index < len) {
            String obj = exist.toArray(String[]::new)[random.nextInt(exist.size())];
            exist.remove(obj);
            ret[index++] = obj;
        }
        return ret;
    }

    public Integer scard(String key) {
        CacheEntry<LinkedHashSet<String>> entry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        if (entry == null) {
            return 0;
        }
        LinkedHashSet<String> exist = entry.getValue();
        return exist == null || exist.isEmpty() ? 0 : exist.size();
    }

    public Integer sismember(String key, String value) {
        CacheEntry<LinkedHashSet<String>> entry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        if (entry == null) {
            return 0;
        }
        LinkedHashSet<String> exist = entry.getValue();
        return exist.contains(value) ? 1 : 0;
    }

    public Integer srem(String key, String[] values) {
        CacheEntry<LinkedHashSet<String>> entry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        if (entry == null) {
            return 0;
        }
        LinkedHashSet<String> exist = entry.getValue();
        return values == null ? 0 : (int) Arrays.stream(values).map(exist::remove).filter(x -> x).count();
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CacheEntry<T> {
        private T value;
    }
}
