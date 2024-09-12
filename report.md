Experiment Report: Polling Application with Svelte and Spring Boot

Introduction
The objective of this experiment was to develop a polling application using Svelte for the frontend and Spring Boot for the backend. The users can create polls, vote, and see the results in real-time. The application also implements user authentication, including login and logout functionality.

Methodology
I implemented a full-stack application where the frontend (Svelte) communicates with the backend (Spring Boot) using REST APIs. Key functionalities include:

User registration and login.
Poll creation (public or private).
Voting on polls.
Displaying poll results.
The backend was set up to handle user sessions and authentication using HTTP sessions.

Results
The application was successfully developed with the following core features:

Poll creation with options for making them public or private and expiration date.
User login and logout functionality.
Real-time voting system with updates to poll results.

Issues Encountered
During development, a few key issues were faced:

Session Management: There were issues in tracking the logged-in user session. Initially, the GET /api/users/current endpoint was not working, and it required proper session management via the HttpSession in Spring Boot.
Incorrect Display of Private Polls: Polls were being marked as public even when created as private due to an incorrect mapping of the isPrivate field. This issue was resolved by adding the @JsonProperty annotation to ensure proper serialization.
Frontend Page Navigation: After logging in or logging out, the navigation in the Svelte frontend wasn’t updating correctly. The issue was related to missing state updates, and I had to ensure the navigation state (currentPage) was updated after login/logout.

Code
The code used in this experiment can be found in the following files:

Frontend (Svelte)
Backend (Spring Boot)
Conclusion
This project provided a good opportunity to integrate a modern frontend framework (Svelte) with a traditional backend (Spring Boot). While there were issues with session management and state updates, these were resolved by improving the handling of HTTP sessions and state in Svelte.
