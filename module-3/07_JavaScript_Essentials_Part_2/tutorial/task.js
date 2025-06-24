function Task(taskName) {
  this.id = newId();
  this.taskName = taskName;
  this.done = false;
}

let id = 101;
function newId() {
  return id++;
}

export { Task };
