# JavaScript Essentials Part 2 tutorial

In this tutorial, you'll work on a task management application that tracks a user's tasks and completion status. The starting code provides a user interface, allowing you to focus on the core objects and modules.

## Getting started

Open the `tutorial` folder in Visual Studio Code.

The `package.json` file lists a dependency:

```json
  "dependencies": {
    "readline-sync": "^1.4.10"
  }
```

This package comes from the [npm Registry](https://www.npmjs.com/). You must install any dependencies before running an application. One way to do this is by opening the Terminal, navigating to the project directory, and running `npm install`. After installing the dependencies, you can run the application.

When you have a `package.json` file, it's common to run applications and other tasks using "scripts" defined there. Open the `package.json` and notice the "scripts" section:

```json
"scripts": {
  "start": "node app.js"
},
```

This means when you run the command `npm run start`, it runs `node app.js`. While you can just run the command `node app.js` directly on the command line, it's better to use the defined "scripts" to run the application, as larger applications may require different commands.

You can start and run the program now, but the menu options display an error until you implement the code in this tutorial.

## Explore starting code

Before you start writing code, it's a good idea to explore the provided code to understand how the code you'll write fits in with everything else:

- `taskManager.js` encapsulates the logic of managing the user's tasks and is where you'll write the code for this tutorial. It currently contains a constructor function and one instance method, but you'll add more to it in this tutorial.

- `app.js` is the application's entry point and contains the logic for the user interface. You'll add some code here to call `TaskManager` functions and handle return values.

- `task.js` contains a constructor function for a `Task` object and exports it.

## Step One: Export `TaskManager`

The purpose of the application is to track and manage a user's tasks. Open the `taskManager.js` file. Find the `TaskManager` constructor function:

```js
function TaskManager() {
  this.tasks = [
    new Task('Write code'),
    new Task('Run tests'),
    new Task('Commit and push changes')
  ];
}
```

The array property `tasks` is pre-populated with a few example tasks using `new` and the `Task` constructor function to create new `Task` objects. Notice the first line in the file imports the `Task` constructor function from another file:

```js
import { Task } from './task.js';
```

Modern JavaScript applications often consist of multiple files, sharing code between files using ES6 module `import` and `export` statements.

Open `task.js` to find the constructor function and the `export` statement that enables you to use in `taskManager.js`:

```js
function Task(taskName) {
  this.id = newId();
  this.taskName = taskName;
  this.done = false;
}

// newId() function

export { Task };
```

> NOTE: The `newId()` function is a helper function that creates new numeric IDs. It's unavailable outside `task.js` because it's not part of the `Task` object and isn't explicitly exported.

Return to `taskManager.js` and add an `export` statement at the end, allowing you to use `TaskManager` in other files:

```js
export { TaskManager };
```

Now you can integrate `TaskManager` into the application. Open `app.js` and add an `import` statement for it at the top of the file:

```js
import { TaskManager } from './taskManager.js';
```

> NOTE: the `import` statement can go before or after the existing `import` for `readlineSync,` but all `import` statements must be at the beginning of a file.

After the `import` statements, add the following line to create a new `TaskManager` instance:

```js
const taskManager = new TaskManager();
```

Now, if you run the application, the menu option for "View Tasks" displays the tasks:

```text
Tasks
--------------------
ID [Done?] Task Name
--------------------
101 [ ] Write code
102 [ ] Run tests
103 [ ] Commit and push changes
--------------------
```

## Step Two: Replace the `for` loop with the `forEach()` method

In `app.js,` a `switch` statement determines which code to run based on the user's selection. The first option, `View Tasks`, calls the `getTasks()` method on the `taskManager` and prints each task to the console in a `for` loop:

```js
for (let i = 0; i < tasks.length; i++) {
  const task = tasks[i];
  console.log(`${task.id} [${task.done ? '✓' : ' '}] ${task.taskName}`);
}
```

For each iteration of the loop, it assigns the current element to `task` and prints its properties to the console.

You can also do this using the `Array` method `forEach()`. This method takes a single parameter—another function called a _callback_—which runs once for each element in the array. Callback functions are typically declared inline, anonymous (unnamed), and use the arrow function syntax.

Replace the `for` loop with a `forEach()`:

```js
tasks.forEach(task => {
  console.log(`${task.id} [${task.done ? '✓' : ' '}] ${task.taskName}`);
});
```

Notice that `task` becomes the parameter passed into the callback function, and although the `console.log()` statement is the same, it's now inside the callback function body.

Start the application again. When you select `View Tasks` it looks the same, but it's now using the `forEach()` method instead of the `for` loop.

## Step Three: Add the `addTask()` method

A necessary feature of a task management application is the ability to add new tasks. The `Add Task` menu option prompts for a task name and passes it to `taskManager.addTask()`, but there isn't an `addTask()` method yet.

In `taskManager.js`, create a new method named `addTask()` that accepts one parameter for the task name. Remember to add it to the `TaskManager` prototype:

```js
TaskManager.prototype.addTask = function(taskName) {

};
```

Attaching the method to an object's `prototype` property makes it accessible from an instance of that object, like the `taskManager` instance you created in `app.js` in Step One. It also allows you to refer to the object instance using the keyword `this`.

Update the method to add a new `Task` object to the `tasks` array. Create a new `Task` object by calling `new Task()` and add it to the array:

```js
TaskManager.prototype.addTask = function(taskName) {
  const newTask = new Task(taskName);
  this.tasks.push(newTask);
};
```

Optionally, you can shorten the method to just one line by passing the new `Task` directly into the `push()` function:

```js
TaskManager.prototype.addTask = function(taskName) {
  this.tasks.push(new Task(taskName));
};
```

You can now run the application, select `Add Task`, and enter a task name. The task then displays when you select the `View Tasks` menu option.

> NOTE: There's no persistent storage, so any tasks you add won't be there when you re-run the application. The task list is reset every time the application starts.

## Step Four: Add a `markTaskDone()` method

Another essential feature of a good task management application is the ability to mark a task complete. The `Mark Task Done` menu option prompts for a task ID and passes it to `taskManager.markTaskDone()`. There isn't a `markTaskDone()` method yet, but you'll add that now.

In `taskManager.js`, create a new instance method named `markTaskDone()` that accepts one parameter for the task ID. Like the `addTask()` method, create `markTaskDone()` on the `prototype` property of `TaskManager`:

```js
TaskManager.prototype.markTaskDone = function(taskId) {

};
```

Each `task` has a `done` property. The `Task` constructor sets the `done` property to `false`. This method must locate the `task` in the array and change its `done` property to `true`.

You could loop through the array looking for the task that matches the ID, but it's better to use the `Array` method `find()`. The `find()` method returns the first element that matches a condition you specify in a callback function. Similar to the `forEach()` method, this callback also takes a single parameter representing the array element:

```js
const foundTask = this.tasks.find(task => task.id === taskId);
```

This condition matches the first element where the `id` property of the `task` array element equals the `taskId` the user provided. If there's a match, that element is the return value and assigned to `task`; if there's no match, it returns `undefined`.

It's a good, defensive programming technique to check if values are `undefined` or `null` before trying to act upon them. Since the `find()` method could return `undefined`, that's what you check for:

```js
const foundTask = this.tasks.find(task => task.id === taskId);
if (foundTask !== undefined) {
  // task found
} else {
  // no matching task found
}
```

If `find()` returns a match, set the `done` property to `true`. If it doesn't return a `task`, there's nothing to act on:

```js
const foundTask = this.tasks.find(task => task.id === taskId);
if (foundTask !== undefined) {
  // task found
  foundTask.done = true;
} else {
  // no matching task found
}
```

Finally, return a boolean indicating to the application whether the process succeeded or not. The function looks like this when you're done:

```js
TaskManager.prototype.markTaskDone = function(taskId) {
  const foundTask = this.tasks.find(task => task.id === taskId);
  if (foundTask !== undefined) {
    foundTask.done = true;
    return true;
  } else {
    return false;
  }
};
```

Run the application again and select `Mark Task Done`. Enter a task ID, like 101, to complete a task, and then select the `View Tasks` menu option to verify it's complete:

```text
Tasks
--------------------
ID [Done?] Task Name
--------------------
101 [✓] Write code
102 [ ] Run tests
103 [ ] Commit and push changes
--------------------
```

> NOTE: Remember, there's no persistent storage, so any changes you make are reset when you re-run the application.

## Step Five: Add a `deleteTask()` method

Finally, the application needs the ability to delete a task if it isn't required. The `Delete Task` menu option prompts for a task ID and passes it to `taskManager.deleteTask()`, but the `deleteTask()` method doesn't exist yet. You'll add that now.

In `taskManager.js`, create a new instance method named `deleteTask()` that accepts one parameter for the task ID. Like the other methods, add it to the `prototype` property:

```js
TaskManager.prototype.deleteTask = function(taskId) {

};
```

Similar to `markTaskDone()` in the previous step, you must find the element in the `tasks` array. However, this time you need the task's array position, or **index**, to pass into `splice()` to remove it.

The `Array` method `findIndex()` is similar to `find()`; however, it returns the element's index instead of a reference to the element. Like `find()`, it also takes a callback function with a parameter for the array element, allowing you to specify a condition for identifying the correct element:

```js
const taskIndex = this.tasks.findIndex(task => task.id === taskId);
```

> NOTE: Since this callback is the same as in the previous step, you might wonder if it's better to declare a function to remove the duplicate code. While you could use a function, it hides the condition for finding a task. When the condition is short and unlikely to change, it's common to prefer the clarity and repeat code like this. However, a declared function might be better if the condition is complex or likely to change.

Similar to the previous step, you must check the return value to determine if it found the task. If `findIndex()` doesn't find a match, it returns `-1`. Otherwise, it returns the index of the first matching element:

```js
const taskIndex = this.tasks.findIndex(task => task.id === taskId);
if (taskIndex !== -1) {
  // task found
} else {
  // no matching task found
}
```

The `splice()` function can remove, replace, and/or add elements to an array. Removing an element requires two parameters: one for the starting index and one for the number of elements to remove. To delete the task, pass it `taskIndex` and `1`:

```js
const taskIndex = this.tasks.findIndex(task => task.id === taskId);
if (taskIndex !== -1) {
  // task found
  this.tasks.splice(taskIndex, 1);
} else {
  // no matching task found
}
```

Like the previous step, the method returns a boolean indicating whether the process succeeded. It looks like this when you're done:

```js
TaskManager.prototype.deleteTask = function(taskId) {
  const taskIndex = this.tasks.findIndex(task => task.id === taskId);
  if (taskIndex !== -1) {
    this.tasks.splice(taskIndex, 1);
    return true;
  } else {
    return false;
  }
};
```

> NOTE: Remember, there's no persistent storage, so any changes you make are reset when you re-run the application.

## Next steps

Add another property to `Task`, like a priority level. The priority level could be a string like `'High priority'` or a number like `3` for high priority.

Add a sorting feature. You could sort in alphabetical order, by priority level, or by completed tasks last. You can add a new menu option to `MAIN_MENU_OPTIONS` in `app.js` with a new `case` in the `switch` statement using the same name.
