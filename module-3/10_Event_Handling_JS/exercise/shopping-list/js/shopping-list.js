let allItemsIncomplete = true;
const pageTitle = 'My Shopping List';
const groceries = [
  { id: 1, name: 'Oatmeal', completed: false },
  { id: 2, name: 'Milk', completed: false },
  { id: 3, name: 'Banana', completed: false },
  { id: 4, name: 'Strawberries', completed: false },
  { id: 5, name: 'Lunch Meat', completed: false },
  { id: 6, name: 'Bread', completed: false },
  { id: 7, name: 'Grapes', completed: false },
  { id: 8, name: 'Steak', completed: false },
  { id: 9, name: 'Salad', completed: false },
  { id: 10, name: 'Tea', completed: false }
];

document.addEventListener('DOMContentLoaded', () => {
  setPageTitle();
  displayGroceries();

  const listItems = document.querySelectorAll('li');
  listItems.forEach((listItem) => {
    listItem.addEventListener('click', () => {
      if (!listItem.classList.contains('completed')) {
        listItem.classList.add('completed');
      }
    });
  });

  listItems.forEach((listItem) => {
    listItem.addEventListener('dblclick', () => {
      if (listItem.classList.contains('completed')) {
        listItem.classList.remove('completed');
      }
    });
  });

  const completeButton = document.getElementById('toggleAll');
  completeButton.addEventListener('click', (event) => {
    gorceryCompleteButton();
  })
})
/**
* This function will get a reference to the title and set its text to the value
* of the pageTitle variable that was set above.
*/
function setPageTitle() {
  const title = document.getElementById('title');
  title.textContent = pageTitle;
}

/**
 * This function will loop over the array of groceries that was set above and add them to the DOM.
 */
function displayGroceries() {
  const ul = document.querySelector('ul');
  groceries.forEach((item) => {
    const li = document.createElement('li');
    li.textContent = item.name;
    const checkCircle = document.createElement('i');
    checkCircle.setAttribute('class', 'far fa-check-circle');
    li.appendChild(checkCircle);
    ul.appendChild(li);
  });
}

function gorceryCompleteButton() {
  const buttonStatus = document.getElementById('toggleAll');
  if (allItemsIncomplete) {
    const listItems = document.querySelectorAll('li');
    listItems.forEach((listItem) => {
      listItem.classList.add('completed');
    });
    buttonStatus.textContent = 'Mark All Incomplete'
    allItemsIncomplete = false;
  }else {
    const listItems = document.querySelectorAll('li');
    listItems.forEach((listItem) => {
      listItem.classList.remove('completed');
    });
    buttonStatus.textContent = 'Mark All Complete';
    allItemsIncomplete = true;
  }
}