# Analytics Service

This documentation provides information about the "Analytics Service" microservice, which analyzes user progress data to provide insights and visualizations.

## Table of Contents
- [API Endpoints](#api-endpoints)


## Api Endpoints
The "Analytics service" provides the following API endpoints
- `GET /user-progress/{userId}` :  Retrieves the workout progress for a specific user.
- `GET /workout-progress/{workoutId}` : Retrieves the workout progress for a specific workout.
- `GET /exercise-progress/{exerciseId}` : Retrieves the workout progress for a specific exercise.
- `GET /progress-by-date/{userId}` : Retrieves the workout progress for a specific user within a date range.
- `GET /progress-by-muscle-group/{userId}` : Retrieves the workout progress for a specific user based on a muscle group.
- `GET /total-volume/{userId}` : Retrieves the total volume of weight lifted by a specific user.
- `POST /add-progress` : Adds a new workout progress entry.
- `PUT /update-progress/{progressId}` : Updates an existing workout progress entry.
- `DELETE /delete-progress/{progressId}` : Deletes an existing workout progress entry.




