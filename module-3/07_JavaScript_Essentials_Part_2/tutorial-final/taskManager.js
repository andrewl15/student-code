import { Task } from './task.js';

function TaskManager() {
  this.tasks = [
    new Task('Write code'),
    new Task('Run tests'),
    new Task('Commit and push changes')
  ];
}

TaskManager.prototype.getTasks = function() {
  return this.tasks;
};

TaskManager.prototype.addTask = function(taskName) {
  this.tasks.push(new Task(taskName));
};

TaskManager.prototype.markTaskDone = function(taskId) {
  const foundTask = this.tasks.find(task => task.id === taskId);
  if (foundTask !== undefined) {
    foundTask.done = true;
    return true;
  } else {
    return false;
  }
};

TaskManager.prototype.deleteTask = function(taskId) {
  const taskIndex = this.tasks.findIndex(task => task.id === taskId);
  if (taskIndex !== -1) {
    this.tasks.splice(taskIndex, 1);
    return true;
  } else {
    return false;
  }
};

export { TaskManager };
