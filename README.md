# Analytics Service

This documentation provides information about the "Analytics Service" microservice, which analyzes user progress data to provide insights and visualizations.


link http://localhost:8081/analytics-service/
<<<<<<< HEAD
## Table of Contents
- [API Endpoints](#api-endpoints)
- [Usage](#usage)

## Api Endpoints
The "Analytics service" provides the following API endpoints
- `GET /user-progress/{userId}` :  Retrieves the workout progress for a specific user.
- `GET /workout-progress/{workoutId}` : Retrieves the workout progress for a specific workout.
- `GET /exercise-progress/{exerciseId}` : Retrieves the workout progress for a specific exercise.
- `GET /progress-by-date/{userId}` : Retrieves the workout progress for a specific user within a date range.
- `GET /progress-by-muscle-group/{userId}` : Retrieves the workout progress for a specific user based on a muscle group.
- `GET /total-volume/{userId}` : Retrieves the total volume of weight lifted by a specific user.


## Usage

To use the "Analytics Service," make HTTP requests to the provided API endpoints as described in the API documentation. Here's a high-level overview of how to use the service:

1. **Retrieve User Progress**: To retrieve workouts for a specific user, you can make a GET request to the `/user-progress/{userId}` endpoint, where `:userID` is the user's ID.

2. **Retrieve Workout Progress**: To retrieve workout progress, you can make a GET request to the `/workout-progress/{workoutId}` endpoint, where `:workoutId` is the workout's ID.

3. **Retrieve Exercise Progress**: To retrieve the last exercise progress, make a GET request to the `/exercise-progress/{exerciseId}` endpoint, where `:exerciseId` is the exercise's ID.

4. **Retrieve Progress by Date**: To retrieve workouts for a specific user within a date range, you can make a GET request to the `/analytics/progress-by-date/{userId}` endpoint, providing `startDate` and `endDate` as query parameters in the format yyyy-MM-dd.( Example: `/analytics/progress-by-date/123?startDate=2023-01-01&endDate=2023-12-31`)

5. **Retrieve Progress by Muscle Group**: To retrieve workouts for a specific user and muscle group, you can make a GET request to the `/analytics/progress-by-muscle-group/{userId}` endpoint, providing `muscleGroup` as a query parameter. ( Example: `/analytics/progress-by-muscle-group/123?muscleGroup=Legs`)

6. **Retrieve Total Volume**: To retrieve the total workout volume for a specific user, you can make a GET request to the `/analytics/total-volume/{userId}` endpoint, where `:userID` is the user's ID.

You can interact with the service using your preferred API client (e.g., Insomnia, Postman) or by integrating it into your web application.

