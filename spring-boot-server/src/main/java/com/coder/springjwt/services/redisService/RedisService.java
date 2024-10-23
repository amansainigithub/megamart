package com.coder.springjwt.services.redisService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setValue(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Unable to connect Redis Server :: " + e.getMessage());
            log.error("could not store data to redis Server :: ");
        }
    }

    public String getValue(String key) {
      try {
          return (String) redisTemplate.opsForValue().get(key);
      }
      catch (Exception e)
      {
          e.printStackTrace();
          log.error("Unable to connect Redis Server :: " + e.getMessage());
          return null;
      }
    }

    public void deleteKey(String key) {
        try {
            redisTemplate.delete(key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Unable to connect Redis Server :: " + e.getMessage());
            log.error("Key :: " + key +" :: Data Could not be Deleted");
        }
    }
}
