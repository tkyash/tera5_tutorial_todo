<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Todo List</title>
<!-- (1) -->

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/app/css/styles.css"
	type="text/css">

</head>
<body>
	<h1>Todo List</h1>

	<div id="todoForm">

		<!-- 2-1 -->
		<t:messagesPanel />

		<!-- (1) -->
		<form:form action="${pageContext.request.contextPath}/todo/create"
			method="post" modelAttribute="todoForm">
			<!-- (2) -->
			<form:input path="todoTitle" />

			<!-- 2-2 -->
			<form:errors path="todoTitle" />

			<form:button>Create Todo</form:button>
		</form:form>
	</div>
	<hr />
	<div id="todoList">
		<ul>
			<!-- (3) -->
			<c:forEach items="${todos}" var="todo">
				<li><c:choose>
						<c:when test="${todo.finished}">
							<!-- (4) -->
							<span class="strike"> <!-- (5) --> ${f:h(todo.todoTitle)}
							</span>
						</c:when>
						<c:otherwise>
                            ${f:h(todo.todoTitle)}

                            <!-- 3-1 -->
							<form:form
								action="${pageContext.request.contextPath}/todo/finish"
								method="post" modelAttribute="todoForm"
								cssStyle="display:inline-block;">

								<!-- 3-2 -->
								<form:hidden path="todoId" value="${f:h(todo.todoId)}" />
								<form:button>Finish</form:button>

							</form:form>

						</c:otherwise>
					</c:choose> <form:form
						action="${pageContext.request.contextPath }/todo/delete"
						method="post" modelAttribute="todoForm"
						cssStyle="display:inline-block;">
						<form:hidden path="todoId" value="${f:h(todo.todoId)}" />
						<form:button>Delete</form:button>
						<form:errors path="todoId" />
					</form:form></li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>