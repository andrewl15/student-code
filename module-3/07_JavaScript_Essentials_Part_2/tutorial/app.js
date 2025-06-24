import readlineSync from 'readline-sync';

const MAIN_MENU_OPTIONS = [ 'View Tasks', 'Add Task', 'Mark Task Done', 'Delete Task' ];
const LINE_SEPARATOR = '--------------------';

let optionSelected;

do {
  optionSelected = promptForMenuOption();
  let optionText = MAIN_MENU_OPTIONS[optionSelected - 1];
  console.log();

  switch (optionText) {
    case 'View Tasks':
      const tasks = taskManager.getTasks();
      console.log('Tasks');
      console.log(LINE_SEPARATOR);
      console.log("ID [Done?] Task Name");
      console.log(LINE_SEPARATOR);
      for (let i = 0; i < tasks.length; i++) {
        const task = tasks[i];
        console.log(`${task.id} [${task.done ? '✓' : ' '}] ${task.taskName}`);
      }
      break;
    case 'Add Task':
      const taskName = readlineSync.question('Enter task name: ');
      taskManager.addTask(taskName);
      console.log('Task added successfully.');
      break;
    case 'Mark Task Done':
      const taskIdToMarkDone = readlineSync.questionInt('Enter task ID to mark as done: ');
      if (taskManager.markTaskDone(taskIdToMarkDone)) {
        console.log('Task marked as done successfully.');
      } else {
        console.log('Invalid task number.');
      }
      break;
    case 'Delete Task':
      const taskIdToDelete = readlineSync.questionInt('Enter task ID to delete: ');
      if (taskManager.deleteTask(taskIdToDelete)) {
        console.log('Task deleted successfully.');
      } else {
        console.log('Invalid task number.');
      }
      break;
  }

  if (optionSelected !== 0) { // if not exiting, prompt enter to continue
    promptEnterToContinue();
  }

} while (optionSelected !== 0) // 'Exit' = 0

function promptForMenuOption() {
  console.clear();
  console.log('Main Menu');
  console.log(LINE_SEPARATOR);

  MAIN_MENU_OPTIONS.forEach((option, index) => {
    console.log(`[${index + 1}] ${option}`);
  });
  console.log('[0] Exit');

  let optionSelected = readlineSync.question('Select an option: ');
  while (!isValidOption(optionSelected, 0, MAIN_MENU_OPTIONS.length)) {
    console.log('Invalid option. Please try again.');
    optionSelected = readlineSync.question('Select an option: ');
  }
  return Number.parseInt(optionSelected);
}

function promptEnterToContinue() {
  console.log(LINE_SEPARATOR);
  readlineSync.question('Press Enter key to continue', {hideEchoBack: true, mask: ''});
  console.clear();
}

/**
 * Validates if the option is a number and within the allowed range.
 * @param {number} option the option selected by the user
 * @param {number} minAllowed the minimum allowed value inclusively
 * @param {number} maxAllowed the maximum allowed value inclusively
 * @returns
 */
function isValidOption(option, minAllowed, maxAllowed) {
  return !(Number.isNaN(Number.parseInt(option))) && option >= minAllowed && option <= maxAllowed;
}
