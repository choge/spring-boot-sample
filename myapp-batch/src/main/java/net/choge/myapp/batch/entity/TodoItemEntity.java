package net.choge.myapp.batch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.ZonedDateTime;

@DynamoDbBean
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoItemEntity {
    @Getter(onMethod = @__({@DynamoDbPartitionKey}))
    private String userId;

    @Getter(onMethod = @__({@DynamoDbSortKey}))
    private String id;
    private String content;

    private long dueAsUnixtime;
    private String status;
}