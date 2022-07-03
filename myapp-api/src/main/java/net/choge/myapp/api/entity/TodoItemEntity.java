package net.choge.myapp.api.entity;

import lombok.*;
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
    private ZonedDateTime due;
    private String status;
}