import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import MinReporter from './test/MinReporter'

// https://vitejs.dev/config/
export default defineConfig({
  css: {
    modules: {
      localsConvention: 'camelCase'
    },
  },
  plugins: [react()],
  test: {
    globals: true,
    environment: 'jsdom',
    reporters: [MinReporter],
  },
})
