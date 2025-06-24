import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import dns from 'node:dns'

dns.setDefaultResultOrder('ipv4first')

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
  },
})
