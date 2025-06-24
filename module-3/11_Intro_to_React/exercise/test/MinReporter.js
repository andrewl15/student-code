export default {
  onFinished(stats) {
    let totalPasses = 0;
    let totalFailures = 0;
    for (const stat of stats) {
      if (stat.result.state === 'fail' && stat.result.errors && stat.result.errors.length > 0) {
        console.log(`${spaces(2)}${formatMessage(stat.result.errors[0].message.replace('\n', ' '))}`);
        throw new Error('Test run failed. See above for details.');
      }

      for (const groupTask of stat.tasks) {
        console.log(`${groupTask.name}`);
        for (const task of groupTask.tasks) {
          if (task.result.state === 'fail') {
            totalFailures++;
            console.log(`${spaces(2)}❌ FAIL: ${task.name}`);
            if (task.result.errors && task.result.errors.length > 0) {
              console.log(`${spaces(4)}${formatMessage(task.result.errors[0].message)}`);
            }
          } else {
            totalPasses++;
            console.log(`${spaces(2)}✔  PASS: ${task.name}`);
          }
        }
      }
    }
    console.log(`*** End test run: ${totalPasses} of ${totalPasses + totalFailures} passed.`);
  }
};

function spaces(num) {
  return ' '.repeat(num);
}

function formatMessage(message) {
  if (message.includes('Unable to find an element with the text')) {
    return message.substring(0, message.indexOf('.'));
  } else if (message.includes('\n')) {
    return message.substring(0, message.indexOf('\n'));
  } else {
    return message;
  }
}
