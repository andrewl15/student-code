import { defineConfig }  from 'cypress';

export default defineConfig({
  defaultCommandTimeout: 500,
  video: false,
  screenshotOnRunFailure: false,
  // reporter: 'cypress/reporters/te-reporter',
  e2e: {
    testIsolation: false,
    specPattern: 'cypress/e2e/**/*.{cy,spec}.{js,jsx,ts,tsx}'
  },
});
