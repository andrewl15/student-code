import {todoLists} from './to-dos.js';
import express from 'express';
import cors from 'cors';

const app = express();
app.use(cors());

const port = 3000; // You can choose any port

// Get all To-Dos
app.get('/todos', (req, res) => {
    res.send(todoLists);
});

app.listen(port, () => {
    console.log(`Server listening on port ${port}`);
});
