package todo.app.todo;

import java.util.Collection;

import javax.inject.Inject;
import javax.validation.groups.Default;

import org.dozer.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

import todo.app.todo.TodoForm.TodoCreate;
import todo.app.todo.TodoForm.TodoFinish;
import todo.domain.model.Todo;
import todo.domain.service.todo.TodoService;

@Controller
// (1)
@RequestMapping("todo")
// (2)
public class TodoController {

	@Inject
	TodoService todoService;

	@Inject
	// (2-1)
	Mapper beanMapper;

	@ModelAttribute
	// (2)
	public TodoForm setUpForm() {
		TodoForm form = new TodoForm();
		return form;
	}

	@RequestMapping(value = "list")
	// (3)
	public String list(Model model) {

		Collection<Todo> todos = todoService.findAll();

		model.addAttribute("todos", todos); // (4)

		return "todo/list"; // (5)
	}

	// (2-2)
	@RequestMapping(value = "create", method = RequestMethod.POST)
	// (2-3,4) (3-1)
	public String create(
			@Validated({ Default.class, TodoCreate.class }) TodoForm todoForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes attributes) {
		// RedirectAttributesはフラッシュスコープに値を格納する。通常のRequest
		// Scopeより少し長い生存期間を持つスコープです。
		// 通常のHTTP Requestのひとつ先の Requestの時点まで生存しています

		// (2-5)
		if (bindingResult.hasErrors()) {
			return list(model);
		}

		// (2-6)
		Todo todo = beanMapper.map(todoForm, Todo.class);

		try {
			todoService.create(todo);
		} catch (BusinessException e) {

			// (2-7)
			model.addAttribute(e.getResultMessages());

			return list(model);
		}

		// (2-8)
		attributes.addFlashAttribute(ResultMessages.success().add(
				ResultMessage.fromText("Todoを作成しました。")));

		return "redirect:/todo/list";

	}

	// 3-2
	@RequestMapping(value = "finish", method = RequestMethod.POST)
	public String finish(
			@Validated({ Default.class, TodoFinish.class }) TodoForm form,
			BindingResult bindingResult, Model model,
			RedirectAttributes attributes) {

		// 3-4
		if (bindingResult.hasErrors()) {
			return list(model);
		}

		try {
			todoService.finish(form.getTodoId());

		} catch (BusinessException e) {

			// 3-5
			model.addAttribute(e.getResultMessages());
			return list(model);

		}

		attributes.addFlashAttribute(ResultMessages.success().add(
				ResultMessage.fromText("finished successfully")));

		return "redirect:/todo/list";
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(
			@Validated({ Default.class, TodoFinish.class }) TodoForm form,
			BindingResult bindingResult, Model model,
			RedirectAttributes attributes) {

		// 3-4
		if (bindingResult.hasErrors()) {
			return list(model);
		}

		try {
			todoService.delete(form.getTodoId());

		} catch (BusinessException e) {

			// 3-5
			model.addAttribute(e.getResultMessages());
			return list(model);

		}

		attributes.addFlashAttribute(ResultMessages.success().add(
				ResultMessage.fromText("deleted successfully")));

		return "redirect:/todo/list";
	}

}
