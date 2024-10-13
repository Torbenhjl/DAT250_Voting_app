# syntax=docker/dockerfile:1

# Step 1: Build the Svelte app using Rollup
FROM node:18-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY . .

# Install dependencies
RUN yarn install

# Build the Svelte app for production using Rollup
RUN yarn build

# Step 2: Serve the Svelte app with sirv
FROM node:18-alpine

# Install sirv globally to serve the app
RUN yarn global add sirv-cli

# Copy the build files from the previous step
COPY --from=build /app/public /app/public

# Expose port 3000 to serve the app
EXPOSE 3000

# Command to serve the app with sirv
CMD ["sirv", "public", "--no-clear", "--port", "3000"]
