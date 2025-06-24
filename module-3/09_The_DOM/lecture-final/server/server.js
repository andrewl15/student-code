import {bookReviews} from './book-reviews.js';
import express from 'express';
import cors from 'cors';

const app = express();
app.use(cors());

const port = 3000; // You can choose any port

// Get the titles of all the books
app.get('/books/titles', (req, res) => {
    res.send(bookReviews.map(book => {book.id, book.bookName}));
});

// Given a book by id, get all its reviews
app.get('/books/:id/reviews', (req, res) => {
    res.send(bookReviews[req.params.id]);
});

app.listen(port, () => {
    console.log(`Server listening on port ${port}`);
});
