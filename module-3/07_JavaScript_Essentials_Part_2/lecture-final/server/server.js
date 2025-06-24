import express from 'express';

const app = express();

// Helper function to generate a standard 52-card deck
function generateStandardDeck() {
    const suits = ["Hearts", "Clubs", "Diamonds", "Spades"];
    const ranks = ["2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"];
    const deck = [];

    suits.forEach(suit => {
        ranks.forEach(rank => {
            deck.push({ suit, rank });
        });
    });

    return deck;
}

// Function to generate specific game decks
function getDeckByGame(game) {
    const standardDeck = generateStandardDeck();

    switch (game?.toLowerCase()) {

        case 'pinochle': {
            const cards = standardDeck
                .filter(card => ["9", "10", "Jack", "Queen", "King", "Ace"].includes(card.rank))
                .concat(
                    standardDeck.filter(card => ["9", "10", "Jack", "Queen", "King", "Ace"].includes(card.rank))
                );
            return {cards};
        }

        case 'euchre': {
            const cards = standardDeck.filter(card => ["9", "10", "Jack", "Queen", "King", "Ace"].includes(card.rank));
            return {cards};
        }

        case 'canasta': {
            const cards = [...standardDeck, ...standardDeck, { suit: "Joker", rank: "Joker" }, { suit: "Joker", rank: "Joker" },
                { suit: "Joker", rank: "Joker" }, { suit: "Joker", rank: "Joker" }];
            return {cards};
        }

        case 'lastcard': {
            const cards = [...standardDeck, ...standardDeck];
            const cardRemoved = cards.splice(Math.floor(Math.random() * cards.length), 1)[0];
            return {cards, cardRemoved};
        }

        default: {
            const cards = standardDeck;
            return {cards};
        }
    }
}

// GET endpoint to fetch a deck for a specific game
app.get('/deck', (req, res) => {
    let { game } = req.query;
    if (game === undefined) {
        game = 'standard';
    }

    let deck = getDeckByGame(game);

    // Check if the game was recognized and return the deck or an error
    if (deck.error) {
        return res.status(400).json(deck);
    } else {
        return res.json({ game, deck });
    }
});

// Start the server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
