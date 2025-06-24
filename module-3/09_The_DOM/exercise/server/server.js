import {shoppingLists} from './shopping-lists.js';
import express from 'express';
import cors from 'cors';

const app = express();
app.use(cors());

const port = 3000;

app.get('/shoppinglists', (req, res) => {
    res.send(shoppingLists);
});

app.listen(port, () => {
    console.log(`Server listening on port ${port}`);
});
