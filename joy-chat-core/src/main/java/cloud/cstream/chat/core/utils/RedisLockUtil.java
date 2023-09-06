package cloud.cstream.chat.core.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;

public class RedisLockUtil {

    private final String LOCK_PREFIX = "redis_lock_";

    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisScript<Boolean> lockScript;

    private final RedisScript<Boolean> unlockScript;

    public RedisLockUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        // 加载 Lua 脚本
        String lockScriptStr = "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then return redis.call('pexpire', KEYS[1], ARGV[2]) else return false end";
        String unlockScriptStr = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return false end";
        lockScript = new DefaultRedisScript<>(lockScriptStr, Boolean.class);
        unlockScript = new DefaultRedisScript<>(unlockScriptStr, Boolean.class);
    }

    /**
     * 尝试获取锁
     *
     * @param key        锁的名称
     * @param expireTime 锁的过期时间，单位为毫秒
     * @return 获取锁成功返回 true，否则返回 false
     */
    public boolean tryLock(String key, long expireTime) {
        String lockKey = LOCK_PREFIX + key;
        String value = Thread.currentThread().getName();
        Boolean result = redisTemplate.execute(lockScript, Collections.singletonList(lockKey), value, expireTime);
        return result != null && result;
    }

    /**
     * 释放锁
     *
     * @param key 锁的名称
     */
    public void releaseLock(String key) {
        String lockKey = LOCK_PREFIX + key;
        String value = Thread.currentThread().getName();
        redisTemplate.execute(unlockScript, Collections.singletonList(lockKey), value);
    }

    /**
     * 尝试获取锁，如果获取失败则等待一段时间后重试
     *
     * @param key         锁的名称
     * @param expireTime  锁的过期时间，单位为毫秒
     * @param waitTime    等待时间，单位为毫秒
     * @param maxRetryNum 最大重试次数
     * @return 获取锁成功返回 true，否则返回 false
     */
    public boolean tryLockWithRetry(String key, long expireTime, long waitTime, int maxRetryNum) throws InterruptedException {
        int retryNum = 0;
        while (retryNum <= maxRetryNum) {
            boolean locked = tryLock(key, expireTime);
            if (locked) {
                return true;
            } else {
                retryNum++;
                Thread.sleep(waitTime);
            }
        }
        return false;
    }
}
