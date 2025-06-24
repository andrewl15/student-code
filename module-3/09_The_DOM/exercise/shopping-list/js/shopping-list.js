/**
 * This function gets the first shopping list from the server via Axios and
 * then calls the setShoppingListTitle() and displayGroceries() to perform the actual
 * display of the title and groceries.
 */
function displayShoppingList() {
  axios.get(`http://localhost:3000/shoppinglists`)
  .then((response) => {
    const shoppingList = response.data[0];
    setShoppingListTitle(shoppingList);
    displayGroceries(shoppingList)
  })
  .catch((error) => {
    const errorLabel = document.getElementById('error-label');
    if(error.response){
      errorLabel.textContent = `Response error of ${error.response.statusCode}`;
    } else if (error.request){
      errorLabel.textContent = `Request error. Server can't be reached.`
    } else {
      errorLabel.textContent = 'Something else when haywire.'
    }
  })
}

/**
 * This function will get a reference to the title and set its text to the value
 * of the pageTitle variable that was set above.
 */
function setShoppingListTitle(shoppingList) {
  const title = document.getElementById('title');
  title.textContent = shoppingList.title;
}

/**
 * This function will loop over the array of groceries that was set above and add them to the DOM.
 */
function displayGroceries(shoppingList) {
  const ul = document.getElementById('groceries');

  for(let i = 0; i < shoppingList.groceries.length; i++){
    const li = document.createElement('li');
    li.textContent = shoppingList.groceries[i];
    ul.appendChild(li); 
  }
}

/**
 * This function will be called when the button is clicked. You will need to get a reference
 * to every list item and add the class completed to each one
 */
function markCompleted() {
  const ul = document.querySelectorAll('li');
  ul.forEach((li) => {
    li.classList.add('completed');
  })
}

document.addEventListener('DOMContentLoaded', () => {
  // Attach a click listener to the button
  const button = document.querySelector('#mark-all-complete');
  button.addEventListener('click', markCompleted);

  // Get the first shopping list from the server and display
  displayShoppingList();
});
