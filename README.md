This is a food delivery system project. It is a microservice based project that comprises of 4 services, a eureka server and an api Gateway.

Dependencies Used
Spring Boot
Spring Web
Spring Data JPA
Hibernate
ModelMapper
Validation Framework
Spring-Security
OpenFeign
Spring Cloud Eureka
Spring Cloud Gateway

1) Food Service
The Food Service provides methods to register and retrieve information about restaurants, cuisines, and dishes. It also includes filtering capabilities for cuisines and dishes based on various criteria.

Features: 
Register Restaurant
Method: registerRestaurant
Description: Registers a new restaurant.
Parameters:
restaurant (Restaurant): The restaurant entity to be registered.
Returns: The registered restaurant object.

Get Restaurant Information
Method: getRestaurant
Description: Retrieves information about a specific restaurant.
Parameters:
rest_id (Integer): The ID of the restaurant.
Returns: The restaurant DTO object.

Register Cuisine
Method: registerCuisine
Description: Registers a new cuisine for a specified restaurant.
Parameters:
cuisine (Cuisine): The cuisine entity to be registered.
rest_id (Integer): The ID of the restaurant.
Returns: The registered cuisine object.

Get Cuisine Information
Method: getCuisine
Description: Retrieves information about a specific cuisine.
Parameters:
name (String): The name of the cuisine.
Returns: The cuisine object.

Get All Cuisines for a Restaurant
Method: getAllCuisines
Description: Retrieves all cuisines for a specified restaurant.
Parameters:
restId (Integer): The ID of the restaurant.
Returns: The restaurant object with its cuisines.

Register Dish
Method: registerDish
Description: Registers a new dish for a specified restaurant and cuisine.
Parameters:
dish (Dish): The dish entity to be registered.
restId (Integer): The ID of the restaurant.
cuisineId (Integer): The ID of the cuisine.
Returns: The registered dish object.

Get Restaurant Menu
Method: getMenu
Description: Retrieves the menu of a specified restaurant, optionally filtered by cuisines and price.
Parameters:
restaurantId (Integer): The ID of the restaurant.
inputCuisines (List<String>, optional): List of cuisines to filter by.
inputPrice (Integer, optional): Price to filter by.
Returns: The restaurant object with its filtered menu.

Get All Restaurants by Cuisine
Method: getAllRestaurants
Description: Retrieves all restaurants that offer specified cuisines.
Parameters:
cuisineName (List<String>): List of cuisines.
Returns: A list of response DTOs with restaurant and cuisine information.

2) Order Service
The Order Service provides methods to place and cancel orders, as well as retrieve order history for both restaurants and users.

Features
Place Order
Method: placeOrder
Description: Places a new order for a specified user and restaurant, including the selected cuisines and dishes.
Parameters:
order (Orders): The order entity to be placed.
userId (Integer): The ID of the user placing the order.
restId (Integer): The ID of the restaurant.
cuisines (List<Integer>): List of cuisine IDs.
dishes (List<Integer>): List of dish IDs.
Returns: The placed order object.

Cancel Order
Method: cancelOrder
Description: Cancels an order if it is still in the “Order Placed” status.
Parameters:
id (Integer): The ID of the order to be cancelled.
Returns: true if the order was successfully cancelled, false otherwise.

Restaurant Order History
Method: restOrderHistory
Description: Retrieves the order history for a specific restaurant, optionally filtered by cuisine and date.
Parameters:
restId (Integer): The ID of the restaurant.
cuisineId (Integer, optional): The ID of the cuisine to filter by.
inputDate (LocalDate, optional): The date to filter by.
Returns: A list of order history DTOs.

User Order History
Method: userOrderHistory
Description: Retrieves the order history for a user on a specific date.
Parameters:
inputDate (LocalDate): The date to filter by.
Returns: A list of lists of order details.

3) MyUser Service
User Service
This microservice is part of the Food Delivery System project. It handles user registration, authentication, and user information management.

Endpoints
Register New User
URL: /register
Method: POST
Description: Registers a new user.
Response:
201 Created: Successfully registered user.
400 Bad Request: User registration failed.

Get User Details
URL: /{userId}/getUser
Method: GET
Description: Retrieves details of a registered user.
Response:
200 OK: Returns the user details.
404 Not Found: User not found.

User Login
URL: /login
Method: POST
Description: Validates user credentials and returns user details.
Response:
200 OK: Returns the user details.

Get User ID by Username
URL: /getUserId
Method: GET
Description: Retrieves the user ID for a given username.
Request Parameters:
username (String): The username of the user.
Response:
200 OK: Returns the user ID.
404 Not Found: User not found.


Reset Password
URL: /{userId}/resetPassword
Method: PUT
Description: Resets the password for a specified user.
Path Variable:
userId (Long): The ID of the user.
Request Parameters:
newPassword (String): The new password.
Response:
200 OK: Password reset successfully.
400 Bad Request: Password reset failed.

Update User Information
URL: /{userId}/update
Method: PUT
Description: Updates the information of a specified user.
Path Variable:
userId (Long): The ID of the user.
Response:
200 OK: User updated successfully.
400 Bad Request: User update failed.

Get All Users
URL: /getAll
Method: GET
Description: Retrieves a list of all registered users.
Response:
200 OK: Returns a list of users.


4) Delivery Service
This microservice is part of the Food Delivery System project. It handles the initiation of delivery processes for orders.

Endpoints
Start Delivery
URL: /startDelivery
Method: POST
Description: Initiates the delivery process for a specified order.
Request Parameters:
order_id (Integer): The ID of the order to be delivered.
Response: 200 OK if the delivery process is successfully started.
