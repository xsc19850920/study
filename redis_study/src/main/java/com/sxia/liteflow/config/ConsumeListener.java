package com.sxia.liteflow.config;
 
import com.sxia.liteflow.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;
 
import java.util.Map;
 
/**
 * @date ：Created in 2022/5/19 10:15
 * @description：消费监听，自动ack
 * xgroup create redis-stream-01 group-a 0 mkstream
 * xgroup create redis-stream-01 group-b 0 mkstream
 */
@Slf4j
@Component
public class ConsumeListener implements StreamListener<String, MapRecord<String, String, String>> {
    private static RedisService redisService;
    @Autowired
    public void setRedisService(RedisService redisService) {
        ConsumeListener.redisService = redisService;
    }

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        String stream = message.getStream();
        RecordId id = message.getId();
        Map<String, String> map = message.getValue();
        log.info("[自动] group:[group-b] 接收到一个消息 stream:[{}],id:[{}],value:[{}]", stream, id, map);
        //消费完毕删除该条消息
        ConsumeListener.redisService.del(stream, id.getValue());
    }
}