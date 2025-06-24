const bookName = 'Cigar Parties for Dummies';
let description = 'Host and plan the perfect cigar party for all of your squirrelly friends.';
let reviews = [
  {
    reviewer: 'Malcolm Madwell',
    title: 'What a book!',
    review:
      "It certainly is a book. I mean, I can see that. Pages kept together with glue and there's writing on it, in some language. Yes indeed, it is a book!",
    rating: 3
  }
];

/**
 * Add product name to the page title.
 */
function setPageTitle() {
  const pageTitle = document.getElementById('page-title');
  pageTitle.querySelector('.name').textContent = bookName;
}

/**
 * Add product description to the page.
 */
function setPageDescription() {
  document.querySelector('.description').textContent = description;
}

/**
 * Display all of the reviews in the reviews array.
 */
function displayReviews() {
  if ('content' in document.createElement('template')) {
    reviews.forEach((review) => { 
      //clear out main
      displayReview(review);
    });
  } else {
    console.error('Your browser does not support templates');
  }
}

/**
 * Add single review to the page.
 *
 * @param {Object} review The review to display
 */
function displayReview(review) {
  const main = document.getElementById('main');
  const tmpl = document.getElementById('review-template').content.cloneNode(true);
  tmpl.querySelector('h4').textContent = review.reviewer;
  tmpl.querySelector('h3').textContent = review.title;
  tmpl.querySelector('p').textContent = review.review;
  // there will always be 1 star because it is a part of the template
  for (let i = 1; i < review.rating; ++i) {
    const img = tmpl.querySelector('img').cloneNode();
    tmpl.querySelector('.rating').appendChild(img);
  }
  main.appendChild(tmpl);
}

// LECTURE STARTS HERE ---------------------------------------------------------------
document.addEventListener('DOMContentLoaded', () => {
  // Set the product reviews page title.
  setPageTitle();
  // Set the product reviews page description.
  setPageDescription();
  // Display all of the product reviews on our page.
  displayReviews();

  const descP = document.querySelector('.description');

  descP.addEventListener('click', (event) => {
    toggleDescriptionEdit(descP)
  })

  const descInput = document.getElementById('inputDesc');
  descInput.addEventListener('keyup', (event) => {
    if (event.key === 'Enter') {
      exitDescriptionEdit(descInput, true);
    } else if (event.key === 'Escape') {
      exitDescriptionEdit(descInput, false);
    } else if (event.ctrlKey && event.key === 'z') {
      exitDescriptionEdit(descInput, false);
    }
  })

  descInput.addEventListener('blur', (event) => {
    exitDescriptionEdit(descInput, false);
  })

  const formButton = document.getElementById('btnToggleForm');
  formButton.addEventListener('click', (event) => {
    showHideForm();
  })

  const saveButton = document.getElementById('btnSaveReview');
  saveButton.addEventListener('click', (event) => {
    event.preventDefault();
    saveReview();
  })
})


/**
 * Hide the description and show the text box.
 *
 * @param {Element} desc the element containing the description
 */
function toggleDescriptionEdit(desc) {
  const textBox = desc.nextElementSibling;
  textBox.value = desc.textContent;
  textBox.classList.remove('d-none');
  desc.classList.add('d-none');
  textBox.focus();
}

/**
 * Hide the text box and show the description.
 * If save is true, also set the description to the contents of the text box.
 *
 * @param {Element} textBox the input element containing the edited description
 * @param {Boolean} save should we save the description text
 */
function exitDescriptionEdit(textBox, save) {
  let desc = textBox.previousElementSibling;
  if (save) {
    desc.textContent = textBox.value;
  }
  textBox.classList.add('d-none');
  desc.classList.remove('d-none');
}

/**
 * Toggle visibility of the add review form.
 */
function showHideForm() {
  const form = document.querySelector('form');
  const btn = document.getElementById('btnToggleForm');

  if (form.classList.contains('d-none')) {
    form.classList.remove('d-none');
    btn.textContent = 'Hide Form';
    document.getElementById('name').focus();
  } else {
    resetFormValues();
    form.classList.add('d-none');
    btn.textContent = 'Add Review';
  }
}

/**
 * Reset all of the values in the form.
 */
function resetFormValues() {
  const form = document.querySelector('form');
  const inputs = form.querySelectorAll('input');
  inputs.forEach((input) => {
    input.value = '';
  });
  document.getElementById('rating').value = 1;
  document.getElementById('review').value = '';
}

/**
 * Save the review that was added using the add review form.
 */
function saveReview() {
  //get all the elements in the review
  const formName = document.getElementById('name')
  const formTitle = document.getElementById('title')
  const formRating = document.getElementById('rating')
  const formReview = document.getElementById('review')
  // get the data from the elements
  const newReview = {
    reviewer: formName.value,
    title: formTitle.value,
    review: formReview.value,
    rating: formRating.value
  }

  //add the object to the reviews array
  reviews.push(newReview);
  //display the reviews
  displayReviews();

  //hide the form

  showHideForm();
}
