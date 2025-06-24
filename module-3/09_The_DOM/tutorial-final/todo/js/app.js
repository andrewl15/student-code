// Wait until the DOM has loaded.
document.addEventListener('DOMContentLoaded', function() {

  // Locate the existing todo panel
  const todoPanel = document.getElementById('todo-panel');

  // Single todo list for testing purposes
  const todoLists = [
    {
      id: 1,
      title: 'My Morning Routine',
      toDos: [
        { id: 1, task: 'Wake up', completed: false },
        { id: 2, task: 'Brush Teeth', completed: false },
        { id: 3, task: 'Shower', completed: false },
        { id: 4, task: 'Get Dressed', completed: false },
        { id: 5, task: 'Take care of pets', completed: false },
        { id: 6, task: 'Make morning beverage', completed: false },
        { id: 7, task: 'Eat breakfast', completed: false },
        { id: 8, task: 'Pack lunch', completed: false },
        { id: 9, task: 'Drive to work', completed: false }
      ]
    }
  ];

  // Display the todo list's title and to dos and confirm in dev tools
  console.log(todoLists[0].title);
  console.log(todoLists[0].toDos);

  /**
   * Add and display a given todo list.
   * @param {*} todoList to add and display
   */
  function addToDoList(todoList) {

    // Clear todo panel
    todoPanel.innerText = '';

    // Create and append todo list div to todo panel
    const todoListDiv = document.createElement('div');
    todoListDiv.classList.add('todo-list');
    todoPanel.appendChild(todoListDiv);

    // Create and append todo list title to todoListDiv
    const todoTitle = document.createElement('h1');
    todoTitle.textContent = todoList.title;
    todoListDiv.appendChild(todoTitle);

    // Create and append the todo tasks to the todoListDiv
    const ul = document.createElement('ul');
    todoList.toDos.forEach((todo) => {
      const li = document.createElement('li');
      li.textContent = todo.task;
      ul.appendChild(li);
    });
    todoListDiv.appendChild(ul);

    // Create and append the completion label to the todo panel
    const label = document.createElement('label');
    label.classList.add('todo-completion');
    label.textContent = `Completion: 0 of ${todoList.toDos.length}`;
    todoPanel.appendChild(label);

  }

  /**
   * Add and display a todo list from the server
   */
  function addServerToDoList() {

    // Create and append an error label it there isn't one already
    let errorLabel = document.getElementById('error-label');
    if (errorLabel === null) {
      errorLabel = document.createElement('label');
      errorLabel.setAttribute('id', 'error-label');
      todoPanel.appendChild(errorLabel);
    } else {
      // Clear the existing error label
      errorLabel.textContent = '';
    }

    // Request the todo list
    axios.get('http://127.0.0.1:3000/todos')
      .then((response) => {
        // Todo list found
        const todoList = response.data[0];
        addToDoList(todoList);
      })
      .catch((error) => {
        if (error.response) {
          errorLabel.innerText = `Error Status Text: ${error.response.statusText}`;
        } else if (error.request) {
          errorLabel.innerText = `Network error. ${error.response.data.errors}`;
        } else {
          errorLabel.innerText = error.response.data.message;
        }
      });

  }

  // Display the "My Morning Routine" todoList
  // addToDoList(todoLists[0]);

  // Display my work routine retrieved from server
  addServerToDoList();
});
