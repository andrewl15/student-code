
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
  // get the data from the server
  axios.get(`http://localhost:3000/books/${bookId}/reviews`)
  .then((response) => {
    // console.log(response.data)
    const bookData = response.data;
    setBookDescription(bookData);
    setBookTitle(bookData);
    displayReviews(bookData);
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
 * Set the book description.
 */
function setBookDescription(book) {
  // find the element where I want to put the data
  const descriptionP = document.getElementById('book-description');
  // add the data to the textContent
  descriptionP.textContent = book.description;
}

/**
 * Set the title
 * @param {} book 
 */
function setBookTitle(book){
  // find the h1 element
  const h1Title = document.querySelector('h1');

  // add to the current text, the title of the book
  h1Title.textContent += ': ' + book.bookName;
}
/**
 * Display all of the reviews for a given book.
 * Loop over the array of reviews and use some helper functions
 * to create the elements needed for the markup and add them to the DOM.
 */
function displayReviews(book) {
  // where are the reviews going to go?
   
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
  // same as insertAdjacentElement('beforeend',...)
  parent.appendChild(reviewerH2);
}

/**
 * Add the rating div along with a star image for the number of ratings 1-5
 * @param {HTMLElement} parent
 * @param {Number} numberOfStars
 */
function addRating(parent, numberOfStars) {
  // need the rating container
  const ratingDiv = document.createElement('div');
  ratingDiv.classList.add('rating');
  // add an img for each rating
  for(let i = 0; i < numberOfStars; i++){
    // create an img tag
    const imgTag = document.createElement('img');
    // add the src attribute
    imgTag.src = 'img/star.png';
    imgTag.setAttribute('class','ratingStar');
    imgTag.setAttribute('id',i);
    // add the img to the ratingDiv
    ratingDiv.appendChild(imgTag);
  }
  // add completed rating div with images to parent/container
  parent.appendChild(ratingDiv);
}

/**
 * Add an h3 element along with the review title
 * @param {HTMLElement} parent
 * @param {string} title
 */
function addTitle(parent, title) {
  const titleH3 = document.createElement('h3');
  titleH3.textContent = title;
  parent.appendChild(titleH3);
}

/**
 * Add the product review
 * @param {HTMLElement} parent
 * @param {string} review
 */
function addReview(parent,review){
  const reviewP = document.createElement('p');
  reviewP.textContent = review;
  parent.appendChild(reviewP);
}


// Pay no attention to the Wizard below this line!!
document.addEventListener('DOMContentLoaded', function() {
  displayBookReviews(0);
});
