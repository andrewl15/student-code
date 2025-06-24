// https://docs.cypress.io/api/introduction/api.html

const authorsUrl = '/authors';
const booksUrl = '/books';
const authorMayaUrl = '/author/302203';
const authorChinuaUrl = '/author/302209';
const authorMelissaUrl = '/author/302227';
const authorJamesUrl = '/author/302204';
const bookBeginningsEndingsUrl = '/book/102230';
const bookWrittenStarsUrl = '/book/102232';
const bookCagedBird = '/book/102206';
const bookUndergroundRailroad = '/book/102237';

describe('React Router Exercise', () => {

  describe('Step One Tests', () => {

    it('Setup nav links', () => {
      cy.visit('/');
      cy.get('nav a').should('have.length', 2);

      // test link existence and default state (not active)
      cy.get('nav a').first().should('include.text', 'Authors');
      cy.get('nav a').first().should('have.attr', 'href', authorsUrl);
      cy.get('nav a').first().should('not.have.class', 'active');
      cy.get('nav a').first().should('not.have.attr', 'aria-current');

      cy.get('nav a').last().should('include.text', 'Books');
      cy.get('nav a').last().should('have.attr', 'href', booksUrl);
      cy.get('nav a').last().should('not.have.class', 'active');
      cy.get('nav a').last().should('not.have.attr', 'aria-current');

      // test active class and aria-current attribute (proves it's from React Router)
      cy.get('nav a').first().click();
      cy.url().should('include', authorsUrl);
      cy.get('nav a').first().should('have.class', 'active');
      cy.get('nav a').first().should('have.attr', 'aria-current', 'page');
      cy.get('nav a').last().should('not.have.class', 'active');
      cy.get('nav a').last().should('not.have.attr', 'aria-current');

      cy.visit('/');
      cy.get('nav a').last().click();
      cy.url().should('include', booksUrl);
      cy.get('nav a').first().should('not.have.class', 'active');
      cy.get('nav a').first().should('not.have.attr', 'aria-current');
      cy.get('nav a').last().should('have.class', 'active');
      cy.get('nav a').last().should('have.attr', 'aria-current', 'page');
    });

    it('Setup route `/authors` to AuthorsView', () => {
      cy.visit(authorsUrl);
      cy.get('article[class*=author-card]').should('have.length', 38);
      cy.get('article[class*=book-card]').should('have.length', 0);
    });

    it('Setup route `/books` to BooksView', () => {
      cy.visit(booksUrl);
      cy.get('article[class*=book-card]').should('have.length', 39);
      cy.get('article[class*=author-card]').should('have.length', 0);
    });

    it('Setup route `/author/id` to AuthorDetailsView', () => {
      cy.visit(authorMayaUrl); // Maya Angelou - hard-coded author until later
      cy.get('article[class*=author-card]').should('have.length', 1);
      cy.get('article[class*=author-card] h2').should('include.text', 'Maya Angelou');
      cy.get('article[class*=book-card]').should('have.length', 3);
    });

    it('Setup route `/book/id` to BookDetailsView', () => {
      cy.visit(bookBeginningsEndingsUrl); // A Thousand Beginnings and Endings - hard-coded book until later
      cy.get('article[class*=book-card]').should('have.length', 1);
      cy.get('article[class*=book-card] h2').should('include.text', 'A Thousand Beginnings and Endings');
      cy.get('section[class*=author-list] span').should('have.length', 14);
    });
  });

  describe('Step Two Tests', () => {
    it('Author names clickable links', () => {
      cy.visit(authorsUrl);
      cy.get('article[class*=author-card]').first().find('a').should('have.attr', 'href', authorChinuaUrl);
      cy.get('article[class*=author-card]').last().find('a').should('have.attr', 'href', authorMelissaUrl);
    });

    it('Author name link goes to AuthorDetailsView', () => {
      cy.visit(authorsUrl);
      cy.get('article[class*=author-card]').first().find('a').click();
      cy.get('article[class*=author-card] h2').should('satisfy', ($el) => {
        // Check if the author name is Maya Angelou (at Step Two) or Chinua Achebe (after Step Two)
        return $el[0].innerText.includes('Maya Angelou') || $el[0].innerText.includes('Chinua Achebe');
      });
    });
  });

  describe('Step Three Tests', () => {
    it('Author details view has correct author', () => {
      cy.visit(authorChinuaUrl);
      cy.get('article[class*=author-card] h2').should('include.text', 'Chinua Achebe');
      cy.get('article[class*=book-card]').should('have.length', 1);

      cy.visit(authorMelissaUrl);
      cy.get('article[class*=author-card] h2').should('include.text', 'Melissa de la Cruz');
      cy.get('article[class*=book-card]').should('have.length', 1);

      cy.visit(authorJamesUrl);
      cy.get('article[class*=author-card] h2').should('include.text', 'James Welch');
      cy.get('article[class*=book-card]').should('have.length', 2);

      cy.visit(authorMayaUrl);
      cy.get('article[class*=author-card] h2').should('include.text', 'Maya Angelou');
      cy.get('article[class*=book-card]').should('have.length', 3);
    });
  });

  describe('Step Four Tests', () => {
    it('Book titles clickable links', () => {
      cy.visit(booksUrl);
      cy.get('article[class*=book-card]').first().find('a').should('have.attr', 'href', bookBeginningsEndingsUrl);
      cy.get('article[class*=book-card]').last().find('a').should('have.attr', 'href', bookWrittenStarsUrl);
    });

    it('Book title link goes to BookDetailsView', () => {
      cy.visit(booksUrl);
      cy.get('article[class*=book-card]').first().find('a').click();
      cy.get('article[class*=book-card] h2').should('satisfy', ($el) => {
        // Check if the book title is A Thousand Beginnings and Endings (at Step Four) or Written in the Stars (after Step Four)
        return $el[0].innerText.includes('A Thousand Beginnings and Endings') || $el[0].innerText.includes('The Hate U Give');
      });
    });
  });

  describe('Step Five Tests', () => {
    it('Book details view has correct book', () => {
      cy.visit(bookBeginningsEndingsUrl);
      cy.get('article[class*=book-card] h2').should('include.text', 'A Thousand Beginnings and Endings');
      cy.get('section[class*=author-list] span').should('have.length', 14);

      cy.visit(bookWrittenStarsUrl);
      cy.get('article[class*=book-card] h2').should('include.text', 'Written in the Stars');
      cy.get('section[class*=author-list] span').should('have.length', 1);
      cy.get('section[class*=author-list] span').should('include.text', 'Aisha Saeed');

      cy.visit(bookCagedBird);
      cy.get('article[class*=book-card] h2').should('include.text', 'I Know Why the Caged Bird Sings');
      cy.get('section[class*=author-list] span').should('have.length', 1);
      cy.get('section[class*=author-list] span').should('include.text', 'Maya Angelou');

      cy.visit(bookUndergroundRailroad);
      cy.get('article[class*=book-card] h2').should('include.text', 'The Underground Railroad');
      cy.get('section[class*=author-list] span').should('have.length', 1);
      cy.get('section[class*=author-list] span').should('include.text', 'Colson Whitehead');
    });
  });
});
