# Experiment Documentation: Docker and PostgreSQL

## Task 1: Using the PostgreSQL Docker Image

### 1.1. Objective
The goal of this task was to set up a PostgreSQL database inside a Docker container and interact with it from a Spring Boot application.

### 1.2. Steps Followed

1. **Pulling the PostgreSQL Image:**
   First, I pulled the official PostgreSQL image from Docker Hub:
   ```bash
   docker pull postgres

Running the PostgreSQL Container: I then ran the PostgreSQL container with the following command:

docker run -p 5432:5432 
-e POSTGRES_USER=myuser -e POSTGRES_PASSWORD=mypassword -e POSTGRES_DB=mydatabase -d --name my-postgres postgres

I then connected to he database with Dbeaver but struggled with authentication. I reslved the issue when i shut down the postgreSQL service on windows.

##Task 2:

I created the dockerfile to build and serve my svelte-app
I then built the docker image with the command:
docker build -t svelte-app .
After building the image, i ran the container with this command:
docker run -p 3000:3000 svelte-app

