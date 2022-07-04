package net.choge.myapp.api.publisher.impl;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Segment;
import com.amazonaws.xray.entities.TraceID;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import net.choge.myapp.api.entity.TodoItemEntity;
import net.choge.myapp.api.publisher.TodoEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Log4j2
public class TodoEventSNSPublisher implements TodoEventPublisher {
    private final SnsClient client;

    @Value("${app.publisher.eventArn:arn:aws:sns:ap-northeast-1:641855268550:mydemo-test-sns-topic}")
    private String topicArn;

    private static final ObjectMapper mapper = new ObjectMapper();

    public TodoEventSNSPublisher(@Autowired SnsClient client) {
        this.client = client;
    }

    @Override
    public boolean publishTodoEvent(TodoItemEntity todo) {
        PublishResponse res = client.publish(req -> {
            try {
                req
                    .topicArn(topicArn)
                    .message(mapper.writeValueAsString(todo))
                    .messageAttributes(extractTraceInformation())
                    .build();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("Published an event to {} with {}", topicArn, res.messageId());

        return true;
    }

    private Map<String, MessageAttributeValue> extractTraceInformation() {
        Map<String, MessageAttributeValue> traceInfo = new HashMap<>();
        Segment segment = AWSXRay.getCurrentSegment();

        if (Objects.isNull(segment)) {
            return traceInfo;
        }

        traceInfo.put("TraceId",
            MessageAttributeValue.builder().stringValue(segment.getTraceId().toString()).dataType("String").build());
        traceInfo.put("SegmentId",
            MessageAttributeValue.builder().stringValue(segment.getId()).dataType("String").build());
        return traceInfo;
    }
}
