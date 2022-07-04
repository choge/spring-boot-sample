package net.choge.myapp.batch.subscriber;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Segment;
import com.amazonaws.xray.entities.TraceID;
import io.awspring.cloud.messaging.listener.Acknowledgment;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.log4j.Log4j2;
import net.choge.myapp.batch.entity.RawMessageEntity;
import net.choge.myapp.batch.entity.TodoItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.UUID;

@Component
@Log4j2
public class TodoBatchSubscriber {

    DynamoDbEnhancedClient client;
    DynamoDbTable<RawMessageEntity> table;

    private static final String COMPONENT_NAME = "myapp-batch";

    public TodoBatchSubscriber(@Autowired DynamoDbEnhancedClient client,
        @Value("${app.tables.todo:mydemo-test-ddb-table-another}") String tableName) {
            this.client = client;
            table = client.table(tableName, TableSchema.fromBean(RawMessageEntity.class));
    }

    @SqsListener(value = {"mydemo-test-sqs-queue"}, deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void queueListner(TodoItemEntity todo,
                             @Header("TraceId") String traceId,
                             @Header("SegmentId") String segmentId,
                             Acknowledgment acknowledgment) {
        try (Segment segment = AWSXRay.beginSegment(COMPONENT_NAME, TraceID.fromString(traceId), segmentId)) {
            log.info(todo);

            String messageId = UUID.randomUUID().toString();
            table.putItem(new RawMessageEntity(messageId, todo.toString()));

            acknowledgment.acknowledge();
            // segment.end() is not required as it implements AutoClosable or something
        }

    }

}
