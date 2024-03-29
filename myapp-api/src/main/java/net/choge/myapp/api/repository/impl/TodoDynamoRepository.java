package net.choge.myapp.api.repository.impl;

import lombok.extern.log4j.Log4j2;
import net.choge.myapp.api.entity.TodoItem;
import net.choge.myapp.api.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Log4j2
public class TodoDynamoRepository implements TodoRepository {

    private final DynamoDbTable<TodoItem> table;

    public TodoDynamoRepository(@Autowired DynamoDbEnhancedClient dynamoDbEnhancedClient,
                                @Value("${app.tables.todo:mydemo-test-ddb-table-todo}") String tableName) {
        table = dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(TodoItem.class));
        log.info("Initialized {} for table {}", TodoDynamoRepository.class, tableName);
    }

    @Override
    public TodoItem retrieveTodoItem(String userId, String id) {
        return table.getItem(Key.builder().partitionValue(userId).sortValue(id).build());
    }

    @Override
    public List<TodoItem> loadTodoItems(String userId) {
        var iter = table.query(r -> r.queryConditional(
            QueryConditional.keyEqualTo(
                Key.builder().partitionValue(userId).build()))
        );
        return iter.items().stream().collect(Collectors.toList());
    }

    @Override
    public boolean createOrUpdateTodoItem(TodoItem item) {
        table.putItem(item);
        return true;
    }
}
