function getUserName(){
    const inputBox = document.getElementById('userName');
    const name = inputBox.value;
    console.log(name);
}

function hideUserName(){
    const inputBox = document.getElementById('userName');
    const name = inputBox.value;
    let hiddenName = '';
    for(let x of name){
        x = hiddenName;
    }
}

function makeImportant(){
    const body = document.querySelector('body');
    body.classList.add('important')
}

function makeUnImportant() {
    const body = document.querySelector('body');
    body.classList.remove('important');
}

function makeDiv(){
    const newDiv = document.createElement('div');
    newDiv.textContent = 'Hey I am new here!';
    newDiv.classList.add('important');

    //place the newDiv
    //first, find a reference element
    const relDiv = document.getElementById('main-content');
    relDiv.insertAdjacentElement('afterbegin', newDiv);

}

function changeFirstRow(){
    const firstRow = document.querySelector('tr');
    const dataItems = Array.from(firstRow.children);
    dataItems.map(item => {
        item.textContent = 'Hello Work!';
    })
}