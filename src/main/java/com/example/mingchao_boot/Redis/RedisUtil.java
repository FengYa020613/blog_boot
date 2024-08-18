package com.example.mingchao_boot.Redis;

import com.example.mingchao_boot.Aspect.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtil {

    @Autowired
    @Qualifier("redisTemplete")
    private RedisTemplate redisTemplate;


    /**
     * 设置验证码 过期时间 5min
     * @param phone 手机号
     * @param code  验证码
     * @param type  1 为登录验证码 2为换绑验证码
     * @return
     */
    public String setCode(String phone,String code,int type){
        redisTemplate.opsForValue().set(phone+"code"+type,code);
        redisTemplate.expire(phone+"code"+type,5, TimeUnit.MINUTES);
        return "success";
    }

    /**
     * 获取验证码
     * @param phone 手机号
     * @param type 1 为登录验证码 2为换绑验证码
     * @return
     */
    public String getCode(String phone,int type){
       return (String) redisTemplate.opsForValue().get(phone+"code"+type);
    }

    /**
     * 删除验证码
     * @param phone
     * @return
     */
    public String delCode(String phone,int type){
        redisTemplate.delete(phone+"code"+type);
        return "success";
    }

    /**
     * 设置token
     * @param key
     * @param value
     */
    public void setToken(String key, String value) {
        redisTemplate.opsForValue().set(key,value);
        redisTemplate.expire(key,7, TimeUnit.DAYS);
    }

    /**
     * 设置key 和 value（String类型）
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key,value);
        Log.info("向Redis插入数据:key="+key+";value="+value);
    }

    public void setUser(String key, String value) {
        redisTemplate.opsForValue().set(key,value);
        redisTemplate.expire(key,7, TimeUnit.DAYS);
        Log.info("向Redis插入数据:key="+key+";value="+value);
    }

    /**
     * 删除key
     * @param key
     */
    public void del(String key) {
        redisTemplate.delete(key);
        Log.info("已从Redis删除key="+key+"的值");
    }

    /**
     * 获取key的value
     * @param key
     * @return
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取int类型
     * @param key
     * @return
     */
    public Integer getInteger(String key) {
        Log.info("已从Redis获取key="+key+"的Integer值");
        return (Integer) redisTemplate.opsForValue().get(key);
    }
    public void setInteger(String key, int value) {
        redisTemplate.opsForValue().set(key,value);
        Log.info("向Redis插入数据:key="+key+";value="+value);
    }
    public void incrValue(String key) {
        Integer value = (Integer) redisTemplate.opsForValue().get(key);
        if (value != null&&value >= 0) {
            redisTemplate.opsForValue().increment(key, 1);
        }else{
            setInteger(key, 1);
        }
    }
    public void decrValue(String key) {
        Integer value = (Integer) redisTemplate.opsForValue().get(key);
        if (value != null&&value > 0) {
            redisTemplate.opsForValue().decrement(key, 1);
        }else{
            setInteger(key, 0);
        }
    }

    /**
     * 获取set类型
     * @param key
     * @return
     */
    public Set<String> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }
    public void addSetMember(String key, String value) {
        redisTemplate.opsForSet().add(key,value);
        log.info("redis插入set:key="+key+";value="+value);
    }
    public void deleteSetMember(String key, String value) {
        redisTemplate.opsForSet().remove(key,value);
        log.info("redis删除set中:key="+key+";value="+value+"的值");
    }
    public void setSet(String key, Set<String> set) {
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            addSetMember(key, iterator.next());
        }
    }


}
