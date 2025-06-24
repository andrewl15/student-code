import * as chai from 'chai';
import { getBooks, getBookById, getMovies, getMovieById } from './app.js';

const expect = chai.expect;
chai.should();

describe('getBooks()', () => {

  it('should return an array containing all books', async () => {
    const result = await getBooks();
    if (result === undefined) {
      chai.assert.fail(`No books were returned. Did you remember to use 'return'?`);
    }

    expect(result).to.be.an('array').that.is.not.empty &&
      expect(result).to.have.lengthOf(39);

    const bookCeremonyId = 102211;
    const bookCeremony = result.find((book) => book.id === bookCeremonyId);
    if (!bookCeremony) {
      chai.assert.fail(`The book with ID ${bookCeremonyId} was expected but not found in the results.`);
    }

    expect(bookCeremony).to.have.property('id').that.equals(bookCeremonyId) &&
      expect(bookCeremony).to.have.property('title').that.equals("Ceremony") &&
      expect(bookCeremony).to.have.property('authors').to.be.an('array').that.deep.include.members([{ id: 302205, fullName: 'Leslie Marmon Silk' }]) &&
      expect(bookCeremony).to.have.property('firstPublished').that.equals('01-01-1997') &&
      expect(bookCeremony).to.have.property('coverImage').that.equals('/images/books/ceremony_cover.jpg') &&
      expect(bookCeremony).to.have.property('genres').to.be.an('array').that.deep.include.members([{ id: 2200, name: 'Fiction' }, { id: 2201, name: 'Historical' }, { id: 2202, name: 'Classics' }, { id: 2212, name: 'War' }]) &&
      expect(bookCeremony).to.have.property('overview').that.equals("Tayo, a young Native American, has been a prisoner of the Japanese during World War II, and the horrors of captivity have almost eroded his will to survive. His return only increases his feeling of estrangement and alienation. As Tayo seeks comfort and resolution in the ancient stories and traditions of his people, the search itself becomes a ritual, a curative ceremony that defeats the most virulent of afflictions—despair.");

    const bookAlchemistId = 102215;
    const bookAlchemist = result.find((book) => book.id === bookAlchemistId);

    if (!bookAlchemist) {
      chai.assert.fail(`The book with ID ${bookAlchemistId} was expected but not found in the results.`);
    }

    expect(bookAlchemist).to.have.property('id').that.equals(bookAlchemistId) &&
      expect(bookAlchemist).to.have.property('title').that.equals('The Alchemist') &&
      expect(bookAlchemist).to.have.property('authors').to.be.an('array').that.deep.include.members([{ id: 302208, fullName: 'Paulo Coelho' }]) &&
      expect(bookAlchemist).to.have.property('firstPublished').that.equals('01-01-1988') &&
      expect(bookAlchemist).to.have.property('coverImage').that.equals('/images/books/the_alchemist_cover.jpg') &&
      expect(bookAlchemist).to.have.property('illustrator').that.equals('James Noel Smith') &&
      expect(bookAlchemist).to.have.property('genres').to.be.an('array').that.deep.include.members([{ id: 2202, name: 'Classics' }, { id: 2200, name: 'Fiction' }, { id: 2212, name: 'Spirituality' }]) &&
      expect(bookAlchemist).to.have.property('overview').that.equals("Paulo Coelho's masterpiece tells the mystical story of Santiago, an Andalusian shepherd boy who yearns to travel in search of a worldly treasure. His quest will lead him to riches far different—and far more satisfying—than he ever imagined. Santiago's journey teaches us about the essential wisdom of listening to our hearts, of recognizing opportunity and learning to read the omens strewn along life's path, and, most importantly, to follow our dreams.");

  });

});

describe('getBookById()', () => {

  it('returns a book for a valid ID', async () => {
    const bookId = 102208;
    const result = await getBookById(bookId);
    if (!result) {
      chai.assert.fail(`No book was returned for ID ${bookId}. Did you remember to use 'return'?`);
    }
    expect(result).to.have.property('id').that.equals(bookId) &&
        expect(result).to.have.property('title').that.equals("Maya's World: Angelina of Italy") &&
        expect(result).to.have.property('authors').to.be.an('array').that.deep.include.members([{ id: 302203, fullName: 'Maya Angelou' }]) &&
        expect(result).to.have.property('firstPublished').that.equals('09-14-2004') &&
        expect(result).to.have.property('coverImage').that.equals('/images/books/angelina_of_italy_cover.jpg') &&
        expect(result).to.have.property('illustrator').that.equals('Lizzy Rockwell') &&
        expect(result).to.have.property('genres').to.be.an('array').that.deep.include.members([{ id: 2209, name: 'Children' }, { id: 2200, name: 'Fiction' }, { id: 2210, name: 'Picture Book' }]) &&
        expect(result).to.have.property('overview').that.equals('Tenderly, joyously, sometimes in sadness,  sometimes in pain, Maya Angelou writes from the heart and  celebrates life as only she has discovered it. In  this moving volume of poetry, we hear the  multi-faceted voice of one of the most powerful and  vibrant writers of our time.');
  });

  it('does not return a book for an invalid ID', async () => {
    const bookId = 999999;
    const result = await getBookById(bookId);
    if (result === undefined) {
      chai.assert.fail("No result was returned. Did you remember to use 'return'?");
    }
    expect(result).to.be.empty;
  });

});


describe('getMovies()', () => {

  it('should return an array containing all movies', async () => {
    const result = await getMovies();
    if (result === undefined) {
      chai.assert.fail(`No movies were returned. Did you remember to use 'return'?`);
    }

    expect(result).to.be.an('array').that.is.not.empty &&
      expect(result).to.have.lengthOf(34);

    const movieAquariusId = 102213;
    const movieAquarius = result.find((book) => book.id === movieAquariusId);
    if (!movieAquarius) {
      chai.assert.fail(`The movie with ID ${movieAquariusId} was expected but not found in the results.`);
    }

    expect(movieAquarius).to.have.property('id').that.equals(movieAquariusId) &&
      expect(movieAquarius).to.have.property('title').that.equals('Aquarius') &&
      expect(movieAquarius).to.have.property('directors').to.be.an('array').that.deep.include.members([{ id: 302200, name: 'Kleber Mendonça Filho' }]) &&
      expect(movieAquarius).to.have.property('releaseDate').that.equals('05-17-2016') &&
      expect(movieAquarius).to.have.property('posterImage').that.equals('/images/movies/Aquarius_Poster.jpg') &&
      expect(movieAquarius).to.have.property('genres').to.be.an('array').that.deep.include.members([{ id: 2200, name: 'Foreign' }, { id: 2201, name: 'Drama' }]) &&
      expect(movieAquarius).to.have.property('taglines').to.be.an('array').and.is.empty &&
      expect(movieAquarius).to.have.property('summary').that.equals("Clara, 65, lives her life to the fullest with her family and friends. A construction company wants her Recife oceanfront condo, as they've already bought all the other in the 3 story building. Clara's staying.");

      const movieMoodId = 102224;
      const movieMood = result.find((book) => book.id === movieMoodId);
      if (!movieMood) {
        chai.assert.fail(`The movie with ID ${movieMoodId} was expected but not found in the results.`);
      }

      expect(movieMood).to.have.property('id').that.equals(movieMoodId) &&
        expect(movieMood).to.have.property('title').that.equals('In the Mood for Love') &&
        expect(movieMood).to.have.property('directors').to.be.an('array').that.deep.include.members([{ id: 302212, name: 'Wong Kar-wai' }]) &&
        expect(movieMood).to.have.property('releaseDate').that.equals('05-20-2000') &&
        expect(movieMood).to.have.property('posterImage').that.equals('/images/movies/In_the_Mood_for_Love_Poster.jpg') &&
        expect(movieMood).to.have.property('genres').to.be.an('array').that.deep.include.members([{ id: 2200, name: 'Foreign' }, { id: 2201, name: 'Drama' }, { id: 2206, name: 'Romance' }]) &&
        expect(movieMood).to.have.property('taglines').to.be.an('array').that.deep.includes.members([ 'Feel the heat, keep the feeling burning, let the sensation explode.' ]) &&
        expect(movieMood).to.have.property('summary').that.equals("Two neighbors form a strong bond after both suspect extramarital activities of their spouses. However, they agree to keep their bond platonic so as not to commit similar wrongs. Hurt and angry, they find comfort in their growing friendship even as they resolve not to be like their unfaithful spouses.");

  });

});


describe('getMovieById()', () => {

  it('returns a movie for a valid ID', async () => {
    const movieId = 102212;
    const result = await getMovieById(movieId);
    if (!result) {
      chai.assert.fail(`No movie was returned for ID ${movieId}. Did you remember to use 'return'?`);
    }
    expect(result).to.have.property('id').that.equals(movieId) &&
        expect(result).to.have.property('title').that.equals('Under the Shadow') &&
        expect(result).to.have.property('releaseDate').that.equals('01-22-2016') &&
        expect(result).to.have.property('posterImage').that.equals('/images/movies/Under_the_Shadow_Poster.jpg') &&
        expect(result).to.have.property('directors').to.be.an('array').that.deep.include.members([{ id: 302235, name: 'Babak Anvari' }]) &&
        expect(result).to.have.property('genres').to.be.an('array').that.deep.include.members([{ id: 2200, name: 'Foreign' }, { id: 2201, name: 'Drama' }, { id: 2209, name: 'Horror' }, { id: 2210, name: 'War' }]) &&
        expect(result).to.have.property('taglines').to.be.an('array').that.deep.include.members([ 'Fear will find you.' ]) &&
        expect(result).to.have.property('summary').that.equals('As a mother and daughter struggle to cope with the terrors of the post-revolution, war-torn Tehran of the 1980s, a mysterious evil begins to haunt their home. As widespread bombings shake the ground beneath their feet, the two grapple with a more insidious evil that is faceless and traceless, coming and going only with the wind.');
  });

  it('does not return a movie for an invalid ID', async () => {
    const movieId = 999999;
    const result = await getMovieById(movieId);
    if (result === undefined) {
      chai.assert.fail("No result was returned. Did you remember to use 'return'?");
    }
    expect(result).to.be.empty;
  });

});