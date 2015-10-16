package todo.domain.repository.todo;

import java.util.Collection;

import todo.domain.model.Todo;

public interface TodoRepository {

    /*
     * TODOの1件取得
     */
    Todo findOne(String todoId);

    /*
     * TODOの全件取得
     */
    Collection<Todo> findAll();

    /*
     * TODOの１件作成
     */
    void create(Todo todo);

    /*
     * TODOの１件更新
     */
    boolean update(Todo todo);

    /*
     * TODOの１件削除
     */
    void delete(Todo todo);

    /*
     * 完了、未完了のTODOの取得（引数で完了、未完了をわける）
     */
    long countByFinished(boolean finished);

}
