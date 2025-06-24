/**
 * This service class currently uses hard-coded data to supply quotes to the application.
 * In the next unit, you'll begin working with a backend server to request this information using Axios.
 */
export default {

  getQuotes() {
    return [
      {
        id: 1,
        text: "The only way to do great work is to love what you do.",
        author: "Steve Jobs"
      },
      {
        id: 2,
        text: "The best way to predict the future is to invent it.",
        author: "Alan Kay"
      },
      {
        id: 3,
        text: "Objective evidence is the ultimate authority. Recorders may lie, but Nature is incapable of it.",
        author: "Walter M. Miller, Jr."
      },
      {
        id: 4,
        text: "There is no real ending. It's just the place where you stop the story.",
        author: "Frank Herbert"
      },
      {
        id: 5,
        text: "I like quoting Einstein. Know why? Because nobody dares contradict you.",
        author: "Studs Terkel"
      },
      {
        id: 6,
        text: "Understanding is a two-way street.",
        author: "Eleanor Roosevelt"
      },
      {
        id: 7,
        text: "Everything was so new—the whole idea of going into space was new and daring. There were no textbooks, so we had to write them.",
        author: "Katherine Johnson"
      },
      {
        id: 8,
        text: "Be less curious about people and more curious about ideas.",
        author: "Marie Curie"
      },
      {
        id: 9,
        text: "The only thing we have to fear is fear itself.",
        author: "Franklin D. Roosevelt"
      },
      {
        id: 10,
        text: "Compassion and tolerance are not a sign of weakness, but a sign of strength.",
        author: "Tenzin Gyatso, 14th Dalai Lama"
      }
    ];
  },
};
