# Unit testing

The purpose of this exercise is to provide you with opportunities to practice [unit testing][what-is-unit-testing] a codebase that doesn't include any automated tests.

## Learning objectives

After completing this exercise, you'll understand:

* How to write unit tests in a "legacy" codebase.
* How unit testing can show that the code is functioning correctly.
* How to structure unit tests in an organized, readable format.
* Why unit tests are important.
* How to write readable unit tests.

## Evaluation criteria and functional requirements

> Code without tests is *bad code*. It doesn't matter how well written it is; it doesn't matter how pretty or object-oriented or well-encapsulated it is. With tests, we can change the behavior of our code quickly and verifiably. Without them, we really don't know if our code is getting better or worse.”
~ Michael Feathers, _Working Effectively with Legacy Code_

You're hired as a new developer at Acme Inc. As such, you have inherited some [legacy code][what-is-legacy-code] that doesn't have any unit tests. Your job is to create unit tests for all classes to ensure that the code is thoroughly tested.

If you found a defect, document the bug on the `BugReport.txt`. There are a total of six defects. After documenting and identifying the defect, fix the production code.

The basis of your exercise submission uses the following criteria:

* The project must not have any build errors.
* You documented the correct bugs on `BugReport.txt` (again, there are 6 bugs)
* Unit tests pass as expected after you've fixed the bugs.
* There is appropriate code coverage to verify that the application code functions as expected.
* Use of good test method names that clearly state what's tested.

## Getting started

1. Open the exercise project in IntelliJ.
2. Create a test class for the class you'll test. For instance, if you're testing the `StringBits` class, create a class in the `test/java/com/techelevator` directory called `StringBitsTest`.
3. The comment preceding each method explains its intended behavior. The tests you'll write verify the method's actual behavior matches the intended behavior.
4. Write test methods in the test class to verify the class under test works as expected.


## Tips and tricks

### Unit testing reduces regressions

Real applications grow over time, and it can be difficult, if not impossible, to foresee all the consequences of changing code. New features may make assumptions regarding the behavior of existing components which inadvertently alters the application's behavior. Bug fixes can fix one thing, but break something else. Unit tests can help detect these **regressions**.

>Note: Regression has several meanings in software engineering, but in general, it means what the word implies: the *opposite of progress*. Whatever change occurred, it made things worse.

Ideally, as you build your application, you're scaffolding it with unit tests. For every new block of code you write, or change you make, you need to write and run unit tests. Unit tests can reveal any changes that inadvertently broke something as you're working. *Unit testing can't prevent regressions, but it can alert you to them.*

### Unit testing can make you a faster developer

Consider the command-line applications you worked on over the past few exercises. Although you can test these applications manually, you'd have to do several things:

1. Make a code change
2. Start the application
3. Click through the menus
4. Manually review results
5. Verify that code works as expected

A unit test automates this effort, is repeatable, quicker, and is therefore more reliable.

Writing good unit tests helps you ship code faster and more reliably than developers who don't.

### Naming matters

Test methods must clearly state what it's testing for in the method name.

For instance, if you want to verify that an `Add()` method returns 4 when it's passed 2 and 2, a good name for the test method is something like `add_should_return_4_when_2_and_2_are_passed`. This is verbose, but it's preferred to use verbose method names in unit tests as they clearly say what method and conditions it's testing for.

---

[what-is-unit-testing]: https://searchsoftwarequality.techtarget.com/definition/unit-testing
[what-is-legacy-code]: http://wiki.c2.com/?LegacyCode
