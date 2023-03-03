[![CircleCI](https://dl.circleci.com/status-badge/img/gh/nguyenhoanganhtuan1206/Java-Template/tree/main.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/nguyenhoanganhtuan1206/Java-Template/tree/main)

[![codecov](https://codecov.io/gh/nguyenhoanganhtuan1206/Java-Template/branch/main/graph/badge.svg?token=FNMAA4L20W)](https://codecov.io/gh/nguyenhoanganhtuan1206/Java-Template)


# Steps

1. Initialize Repository with the following stuff were correctly set up
    - Git ignore
    - Circle CI
    - Code COV
    - Flyway
    - SpringBoot
    - Some libraries (eg: Loombok)
2. Integrate Swagger
    - Add an empty API (eg GET api/v1/books)
    - Add library
    - Check the Swagger UI and make sure it works correctly
3. Add database
    - Add migration files to create the database tables following the requirement
    - Add migration file to create two roles
    - Add migration file to create an admin
    - Run the application with docker started
    - Make sure the database tables were created with the correct structure and relationships
    - Check the roles were created
4. Create CRUD for users
5. Create CRUD for books
6. Add Login endpoint
7. Integrate security and define role foreach endpoints
8. Create POSTMAN collections
9. Deployment