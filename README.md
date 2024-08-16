# Comment Module for Social Media Application

Welcome to the Comment Module for Social Media Application, similar to Twitter/Facebook. This platform offers a comprehensive suite of services designed to facilitate and enhance social media interactions. It enables users to access detailed information about social media posts, including their content, authorship, timestamps, and reaction statistics. In addition, it supports robust comment management functionalities, allowing users to create, retrieve, and manage comments and replies on posts, ensuring dynamic and engaging interactions. Furthermore, the platform handles the updating and tracking of reactions, such as likes and dislikes, across posts, comments, and replies, providing an integrated approach to managing and analyzing user engagement and feedback. This all-in-one service ensures a seamless and enriched user experience for interacting with social media content and managing online interactions.
## Project Details

The Comment Module includes the following key functionalities:

1. **Post Information**: Provides details about a social media post including its, author, content, timestamp and reactions such as likes and dislikes

2. **Comments Management**: Handles the creation and retrieval of paginated comments and replies on social media posts. It includes functionality to post new comments, reply to existing comments, reply to existing replies and fetch all comments and replies related to a specific post or comment.

3. **Reaction Update**: Manages reactions such as likes and dislikes on posts, comments, and replies. It updates the reaction counts for various social media interactions

## Getting Started

To build the project, use the following command:

```bash
mvn clean package
```

To run the tests, use:

```bash
mvn test
```

To deploy locally

```bash
mvn spring-boot:run
```


## Prerequisites
Make sure you have MongoDB Community Edition installed, as the application relies on it for data storage.

## Usage
Detailed usage instructions and API documentation will be provided separately in the project documentation. The API endpoints and expected request/response formats will be documented to help you integrate this module with the social media application.

## Use Swagger
```agsl
https://localhost:8080/swagger-ui/index.html
```