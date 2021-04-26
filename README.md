# NAB eCommerce

eCommerce application based built using Spring Boot.

## DEMO
Deployed to Heroku Cloud:

    https://nab-datvm.herokuapp.com/swagger-ui.html

Note: It is running on a free dyno, so the services go to sleep if not in use. For the first time, it may take some time to respond or might be not working
![Screenshot from running application](image/NAB-run.png?raw=true "Screenshot JWT Spring Security Demo")

## Requirements
This demo is build with Maven 3.6.x and Java 8.

## Feature
- Regular Username/Password authentication. (JWT token)
- Stores user information in the MySQL database.
- Provide function to manage a product and order.
## Usage

- Spring boot
- Spring security
- Spring JPA
- QueryDSLExecutor. (filter and query on multi field)
- Mapstruct. (cover to DTO Object)
- Maven
- MySql
- OAuth2 facebook. (to register by facebook).

Just start the application with the Spring Boot maven plugin (`mvn spring-boot:run`). The application is
running at [http://localhost:8080](http://localhost:8080).

## Backend
There are user accounts present to demonstrate the different levels of access to the endpoints in
the API.
```
Admin - admin:admin
```

Note: If you want to use method required admin role. Please sign in via http://localhost:8080/auth/signin api to get token.
![Screenshot from running application](image/authen.png?raw=true "Screenshot JWT Spring Security Demo")
### Using another database

Actually this demo is using an embedded MySql database that is automatically configured by Spring Boot. If you want to connect 
to another database you have to specify the connection in the *application.yml* in the resource directory. Here is an example for a MySQL DB:

```
spring:
  jpa:
      hibernate:
        ddl-auto: create
      show-sql: false
      generate-ddl: true
    datasource:
      url: jdbc:mysql://{database_host}:3306/{database_name}
      username: {username}
      password: {password}
      initialization-mode: always
      driver-class-name: com.mysql.jdbc.Driver
```

---------------------------------------

## REST API Endpoints
All inputs and outputs use JSON format.

**To open Swagger (interactive) API documentation, navigate your browser to [YOUR-URL]/swagger-ui.html**


```
/auth
  POST /auth/signin - Login using username: b and password:b
  GET /oauth2/authorization/facebook - sign up via facebook and username is email and password is : 123123
  POST /auth/signup
/branch
  POST /admin/branch/add                - add new branch
  DELETE /admin/branch/delete/{id}      - delete branch by ID
  GET /admin/branch/list                - get list branch

/customer
  GET /customer/order                   - get order detail
  POST /customer/order/add              - do order
  GET /customer/product                 - product detail
  POST /customer/product/list           - product list, using predicate for search
    ```
    json search example
        {
          "dest": true,                 // sort by dest
          "field": "string",            // field want to sort
          "metricFilters": [            // list condition filter
            {
              "condition": "string",    // using for number condition ( '<' '>' '=')
              "field": "string",        // field filter
              "value": "string"         // confition filter
            }
          ],
          "page": 0,                    // page number
          "pageSize": 0                 // page size
        }
    ```
/product
  POST /admin/order/list               - list order
  POST /admin/product/add              - add new product
  DELETE /admin/product/delete/{id}    - delete product by id
  PUT /admin/product/update            - update product
  PUT /admin/product/update/quantity   - update product quantity

```



## Database relationship
![Screenshot from running application](image/database.png?raw=true "Database relation")

## UML diagram
![Screenshot from running application](image/UML.jpg?raw=true "Database relation")
Please feel free to send me some feedback or questions!
