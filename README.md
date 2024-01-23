# Analytics Service

This documentation provides information about the "Analytics Service" microservice, which analyzes user progress data to provide insights and visualizations.


link http://localhost:8081/analytics-service/
<<<<<<< HEAD
## Table of Contents
- [API Endpoints](#api-endpoints)
- [Usage](#usage)

## Api Endpoints
The "Analytics service" provides the following API endpoints
- `GET /weight-progress/{userId}/{exerciseName}` : Retrieves the weight progress for a specific user and exercise within a date range.
- `GET /user-training-info/{userId}` :  Retrieves information about the user's gym visits and training details within a date range.
- `GET /average-weight-progress/{userId}/{muscleGroup}` : Retrieves the average weight progress for a specific user and muscle group within a date range.



## Usage

To use the "Analytics Service," make HTTP requests to the provided API endpoints as described in the API documentation. Here's a high-level overview of how to use the service:


1**Retrieve Weight Progress for Exercise**: To retrieve the weight progress for a specific exercise and user within a date range, make a GET request to the `/weight-progress/{userId}/{exerciseName}` endpoint. Include startDate and endDate as query parameters in the format yyyy-MM-dd if you want to specify a date range. (Example: `/weight-progress/1/Pull-ups?startDate=2023-01-01&endDate=2025-12-31`)

2**Retrieve User Training Information**: To retrieve user training information, including the number of gym visits and detailed training information for each day within a specified time range, make a GET request to the `/user-training-info/{userId}` endpoint. Include startDate and endDate as query parameters in the format yyyy-MM-dd if you want to specify a date range. (Example: `/user-training-info/1?startDate=2023-01-01&endDate=2025-12-31`)
   
3**Retrieve Average Weight Progress by Muscle Group**: To retrieve the average weight progress for a specific muscle group and user within a date range, make a GET request to the `/average-weight-progress/{userId}/{muscleGroup}` endpoint. Include startDate and endDate as query parameters in the format yyyy-MM-dd if you want to specify a date range. (Example: `/average-weight-progress/2/Legs?startDate=2023-01-01&endDate=2025-12-31`)

You can interact with the service using your preferred API client (e.g., Insomnia, Postman) or by integrating it into your web application.

