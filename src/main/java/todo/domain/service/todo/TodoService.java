package todo.domain.service.todo;

import java.util.Collection;

import todo.domain.model.Todo;


public interface TodoService {

    /*
     * TODOの全件取得
     */
    Collection<Todo> findAll();

    /*
     * TODOの新規作成
     */
    Todo create(Todo todo);

    /*
     * TODOの完了
     */
    Todo finish(String todoId);

    /*
     * TODOの削除
     */
    void delete(String todoId);

}
