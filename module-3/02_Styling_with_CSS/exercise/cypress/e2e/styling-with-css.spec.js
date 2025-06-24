function compareStylePixelsEQ(element, styleAttribute, expectedValue, decimalPlaces) {
    // Get the style value as a number
    let actualValue = getPixels(element, styleAttribute);

    // Round and compare actual to expected. If decimalPlaces wasn't passed in, don't round.
    if (decimalPlaces === undefined ? actualValue === expectedValue : compareRounded(actualValue, expectedValue, decimalPlaces) === 0) {
        return true;
    } else {
        cy.log(`Expected ${styleAttribute} to equal ${expectedValue} but got ${actualValue}`);
        return false;
    }
}

function getPixels(element, styleAttribute) {
    return parseFloat(getComputedStyle(element)[styleAttribute].replace('px', ''));
}

function compareRounded(x, y, decimalPlaces) { return Math.round(x * Math.pow(10, decimalPlaces)) - Math.round(y * Math.pow(10, decimalPlaces)) }

function devicePixelRatioWarning() {
    return `*** WARNING ***\r\nDevice pixel ratio is ${window.devicePixelRatio}. This usually means either your display is being scaled or your browser is at a zoom level other than 100%. This can affect some test results, so please be sure to scale to 100% before running the tests.`
}

describe('Styling with CSS Exercise', () => {
    beforeEach(() => {
        if (window.devicePixelRatio !== 1) {
            cy.log(devicePixelRatioWarning());
            console.log(devicePixelRatioWarning());
        }
        cy.visit('./index.html');
        cy.get('style').should('have.length', 0); // should not have a style tag anywhere in document.
        cy.get('[style]').should('have.length', 0); // should be no elements with a style attribute
    });

    describe('Step One: Style aside', () => {
        describe('aside styles', () => {
            it('Position that keeps it in the same place on the screen while the page scrolls.', () => {
                cy.get('aside').should('have.css', 'position').should('equal', 'fixed');
            });
            it('A blue border separating it from the rest of the page.', () => {
                cy.get('aside').should('have.css', 'border-right-color').should('equal', 'rgba(0, 173, 238, 0.4)');
                cy.get('aside').should('have.css', 'border-right-style').should('equal', 'solid');

                cy.get('aside').should('satisfy', ($a) => {
                    return compareStylePixelsEQ($a[0], 'border-right-width', 1, 2);
                });
            });
            it('A height so that blue border extends to the bottom of the viewport.', () => {
                let docHeight;
                let viewportHeight = Cypress.config().viewportHeight;
                cy.document().then((d) => {
                    return d.documentElement.clientHeight;
                }).then(height => docHeight = height);

                cy.get('aside').should('satisfy', ($a) => {
                    const asideHeight = $a.height();
                    return asideHeight == docHeight || asideHeight == viewportHeight;
                });
            });
            it('Give it a width of 200px.', () => {
                cy.get('aside').should('have.css', 'width').should('equal', '200px');
            });
        });
        describe('image styles', () => {
            it('Give it a height or width.', () => {
                cy.get('aside > img').should('satisfy', ($a) => {
                    if (compareStylePixelsEQ($a[0], 'height', 120, 2)) {
                        return compareStylePixelsEQ($a[0], 'width', 120, 2);
                    } else {
                        return false;
                    }
                });
            });
            it('Center the image.', () => {
                cy.get('aside > img').should('satisfy', ($i) => {
                    let ml = parseFloat($i.css('margin-left').replace('px', ''));
                    if (ml === 0) ml = $i.position().left;
                    return ml >= 35 && ml <= 45;
                });
            });
        });
        describe('main styles', () => {
            it('Move main over.', () => {
                cy.get('main').should("satisfy", ($m) => {
                    return ($m.css('margin-left').replace('px', '') >= 200 && $m.css('position') === 'static')
                        || ($m.css('padding-left').replace('px', '') >= 200 && $m.css('position') === 'static')
                        || ($m.css('left').replace('px', '') >= 200 && $m.css('position') === 'relative')
                });
            });
        });
    });

    describe('Step Two: Style nav', () => {
        describe('li styles', () => {
            it('Center align text.', () => {
                cy.get('li').should('have.css', 'text-align').should('equal', 'center');
            });
            it('Make all the letters uppercase.', () => {
                cy.get('li').should('have.css', 'text-transform').should('equal', 'uppercase');
            });
            it('Blue border separating each element.', () => {
                cy.get('li').should('have.css', 'border-bottom-color').should('equal', 'rgba(0, 173, 238, 0.4)');
                cy.get('li').should('have.css', 'border-bottom-style').should('equal', 'solid');
                cy.get('li').should('satisfy', ($li) => {
                    return compareStylePixelsEQ($li[0], 'border-bottom-width', 1, 2);
                });

            });
            it('Lines don\'t go across the entire sidebar.', () => {
                cy.get('li').should('satisfy', ($li) => {
                    const asideWidth = Cypress.$('aside').css('width').replace('px', '');
                    const liWidth = $li.css('width').replace('px', '');
                    return liWidth < asideWidth - 1;
                });
            });
            it('Center li elements.', () => {
                cy.get('li').should('satisfy', ($li) => {
                    const ml = parseFloat($li.css('margin-left').replace('px', ''));
                    const mr = parseFloat($li.css('margin-right').replace('px', ''));
                    return (ml > 0 || mr > 0);
                });
            });
            it('Set the list style to not have bullet points.', () => {
                cy.get('li').should('have.css', 'list-style-type').should('equal', 'none');
            });
            it('Add space between lines and text.', () => {
                cy.get('li').should("satisfy", ($li) => {
                    return $li.css('padding-top').replace('px', '') > 0
                        || $li.css('padding-bottom').replace('px', '') > 0
                });
            });
        });
        describe('a styles', () => {
            it('Bold the text.', () => {
                cy.get('li a').should(($a) => {
                    const fw = parseInt($a.css('font-weight'));
                    expect(fw).to.be.gt(400);
                });
            });
            it('Set text color to blue.', () => {
                cy.get('li a').should('have.css', 'color').should('equal', 'rgb(0, 173, 238)'); // browser returns RGB code even though hex was used
            });
            it('Remove the underline.', () => {
                cy.get('li:last-child a').should('have.css', 'text-decoration-line').should('equal', 'none');
            });
            it('Except for first one.', () => {
                cy.get('li:first-child').should('satisfy', ($li) => {
                    return $li.css('text-decoration-line') === 'underline' || $li.children(0).css('text-decoration-line') === 'underline';
                });
                cy.get('li').not('li:first-child').should('satisfy', ($li) => {
                    return $li.css('text-decoration-line') === 'none' && $li.children(0).css('text-decoration-line') === 'none';
                });
            });
        });
    });

    describe('Step Three: Style main', () => {
        describe('header image styles', () => {
            it('Header logo height.', () => {
                cy.get('header img').should('have.css', 'height').should('equal', '100px');
            });
            it('Header logo should have spacing below it.', () => {
                cy.get('header img').should('satisfy', ($img) => {
                    return $img.css('margin-bottom').replace('px', '') > 0
                        || $img.css('padding-bottom').replace('px', '') > 0;
                });
            });
        });
        describe('banner image styles', () => {
            it('Width should fit in viewport.', () => {
                cy.get('img.banner').should('satisfy', ($img) => {
                    const mainWidth = parseInt(Cypress.$('main').css('width').replace('px', ''));
                    const mainPaddingLeft = parseInt(Cypress.$('main').css('padding-left').replace('px', ''));
                    const mainPaddingRight = parseInt(Cypress.$('main').css('padding-right').replace('px', ''));
                    const bannerWidth = parseInt($img.css('width').replace('px', ''));

                    return bannerWidth == mainWidth - mainPaddingLeft - mainPaddingRight;
                });
            });
        });
        describe('main styles', () => {
            it('Main content block has clearance on left and right.', () => {
                cy.get('main').should('have.css', 'padding-right').should('equal', '20px');
                cy.get('main').should('satisfy', ($m) => {
                    return $m.css('padding-left') === '220px'
                        || $m.css('margin-left') === '220px'
                        || ($m.css('padding-left') === '200px' && $m.css('margin-left') === '20px')
                        || ($m.css('padding-left') === '20px' && $m.css('margin-left') === '200px')
                });
            });
        });
    });

    describe('Step Four: Style section', () => {
        describe('div styles', () => {
            it('Divs on the same line.', () => {
                cy.get('section div').should('satisfy', ($div) => {
                    const sectionWidth = Cypress.$('section').css('width').replace('px', '');
                    const divWidth = $div.css('width').replace('px', '');
                    return divWidth <= sectionWidth * 0.33;
                });
                cy.get('section div').should('have.css', 'display').should('equal', 'inline-block');
            });
            it('Borders are between the elements and are green.', () => {
                // Look at the middle div since it should be bordered on one side or the other
                cy.get('section div#our-work').should('satisfy', ($div) => {
                    // Based on that, test the middle div and the other div depending if it's left or right
                    if ($div.css('border-left-width') === '1px') {
                        return ($div.css('border-left-style') === 'solid' && $div.css('border-left-color') === 'rgba(170, 239, 135, 0.8)')
                            && (
                                Cypress.$('#contact-us').css('border-left-style') === 'solid' && 
                                Cypress.$('#contact-us').css('border-left-color') === 'rgba(170, 239, 135, 0.8)' &&
                                Cypress.$('#contact-us').css('border-left-width') === '1px'
                            );
                    }
                    else if ($div.css('border-right-width') === '1px') {
                        return ($div.css('border-right-style') === 'solid' && $div.css('border-right-color') === 'rgba(170, 239, 135, 0.8)')
                            && (
                                Cypress.$('#what-we-do').css('border-right-style') === 'solid' && 
                                Cypress.$('#what-we-do').css('border-right-color') === 'rgba(170, 239, 135, 0.8)' &&
                                Cypress.$('#what-we-do').css('border-right-width') === '1px'
                            );
                    } else {
                        return false;
                    }
                });
            });
            it('Spacing around text.', () => {
                cy.get('section div').should('satisfy', ($div) => {
                    return $div.css('padding-left').replace('px', '') > 0
                        || $div.css('padding-right').replace('px', '') > 0
                        || $div.css('margin-left').replace('px', '') > 0
                        || $div.css('margin-right').replace('px', '') > 0
                });
            });
            it('Spacing above divs.', () => {
                cy.get('section div').should('satisfy', ($div) => {
                    return $div.css('padding-top').replace('px', '') > 0
                        || $div.css('margin-top').replace('px', '') > 0
                });
            });
        });
        describe('h2 styles', () => {
            it('Set text color to green.', () => {
                cy.get('section h2').should('have.css', 'color').should('equal', 'rgb(140, 195, 111)'); // browser returns RGB code even though hex was used
            });
        });
        describe('i styles', () => {
            it('Space between image and text.', () => {
                cy.get('section div h2 i').should('satisfy', ($i) => {
                    return $i.css('padding-right').replace('px', '') > 0
                        || $i.css('margin-right').replace('px', '') > 0;
                });
            });
        });
    });
});
