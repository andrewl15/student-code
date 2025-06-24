const { defineConfig } = require('cypress')

module.exports = defineConfig({
  defaultCommandTimeout: 500,
  video: false,
  screenshotOnRunFailure: false,
  reporter: 'cypress/reporters/te-reporter',
  e2e: {
    specPattern: 'cypress/e2e/**/*.{cy,spec}.{js,jsx,ts,tsx}'
  },
})
