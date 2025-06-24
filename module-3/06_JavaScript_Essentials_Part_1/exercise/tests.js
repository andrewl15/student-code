import { sumAny, createPet, isTeen, formatAddress, average, getItemNumber, getItemStyle, extractOdds, matchingStrand, catsAndDogs, sumAll, sumDigits } from "./exercises.js";
import { should as _should } from "chai";
const should = _should();

describe("Exercises - JavaScript Essentials Part 1", () => {

  describe("1. sumAny", () => {
    it("sumAny(4, '-2') returns 2", () => {
      sumAny(4, '-2').should.equal(2);
    });
    it("sumAny(1, 2.5) returns 3.5", () => {
      sumAny(1, 2.5).should.equal(3.5);
    });
    it("sumAny(1) returns 1", () => {
      sumAny(1).should.equal(1);
    });
    it("sumAny() returns 0", () => {
      sumAny().should.equal(0);
    });
    it("sumAny(1, true) returns NaN", () => {
      sumAny(1, true).should.be.NaN;
    });
    it("sumAny(1, 'foo') returns NaN", () => {
      sumAny(1, 'foo').should.be.NaN;
    });
  });

  describe("2. createPet", () => {
    it("createPet('Cat', 'Whiskers', 10, 'Fluffy cat.') object is correct", () => {
      const result = createPet('Cat', 'Whiskers', 10, 'Fluffy cat.');
      result.should.have.property('type').equal('cat');
      result.should.have.property('name').equal('Whiskers');
      result.should.have.property('age').equal(10);
      result.should.have.property('isSenior').equal(true);
      result.should.have.property('description').equal('Fluffy cat.');
    });    
    it("createPet('BIRD', 'Earl', 20, 'A clever bird.') object is correct", () => {
      const result = createPet('BIRD', 'Earl', 20, 'A clever bird.');
      result.should.have.property('type').equal('bird');
      result.should.have.property('name').equal('Earl');
      result.should.have.property('age').equal(20);
      result.should.have.property('isSenior').equal(true);
      result.should.have.property('description').equal('A clever bird.');
    });
    it("createPet(' Dog ', ' Spot ', 3, ' Cute pup. ') object is correct", () => {
      const result = createPet(' Dog ', ' Spot ', 3, ' Cute pup. ');
      result.should.have.property('type').equal('dog');
      result.should.have.property('name').equal('Spot');
      result.should.have.property('age').equal(3);
      result.should.have.property('isSenior').equal(false);
      result.should.have.property('description').equal('Cute pup.');
    });
  });

  describe("3. isTeen", () => {
    it("isTeen(12) returns false", () => {
      isTeen(12).should.be.false;
    });
    it("isTeen(13) returns true", () => {
      isTeen(13).should.be.true;
    });
    it("isTeen(15) returns true", () => {
      isTeen(15).should.be.true;
    });
    it("isTeen(19) returns true", () => {
      isTeen(19).should.be.true;
    });
    it("isTeen(20) returns false", () => {
      isTeen(20).should.be.false;
    });
  });

  describe("4. formatAddress", () => {
    it("formatAddress({\n\t  streetNumber: 526, \n\t  streetName: 'Market', \n\t  streetType: 'St', \n\t  city: 'Philadelphia', \n\t  state: 'PA', \n\t  zip: '19106' \n\t}) returns correct address", () => {
      const result = formatAddress({ streetNumber: 526, streetName: 'Market', streetType: 'St', city: 'Philadelphia', state: 'PA', zip: '19106' });
      result.should.equal('526 Market St\nPhiladelphia, PA 19106');
    });    
    it("formatAddress({\n\t  streetNumber: 701, \n\t  streetName: 'N 1st', \n\t  streetType: 'Ave', \n\t  city: 'Minneapolis', \n\t  state: 'MN', \n\t  zip: '55403' \n\t}) returns correct address", () => {
      const result = formatAddress({ streetNumber: 701, streetName: 'N 1st', streetType: 'Ave', city: 'Minneapolis', state: 'MN', zip: '55403' });
      result.should.equal('701 N 1st Ave\nMinneapolis, MN 55403');
    }); 
    it("formatAddress({\n\t  streetNumber: 2000, \n\t  streetName: \n\t  'Sycamore', \n\t  streetType: 'St', \n\t  city: 'Cleveland', \n\t  state: 'OH', \n\t  zip: '44113' \n\t}) returns correct address", () => {
      const result = formatAddress({ streetNumber: 2000, streetName: 'Sycamore', streetType: 'St', city: 'Cleveland', state: 'OH', zip: '44113' });
      result.should.equal('2000 Sycamore St\nCleveland, OH 44113');
    }); 
  });

  describe("5. average", () => {
    it("average([10, 15, 20]) returns '15.00'", () => {
      average([10, 15, 20]).should.equal('15.00');
    });
    it("average([6, 17, 45, 24.5]) returns '23.13'", () => {
      average([6, 17, 45, 24.5]).should.equal('23.13');
    });
    it("average([]) returns '0.00'", () => {
      average([]).should.equal('0.00');
    });
  });

  describe("6. getItemNumber", () => {
    it("getItemNumber('0-324-ZID12345678') returns '12345678'", () => {
      getItemNumber('0-324-ZID12345678').should.equal('12345678');
    });
    it("getItemNumber('0A12012345678') returns '12345678'", () => {
      getItemNumber('0A12012345678').should.equal('12345678');
    });
    it("getItemNumber('XID012345-001') returns '012345'", () => {
      getItemNumber('XID012345-001').should.equal('012345');
    });
    it("getItemNumber('XID012345-BB-001') returns '012345'", () => {
      getItemNumber('XID012345-BB-001').should.equal('012345');
    });
    it("getItemNumber('XID01234') returns '01234'", () => {
      getItemNumber('XID01234').should.equal('01234');
    });
    it("getItemNumber('1234567890A213B-020') returns '1234567890'", () => {
      getItemNumber('1234567890A213B-020').should.equal('1234567890');
    });
    it("getItemNumber('1234567890') returns '1234567890'", () => {
      getItemNumber('1234567890').should.equal('1234567890');
    });
  });

  describe("7. getItemStyle", () => {
    it("getItemStyle('XID012345-001') returns '001'", () => {
      getItemStyle('XID012345-001').should.equal('001');
    });
    it("getItemStyle('1234567890-A213B-0202') returns '0202'", () => {
      getItemStyle('1234567890-A213B-0202').should.equal('0202');
    });
    it("getItemStyle('0-324-ZID12345678') returns ''", () => {
      getItemStyle('0-324-ZID12345678').should.equal('');
    });
    it("getItemStyle('1234567890-A213B') returns ''", () => {
      getItemStyle('1234567890-A213B').should.equal('');
    });
    it("getItemStyle('1234567890-A2') returns ''", () => {
      getItemStyle('1234567890-A2').should.equal('');
    });
  });

  describe("8. extractOdds", () => {
    it("extractOdds([1, 2, 3, 4, 5]) removed odds from original array", () => {
      let original = [1, 2, 3, 4, 5];
      extractOdds(original).should.deep.equal([1, 3, 5]);
      original.should.deep.equal([2, 4]);
    });
    it("extractOdds([15, 3, 9, 20, 22, 5]) removed odds from original array", () => {
      let original = [15, 3, 9, 20, 22, 5];
      extractOdds(original).should.deep.equal([15, 3, 9, 5]);
      original.should.deep.equal([20, 22]);
    });
    it("extractOdds([2, 20, 4]) removed odds from original array", () => {
      let original = [2, 20, 4];
      extractOdds(original).should.deep.equal([]);
      original.should.deep.equal([2, 20, 4]);
    });
    it("extractOdds([[15, 3, 9]]) removed odds from original array", () => {
      let original = [15, 3, 9];
      extractOdds(original).should.deep.equal([15, 3, 9]);
      original.should.deep.equal([]);
    });
  });

  describe("9. matchingStrand", () => {
    it("matchingStrand('Aa') returns 'TT' preserving the input string", () => {
      let original = 'Aa';
      matchingStrand(original).should.equal('TT');
      original.should.equal('Aa');
    });
    it("matchingStrand('ATCG') returns 'TAGC' preserving the input string", () => {
      let original = 'ATCG';
      matchingStrand(original).should.equal('TAGC');
      original.should.equal('ATCG');
    });
    it("matchingStrand('gcgtaat') returns 'CGCATTA' preserving the input string", () => {
      let original = 'gcgtaat';
      matchingStrand(original).should.equal('CGCATTA');
      original.should.equal('gcgtaat');
    });
    it("matchingStrand('zattag') returns null and preserves the input string", () => {
      let original = 'zattag';
      should.equal(matchingStrand(original), null);
      original.should.equal('zattag');
    });
    it("matchingStrand('') returns '' preserving the input string", () => {
      let original = '';
      matchingStrand(original).should.equal('');
      original.should.equal('');
    });
  });

  describe("10. catsAndDogs", () => {
    it("catsAndDogs([{type: 'bird'}, {type: 'cat'}]) returns {\n\t  catCount: 1, \n\t  dogCount: 0, \n\t  otherCount: 1\n\t}", () => {
      catsAndDogs([{type: 'bird'}, {type: 'cat'}]).should.be.deep.equal({catCount: 1, dogCount: 0, otherCount: 1});
    });
    it("catsAndDogs([{type: 'cat'}, {type: 'dog'}, {type: 'cat'}]) returns {\n\t  catCount: 2, \n\t  dogCount: 1, \n\t  otherCount: 0\n\t}", () => {
      catsAndDogs([{type: 'cat'}, {type: 'dog'}, {type: 'cat'}]).should.be.deep.equal({catCount: 2, dogCount: 1, otherCount: 0});
    });
    it("catsAndDogs([{type: 'cat'}, {noType: true}]) returns {\n\t  catCount: 1, \n\t  dogCount: 0, \n\t  otherCount: 1\n\t}", () => {
      catsAndDogs([{type: 'cat'}, {noType: true}]).should.be.deep.equal({catCount: 1, dogCount: 0, otherCount: 1});
    });    
    it("catsAndDogs([]) returns {\n\t  catCount: 0, \n\t  dogCount: 0, \n\t  otherCount: 0\n\t}", () => {
      catsAndDogs([]).should.be.deep.equal({catCount: 0, dogCount: 0, otherCount: 0});
    });   
    it("catsAndDogs() returns {\n\t  catCount: 0, \n\t  dogCount: 0, \n\t  otherCount: 0\n\t}", () => {
      catsAndDogs().should.be.deep.equal({catCount: 0, dogCount: 0, otherCount: 0});
    });
  });

  describe("11. sumAll", () => {
    it("sumAll(1, 2, 3) returns 6", () => {
      sumAll(1, 2, 3).should.equal(6);
    });
    it("sumAll(1, -2, 3, 6) returns 8", () => {
      sumAll(1, -2, 3, 6).should.equal(8);
    });
    it("sumAll(1, '-3') returns -2", () => {
      sumAll(1, '-3').should.equal(-2);
    });
    it("sumAll(true, null, 'a') returns 0", () => {
      sumAll(true, null, 'a').should.equal(0);
    });
    it("sumAll() returns 0", () => {
      sumAll().should.equal(0);
    });
  });
  
  describe("12. sumDigits", () => {
    it("sumDigits(111) returns 3", () => {
      sumDigits(111).should.equal(3);
    });
    it("sumDigits(123456789) returns 45", () => {
      sumDigits(123456789).should.equal(45);
    });
    it("sumDigits(-111) returns -3", () => {
      sumDigits(-111).should.equal(-3);
    });
    it("sumDigits(-54321) returns -15", () => {
      sumDigits(-54321).should.equal(-15);
    });
    it("sumDigits() returns 0", () => {
      sumDigits().should.equal(0);
    });
  });
});
