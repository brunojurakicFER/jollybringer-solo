import { exec } from 'child_process';
import { cp, rm } from 'fs/promises';
import { join } from 'path';

const SPRING_STATIC = '../jollybringer/src/main/resources/static';

async function buildAndCopy() {
  try {
    // Run vite build
    await new Promise((resolve, reject) => {
      exec('npm run build', (error) => {
        if (error) reject(error);
        resolve();
      });
    });

    // Clean spring static folder
    await rm(SPRING_STATIC, { recursive: true, force: true });

    // Copy dist to spring static
    await cp('./dist', SPRING_STATIC, { recursive: true });

    console.log('Build and copy completed successfully!');

  } catch (error) {
    console.error('Error during build process:', error);
    process.exit(1);
  }
}

buildAndCopy();