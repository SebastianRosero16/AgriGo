import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
      '@/api': path.resolve(__dirname, './src/api'),
      '@/auth': path.resolve(__dirname, './src/auth'),
      '@/components': path.resolve(__dirname, './src/components'),
      '@/context': path.resolve(__dirname, './src/context'),
      '@/data-structures': path.resolve(__dirname, './src/data-structures'),
      '@/hooks': path.resolve(__dirname, './src/hooks'),
      '@/layouts': path.resolve(__dirname, './src/layouts'),
      '@/logic': path.resolve(__dirname, './src/logic'),
      '@/modules': path.resolve(__dirname, './src/modules'),
      '@/pages': path.resolve(__dirname, './src/pages'),
      '@/router': path.resolve(__dirname, './src/router'),
      '@/store': path.resolve(__dirname, './src/store'),
      '@/utils': path.resolve(__dirname, './src/utils'),
    },
  },
  server: {
    port: 3000,
    strictPort: true,
    proxy: {
      '/auth': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false,
      },
      '/farmers': {
        target: 'http://localhost:8082',
        changeOrigin: true,
        secure: false,
      },
      '/stores': {
        target: 'http://localhost:8083',
        changeOrigin: true,
        secure: false,
      },
      '/marketplace': {
        target: 'http://localhost:8086',
        changeOrigin: true,
        secure: false,
      },
      '/ai': {
        target: 'http://localhost:8085',
        changeOrigin: true,
        secure: false,
      },
      '/price-comparator': {
        target: 'http://localhost:8086',
        changeOrigin: true,
        secure: false,
      },
    },
  },
  build: {
    outDir: 'dist',
    sourcemap: false,
    minify: 'esbuild',
    chunkSizeWarningLimit: 1000,
    rollupOptions: {
      output: {
        manualChunks: undefined, // Desactivar chunks manuales para forzar rebuild
      },
    },
  },
});
