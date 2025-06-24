const { response } = require("express");

/**
 * Get the book titles from the server and add them as options
 * to the book select.
 */
function setBookDropdown() {}

/**
 * Display the reviews for a given bookId
 * 
 * @param {*} bookId 
 */
function displayBookReviews(bookId) {
  axios.get(`http://localhost:3000/books/${bookId}/reviews`)
  .then((response) => {
    //console.log(response.data);
    const bookData = response.data;
    setBookDescription(bookData);
  })
  .catch((error) => {
    const errorLabel = document.getElementById('error-label');
    if(error.response){
      errorLabel.textContent = `Response error of ${error.response.statusCode}`
    }else if (error.request){
      errorLabel.textContent = `Request error. Server can't be reached.`
    }else{
      errorLabel.textContent = 'Something else went wrong.'
    }
  })
}

/**
 * Set the book description.
 */
function setBookDescription(book) {
  //find the element where I want to put the data
  const descriptionP = document.getElementById('book-description');
  //add the data to the text content
  descriptionP.textContent = book.description;

}

function setBookTitle(book) {
  const h1Title = document.querySelector()
}

/**
 * Display all of the reviews for a given book.
 * Loop over the array of reviews and use some helper functions
 * to create the elements needed for the markup and add them to the DOM.
 */
function displayReviews(book) {
  const container = document.getElementById('reviews');

  book.reviews.array.forEach((item) => {

  })
}

/**
 * Create a new h2 element with the name of the reviewer and append it to
 * the parent element that is passed to me.
 *
 * @param {HTMLElement} el: The element to append the reviewer to
 * @param {string} name The name of the reviewer
 */
function addReviewer(parent, name) {
  const reviewerH2 = document.createElement('h2');
  reviewerH2.textContent = name;
  parent.appendChild(reviewerH2);
}

/**
 * Add the rating div along with a star image for the number of ratings 1-5
 * @param {HTMLElement} parent
 * @param {Number} numberOfStars
 */
function addRating(parent, numberOfStars) {}

/**
 * Add an h3 element along with the review title
 * @param {HTMLElement} parent
 * @param {string} title
 */
function addTitle(parent, title) {}

/**
 * Add the product review
 * @param {HTMLElement} parent
 * @param {string} review
 */



// Pay no attention to the Wizard below this line!!
document.addEventListener('DOMContentLoaded', function() {
  displayBookReviews(0);
});
