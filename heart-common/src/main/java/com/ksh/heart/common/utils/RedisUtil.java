package com.ksh.heart.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /* -------------------String相关操作------------------------- */

    /**
     * 删除
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 是否存在key
     */
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 设置指定 key 的值
     * expire单位毫秒
     */
    public void set(String key, String value, long expire) {
        if(expire > 0){
            stringRedisTemplate.opsForValue().set(key, value, expire, TimeUnit.MILLISECONDS);
        }else{
            stringRedisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * 获取指定 key 的值
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 批量获取
     */
    public List<String> multiGet(Collection<String> keys) {
        return stringRedisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 批量添加
     */
    public void multiSet(Map<String, String> maps) {
        stringRedisTemplate.opsForValue().multiSet(maps);
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
     * @param unit
     *            时间单位, 天:TimeUnit.DAYS 小时:TimeUnit.HOURS 分钟:TimeUnit.MINUTES
     *            秒:TimeUnit.SECONDS 毫秒:TimeUnit.MILLISECONDS
     */
    public void setEx(String key, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 增加(自增长), 负数则为自减
     */
    public Long incrBy(String key, long increment) {
        return stringRedisTemplate.opsForValue().increment(key, increment);
    }

    /* -------------------set相关操作------------------------- */

    /**
     * set添加元素
     */
    public Long sAdd(String key, String... values) {
        return stringRedisTemplate.opsForSet().add(key, values);
    }

    /**
     * set移除元素
     */
    public Long sRemove(String key, Object... values) {
        return stringRedisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 判断集合是否包含value

     */
    public Boolean sIsMember(String key, Object value) {
        return stringRedisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取集合所有元素
     */
    public Set<String> setMembers(String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    /* -------------------RedisTemplate相关操作------------------------- */

    /**
     * 指定缓存失效时间
     * @param time 时间(秒)
     */
    public void expire(String key,long time){
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 删除缓存
     */
    @SuppressWarnings("unchecked")
    public void del(String ... key){
        if(key!=null&&key.length>0){
            if(key.length==1){
                redisTemplate.delete(key[0]);
            }else{
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 普通缓存获取
     */
    public Object getObj(String key){
        return key==null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     */
    public void setObj(String key,Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入并设置时间
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public void setObj(String key,Object value,long time){
        if(time>0){
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        }else{
            setObj(key, value);
        }
    }

    /**
     * 获取hashKey对应的所有键值
     */
    public Map<Object,Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     */
    public void hmset(String key, Map<String,Object> map){
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * HashSet 并设置时间
     * @param time 时间(秒)
     */
    public void hmset(String key, Map<String,Object> map, long time){
        redisTemplate.opsForHash().putAll(key, map);
        expire(key, time);
    }

    /**
     * 删除hash表中的值
     */
    public void hdel(String key, Object... item){
        redisTemplate.opsForHash().delete(key,item);
    }

    /**
     * 判断hash表中是否有该项的值
     */
    public Boolean hHasKey(String key, String item){
        return redisTemplate.opsForHash().hasKey(key, item);
    }
}
