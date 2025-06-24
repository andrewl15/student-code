package com.techelevator;

public class Exercises {

    /**
     * For the purposes of this exercise, the following naming rules are tested:
     *
     * Variable names:
     *  - must start with a lowercase character a-z.
     *  - underscore ('_') characters are not allowed.
     *  - dollar sign ('$') characters are not allowed.
     *  - must be at least two characters in length.
     *  - You are encouraged to create descriptive names and are required to camel case them as appropriate.
     *
     * Constant names:
     *  - must start with an uppercase character A-Z.
     *  - dollar sign ('$') characters are not allowed.
     *  - must be at least two characters in length.
     *  - You are encouraged to create descriptive names and are required to upper case them as appropriate.
     *
     * Due to practical limitations, camel case and pascal case are not tested other than checking the first
     * character of the name is lower case for variables and upper case for constants. Your instructor will
     * manually review your solution checking for the correct casing.
     */

	public static void main(String[] args) {

        /* Exercise 1
        1. 4 birds are sitting on a branch. 1 flies away. How many birds are left on
        the branch?
        */
		// ### EXAMPLE:
		int birdsOnABranch = 4;
		int birdsThatFlyAway = 1;
		int birdsRemaining = birdsOnABranch - birdsThatFlyAway;

        /* Exercise 2
        2. There are 6 birds and 3 nests. How many more birds are there than
        nests?
        */
		// ### EXAMPLE:
		int numberOfBirds = 6;
		int numberOfNests = 3;
		int numberOfExtraBirds = numberOfBirds - numberOfNests;

        /* Exercise 3
        3. 3 raccoons are playing in the woods. 2 go home to eat dinner. How
        many raccoons are left in the woods?
        */
		int numberOfRacccons = 3;
		int numberOfRaccoonsThatGoHome = 2;
		int numberOfRemainingRacoons = numberOfRacccons - numberOfRaccoonsThatGoHome;

        /* Exercise 4
        4. There are 5 flowers and 3 bees. How many less bees than flowers?
        */
		int numberOfFlowers = 5;
		int numberOfBees = 3;
		int numberOfLessBeesThanFlowers = numberOfFlowers - numberOfBees;

        /* Exercise 5
        5. 1 lonely pigeon was eating breadcrumbs. Another pigeon came to eat
        breadcrumbs, too. How many pigeons are eating breadcrumbs now?
        */
		int numberOfPigeonsEating = 1;
		numberOfPigeonsEating += 1;

        /* Exercise 6
        6. 3 owls were sitting on the fence. 2 more owls joined them. How many
        owls are on the fence now?
        */
		int numberOfOwls = 3;
		numberOfOwls += 2;

        /* Exercise 7
        7. 2 beavers were working on their home. 1 went for a swim. How many
        beavers are still working on their home?
        */
		int numberOfBeavers = 2;
		numberOfBeavers -= 1;

        /* Exercise 8
        8. 2 toucans are sitting on a tree limb. 1 more toucan joins them. How
        many toucans in all?
        */
		int numberOfToucans = 2;
		numberOfToucans += 1;

        /* Exercise 9
        9. There are 4 squirrels in a tree with 2 nuts. How many more squirrels
        are there than nuts?
        */
		int numberOfSquirrels = 4;
		int numberOfNuts = 2;
		int numberOfMoreSquirrelsThanNuts = numberOfSquirrels - numberOfNuts;

        /* Exercise 10
        10. Mrs. Hilt found a quarter, 1 dime, and 2 nickels. How much money did
        she find?
        */
		double quarterQuantity = 0.25;
		double dimeQuantity = 0.1;
		double nickelQuantity = 0.1;
		double moneyFound = quarterQuantity + dimeQuantity + nickelQuantity;

        /* Exercise 11
        11. Mrs. Hilt's favorite first grade classes are baking muffins. Mrs. Brier's
        class bakes 18 muffins, Mrs. MacAdams's class bakes 20 muffins, and
        Mrs. Flannery's class bakes 17 muffins. How many muffins does first
        grade bake in all?
        */
		int numberOfBrierMuffins = 18;
		int numberOfMacAdamsMuffins = 20;
		int numberOfFlanneryMuffins = 17;
		int numberOfMuffinsInGrade = numberOfBrierMuffins + numberOfMacAdamsMuffins + numberOfFlanneryMuffins;

        /* Exercise 12
        12. Mrs. Hilt bought a yoyo for 24 cents and a whistle for 14 cents. How
        much did she spend in all for the two toys?
        */
		double yoyoAmount = 0.24;
		double whistleAmount = 0.14;
		double totalSpent = yoyoAmount + whistleAmount;

        /* Exercise 13
        13. Mrs. Hilt made 5 Rice Krispies Treats. She used 8 large marshmallows
        and 10 mini marshmallows.How many marshmallows did she use
        altogether?
        */
		int numberOfLargeMarshmallows = 8;
		int numberOfMiniMarshmallows = 10;
		int numberOfTotalMarshmallows = numberOfLargeMarshmallows + numberOfMiniMarshmallows;

        /* Exercise 14
        14. At Mrs. Hilt's house, there was 29 inches of snow, and Brecknock
        Elementary School received 17 inches of snow. How much more snow
        did Mrs. Hilt's house have?
        */
		int amountOfSnowAtHilt = 29;
		int amountOfSnowAtSchool = 17;
		int amountMoreSnowAtHilt = amountOfSnowAtHilt - amountOfSnowAtSchool;

        /* Exercise 15
        15. Mrs. Hilt has $10. She spends $3 on a toy truck and $2.50 on a pencil
        case. How much money does she have left?
        */
		int totalAmountOfMoney = 10;
		int amountSpentOnTruck = 3;
		double amountSpentOnPencil = 2.50;
		double amountOfMoneyLeft = totalAmountOfMoney - (amountSpentOnTruck + amountSpentOnPencil);

        /* Exercise 16
        16. Josh had 16 marbles in his collection. He lost 7 marbles. How many
        marbles does he have now?
        */
		int numberOfMarblesInCollection = 16;
		int numberOfMarblesLost = 7;
		int numberOfMarblesLeft = numberOfMarblesInCollection - numberOfMarblesLost;

        /* Exercise 17
        17. Megan has 19 seashells. How many more seashells does she need to
        find to have 25 seashells in her collection?
        */
		int numberOfCurrentSeashells = 19;
		int numberOfSeashellsToReach25Shells = 25 - numberOfCurrentSeashells;

        /* Exercise 18
        18. Brad has 17 balloons. 8 balloons are red and the rest are green. How
        many green balloons does Brad have?
        */
		int numberOfBalloons = 17;
		int numberOfRedBalloons = 8;
		int numberOfGreenBalloons = numberOfBalloons - numberOfRedBalloons;

        /* Exercise 19
        19. There are 38 books on the shelf. Marta put 10 more books on the shelf.
        How many books are on the shelf now?
        */
		int numberOfBooksOnShelf = 38;
		int numberOfBooksAdded = 10;
		int numberOfTotalBooks = numberOfBooksOnShelf + numberOfBooksAdded;

        /* Exercise 20
        20. A bee has 6 legs. How many legs do 8 bees have?
        */
		int numberOfBeeLegs = 6;
		int numberOfEightBees = 8;
		int numberOfLegsOnEightBees = numberOfBeeLegs * numberOfEightBees;


        /* Exercise 21
        21. Mrs. Hilt bought an ice cream cone for 99 cents. How much would 2 ice
        cream cones cost?
        */
		double costOfCone = 0.99;
		double costOfTwoCones = costOfCone * 2;

        /* Exercise 22
        22. Mrs. Hilt wants to make a border around her garden. She needs 125
        rocks to complete the border. She has 64 rocks. How many more rocks
        does she need to complete the border?
        */
		int totalRocksNeeded = 125;
		int currentRockAmount = 64;
		int requiredRocksRemaining = totalRocksNeeded - currentRockAmount;

        /* Exercise 23
        23. Mrs. Hilt had 38 marbles. She lost 15 of them. How many marbles does
        she have left?
        */
		int numberTotalMarbles = 38;
		int numberOfHerMarblesLost = 15;
		int numberOfRemainingMarbles = numberTotalMarbles - numberOfHerMarblesLost;

        /* Exercise 24
        24. Mrs. Hilt and her sister drove to a concert 78 miles away. They drove 32
        miles and then stopped for gas. How many miles did they have left to drive?
        */
		int numberOfMilesToDrive = 78;
		int numberOfMilesDriven = 32;
		int numberOfMilesToDriveLeft = numberOfMilesToDrive - numberOfMilesDriven;

        /* Exercise 25
        25. Mrs. Hilt spent 1 hour and 30 minutes shoveling snow on Saturday
        morning and 45 minutes shoveling snow on Saturday afternoon. How
        much total time (in minutes) did she spend shoveling snow?
        */
		int amountOfTimeShovelingSnowInMorning = 90;
		int amountOfTimeShovelingSnowInAfternoon = 45;
		int amountOfTotalTimeShovelingSnow = amountOfTimeShovelingSnowInMorning + amountOfTimeShovelingSnowInAfternoon;



        /* Exercise 26
        26. Mrs. Hilt bought 6 hot dogs. Each hot dog cost 50 cents. How much
        money did she pay for all of the hot dogs?
        */
		int numberOfHotDogs = 6;
		double priceOfHotdog = 0.50;
		double priceOfPurchasedHotdogs = numberOfHotDogs * priceOfHotdog;

        /* Exercise 27
        27. Mrs. Hilt has 50 cents. A pencil costs 7 cents. How many pencils can
        she buy with the money she has?
        */
		double amountOfCents = 0.50;
		double costOfPencil = 0.07;
		int numberOfPencilsToBuy = (int)(amountOfCents / costOfPencil);

        /* Exercise 28
        28. Mrs. Hilt saw 33 butterflies. Some of the butterflies were red and others
        were orange. If 20 of the butterflies were orange, how many of them
        were red?
        */
		int numberOfSeenButterflies = 33;
		int numberOfOrangeButterflies = 20;
		int numberOfRedButterflies = numberOfSeenButterflies - numberOfOrangeButterflies;

        /* Exercise 29
        29. Kate gave the clerk $1.00. Her candy cost 54 cents. How much change
        should Kate get back?
        */
		double amountOfMoneyToClerk = 1.00;
		double costOfCandy = 0.54;
		double amountOfMoneyRemaining = amountOfMoneyToClerk - costOfCandy;

        /* Exercise 30
        30. Mark has 13 trees in his backyard. If he plants 12 more, how many trees
        will he have?
        */
		int initialNumberOfTrees = 13;
		int numberOfTreesAfterPlanting = initialNumberOfTrees + 12;

        /* Exercise 31
        31. Joy will see her grandma in two days. How many hours until she sees
        her?
        */
		int numberOfDaysUntilGrandma = 2;
		int numberOfHoursPerDay = 24;
		int numberOfHoursUntilGrandma = numberOfDaysUntilGrandma * numberOfHoursPerDay;

        /* Exercise 32
        32. Kim has 4 cousins. She wants to give each one 5 pieces of gum. How
        much gum will she need?
        */
		int numberOfCousins = 4;
		int numberOfGumToGiveEach = 5;
		int totalAmountOfGumGiven = numberOfCousins * numberOfGumToGiveEach;

        /* Exercise 33
        33. Dan has $3.00. He bought a candy bar for $1.00. How much money is
        left?
        */
		double amountOfDansMoney = 3.00;
		double costOfCandyBar = 1.00;
		double amountAfterBuyingCandyBar = amountOfDansMoney - costOfCandyBar;

        /* Exercise 34
        34. 5 boats are in the lake. Each boat has 3 people. How many people are
        on boats in the lake?
        */
		int numberOfBoatsInLake = 5;
		int numberOfPeoplePerBoat = 3;
		int totalPeopleOnTheLake =  numberOfBoatsInLake * numberOfPeoplePerBoat;

        /* Exercise 35
        35. Ellen had 380 legos, but she lost 57 of them. How many legos does she
        have now?
        */
		int numberOfLegos = 380;
		int numberOfLostLegos = 57;
		int numberOfLegosLeft = numberOfLegos - numberOfLostLegos;

        /* Exercise 36
        36. Arthur baked 35 muffins. How many more muffins does Arthur have to
        bake to have 83 muffins?
        */
		int numberOfBakedMuffins = 35;
		int numberOfMuffinsToHit85 = 83 - numberOfBakedMuffins;

        /* Exercise 37
        37. Willy has 1400 crayons. Lucy has 290 crayons. How many more
        crayons does Willy have then Lucy?
        */
		int numberOfWillysCrayons = 1400;
		int numberOfLucysCrayons = 290;
		int numberOfWillysCrayonsComparedToLucy = numberOfWillysCrayons - numberOfLucysCrayons;

        /* Exercise 38
        38. There are 10 stickers on a page. If you have 22 pages of stickers, how
        many stickers do you have?
        */
		int numberOfStickersOnPage = 10;
		int numberOfPages = 22;
		int numberOfTotalStickers = numberOfStickersOnPage * numberOfPages;

        /* Exercise 39
        39. There are 100 cupcakes for 8 children to share. How much will each
        person get if they share the cupcakes equally?
        */
		double numberOfCupcakes = 100;
		double numberOfChildren = 8;
		double numberOfCupcakesPerChild = (numberOfCupcakes / numberOfChildren);

        /* Exercise 40
        40. She made 47 gingerbread cookies which she will distribute equally in
        tiny glass jars. If each jar is to contain six cookies, how many
        cookies will not be placed in a jar?
        */
		int numberOfGingerbreadCookies = 47;
		int amountOfCookiesPerJar = 6;
		int numberOfRemainingCookies = (numberOfGingerbreadCookies%amountOfCookiesPerJar);

        /* Exercise 41
        41. She also prepared 59 croissants which she plans to give to her 8
        neighbors. If each neighbor received an equal number of croissants,
        how many will be left with Marian?
        */
		int preparedCroissants = 59;
		int numberOfNeighbors = 8;
		int numberOfRemainingCroissants = preparedCroissants%numberOfNeighbors;

        /* Exercise 42
        42. Marian also baked oatmeal cookies for her classmates. If she can
        place 12 cookies on a tray at a time, how many trays will she need to
        prepare 276 oatmeal cookies at a time?
        */
		int numberOfCookiesPerTray = 12;
		int numberOfCookiesToBake = 276;
		int numberOfRequiredTrays = numberOfCookiesToBake / numberOfCookiesPerTray;

        /* Exercise 43
        43. Marian’s friends were coming over that afternoon so she made 480
        bite-sized pretzels. If one serving is equal to 12 pretzels, how many
        servings of bite-sized pretzels was Marian able to prepare?
        */
		int numberOfBiteSizedPretzels = 480;
		int onePretzelServing = 12;
		int numberOfServingsCreated = numberOfBiteSizedPretzels / onePretzelServing;

        /* Exercise 44
        44. Lastly, she baked 53 lemon cupcakes for the children living in the city
        orphanage. If two lemon cupcakes were left at home, how many
        boxes with 3 lemon cupcakes each were given away?
        */
		int numberOfLemonCupcakes = 53;
		int numberOfCupcakesPerBox = 3;
		int numberOfBoxesOfCupcakes = (numberOfLemonCupcakes - 2) / numberOfCupcakesPerBox;


        /* Exercise 45
        45. Susie's mom prepared 74 carrot sticks for breakfast. If the carrots
        were served equally to 12 people, how many carrot sticks were left
        uneaten?
        */
		int numberOfCarrotSticks = 74;
		int numberOfPeopleToShareCarrots = 12;
		int numberOfRemainingCarrots = numberOfCarrotSticks%numberOfPeopleToShareCarrots;

        /* Exercise 46
        46. Susie and her sister gathered all 98 of their teddy bears and placed
        them on the shelves in their bedroom. If every shelf can carry a
        maximum of 7 teddy bears, how many shelves will be filled?
        */
		int numberOfGatheredTeddyBears = 98;
		int numberOfBearsPerShelf = 7;
		int numberOfShelvesFilled = numberOfGatheredTeddyBears / numberOfBearsPerShelf;

        /* Exercise 47
        47. Susie’s mother collected all family pictures and wanted to place all of
        them in an album. If an album can contain 20 pictures, how many
        albums will she need if there are 480 pictures?
        */
		int numberOfPicturesPerAlbum = 20;
		int numberOfPictures = 480;
		int numberOfRequiredAlbums = numberOfPictures / numberOfPicturesPerAlbum;

        /* Exercise 48
        48. Joe, Susie’s brother, collected all 94 trading cards scattered in his
        room and placed them in boxes. If a full box can hold a maximum of 8
        cards, how many boxes were filled and how many cards are there in
        the unfilled box?
        */
		int totalTradingCards = 94;
		int maximumCardsPerBox = 8;
		int numberOfBoxesFilled = totalTradingCards / maximumCardsPerBox;
		int numberOfCardsInUnfilledBox = totalTradingCards%maximumCardsPerBox;

        /* Exercise 49
        49. The Milky Way galaxy contains 300 billion stars. The Andromeda galaxy
        contains 1 trillion stars. How many stars do the two galaxies contain combined?
        */
		long numberOfMilkyWayStars = 300_000_000_000L;
		long numberOfAdromedaStars = 1_000_000_000_000L;
		long totalStarsOfBothGalaxys = numberOfMilkyWayStars + numberOfAdromedaStars;

        /* Exercise 50
        50. Cristina baked 17 croissants. If she planned to serve this equally to
        her seven guests, how many will each have?
        */
		double numberOfBakedCroissants = 17;
		double numberOfGuests = 7;
		double numberOfCroissantsPerGuest = numberOfBakedCroissants / numberOfGuests;

	    /* Exercise 51
	    51. Bill and Jill are house painters. Bill can paint a standard room in 2.15 hours, while Jill averages
	    1.90 hours. How long will it take the two painters working together to paint 5 standard rooms?
	    Hint: Calculate the rate at which each painter can complete a room (rooms / hour), combine those rates, 
	    and then divide the total number of rooms to be painted by the combined rate.
	    */
		double billPaintHours = 2.15;
		double jillPaintHours = 1.90;
		double combinedPaintingHours = (1 / billPaintHours + 1 / jillPaintHours);
		double totalTime = 5 / combinedPaintingHours;

	    /* Exercise 52
	    52. Create and assign variables to hold a first name, last name, and middle initial. Using concatenation,
		build an additional variable to hold the full name in the order of last name, first name, middle initial. The
		last and first names should be separated by a comma followed by a space, and the middle initial must end
		with a period. Use "Grace", "Hopper, and "B" for the first name, last name, and middle initial.
		Example: "John", "Smith, "D" —> "Smith, John D."
	    */
		String firstName = "Grace";
		String lastName = "Hopper";
		char initial = 'B';
		String fullName = lastName + ", " + firstName + " " + initial + ".";

	    /* Exercise 53
	    53. The distance between New York and Chicago is 800 miles, and the train has already travelled 537 miles.
	    What percentage of the trip as a whole number has been completed?
	    */
		double numberOfMilesBetween = 800;
		double milesAlreadyTraveled = 537;
		int percentOfTripDone = (int)((milesAlreadyTraveled / numberOfMilesBetween) * 100);
	}

}
