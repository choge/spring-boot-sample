package net.choge.myapp.api.publisher.impl;

import lombok.extern.log4j.Log4j2;
import net.choge.myapp.api.entity.TodoItemEntity;
import net.choge.myapp.api.publisher.TodoEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Component
@Log4j2
public class TodoEventSNSPublisher implements TodoEventPublisher {
    private SnsClient client;

    @Value("${app.publisher.eventArn:arn:aws:sns:ap-northeast-1:641855268550:mydemo-test-sns-topic}")
    private String topicArn;

    public TodoEventSNSPublisher(@Autowired SnsClient client) {
        this.client = client;
    }

    @Override
    public boolean publishTodoEvent(TodoItemEntity todo) {
        PublishResponse res = client.publish(req -> req.topicArn(topicArn).message(todo.toString()).build());
        log.info("Published an event to {} with {}", topicArn, res.messageId());

        return true;
    }
}
