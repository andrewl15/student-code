
function changeMain() {
    const element = document.querySelector('#main-content');
    element.textContent = 'Hello World!';
}

function changeOther() {
    const element = document.getElementById('other-div');
    element.innerHTML = '<strong>I changed the text</strong>'
}

function changeTD() {
    const firstTD = document.querySelector('td');
    firstTD.textContent = 'This is the first td.'
}

function numberTDs() {
    const allTDs = document.querySelectorAll('td');
    for(let i = 0; i < allTDs.length; i++){
        allTDs[i].textContent = `This is number ${i + 1}`;
    }
}

function getUserName(){
    const inputBox = document.getElementById('userName');
    const name = inputBox.value;
    console.log(name);
}

function hideUserName(){
    const inputBox = document.getElementById('userName');
    // get the value from the inputBox
    const name = inputBox.value;
    let hiddenName = '';
    for(let i = 0; i < name.length; i++){
        hiddenName += '*';
    }
    // set the value in the inputBox
    inputBox.value = hiddenName;
}

function makeImportant() {
    const body = document.querySelector('body');
    body.classList.add('important')
}

function makeUnImportant() {
    const body = document.querySelector('body');
    body.classList.remove('important');
}

function makeDiv() {
    const newDiv = document.createElement('div');
    newDiv.textContent = 'Hey, I am new here!';
    newDiv.classList.add('important');

    // place the newDiv
    // first, find a reference element
    const relDiv = document.getElementById('main-content');
    relDiv.insertAdjacentElement('afterbegin',newDiv);
}

function changeFirstRow() {
    // grab the first tr
    const firstRow = document.querySelector('tr');
    const dataItems = Array.from(firstRow.children);
    dataItems.map(item => {
        console.log(item.textContent)
        item.textContent = 'Hello World!';
        
    })
}