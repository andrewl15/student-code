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

export { TaskManager };
