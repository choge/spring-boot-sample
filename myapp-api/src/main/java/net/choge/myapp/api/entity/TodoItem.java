package net.choge.myapp.api.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoItem {
    @Getter(onMethod = @__({@DynamoDbPartitionKey}))
    private String userId;

    @Getter(onMethod = @__({@DynamoDbSortKey}))
    private String id;
    private String content;

    private long dueAsUnixtime;
    private String status;
}