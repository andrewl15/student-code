import { defineConfig } from 'cypress'

export default defineConfig({
    defaultCommandTimeout: 3000,
    screenshotOnRunFailure: false,
    video: false,
    e2e: {
        specPattern: 'cypress/e2e/**/*.{cy,spec}.{js,jsx,ts,tsx}',
        baseUrl: 'http://localhost:4173'
    },
    component: {
        specPattern: 'src/**/__tests__/*.{cy,spec}.{js,ts,jsx,tsx}',
        devServer: {
            framework: 'react',
            bundler: 'vite'
        }
    }
});
