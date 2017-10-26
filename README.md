# Basic Web Application

## Overview
This is a simple Product Management web application. This application allows users to search produces by provided conditions. 
If user is authorized, use is allowed to create, update and/or delete a product.

## Requirements

### Data Models

**Product**
- Id
- Name
- Description
- Price
- CategoryId

**Category**
- Id
- Name

**User**
- Username
- Password

### Features
**General**
- All pages should be written using Servlet and Thymeleaf technology.
- Form validation from client-side is not required but server-side must ensure the valid input values.

**Search Product**
- By Name (Text input)
- By Category (pre-defined values: Category 1, Category 2, Category 3, Category 4)
- By Price range (Number input)
- Order by: Name, Price
- Search result must be displayed in a grid (table) with all attributes (Name, Category, Price and Description)
- Allow search with any combination of above criteria.
- Click on an item will go to Edit Product page. (Invisible for unauthorized user)
- Click on ‘Add New’ button will go to New Product page (Invisible for unauthorized user)

**Manage Product (New/Edit Product page)**
- Only available for authorized user
- Form to add/edit new Product (name, category, price, description)
  - If add new, all fields are blank
  - If edit, all fields are pre-populated using selected product
- Button ‘Update’ / ‘Add New’ to persist data
- Button ‘Delete’
- After a transaction, back to Search Product Page

**Login Page**
- Default homepage
- A link to Search Product Page
- Form login: Username, password
- Password must be encrypted when stored in DB
- Login Fail: display error message
- Login success: go to Search Product Page
- Unauthorized user can only access Search Product Page.
- Unauthorized user will go to an error page if trying to access other pages (except for login page and Search Product Page)

**Logout Page**
- Display Logout link only login successfully
- Logout success: User could not access to authorized pages

**Show Active Sessions**
- Display number of active sessions on Search Product Page.
- Active session = authorized user (login successfully)
- 1 user login on 2 different browsers will count as 2
- There is no logout so we can use session timeout to reduce the count.

## Development
- [JDK 8](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](http://maven.apache.org/download.cgi)
- [PostgreSQL](https://www.postgresql.org/download/)
- IDE: [IntelliJ](https://www.jetbrains.com/idea/download) or [Eclipse for JavaEE](http://www.eclipse.org/downloads/eclipse-packages/)
- Create fresh database in case you don't have one
- Update DB connection. In code-base, the DB connection is **hardcode**, located at class `vn.kms.fundamentals.basicwebapp.web.listener.AppInitializer`
- Create your DB migration files for your new feature or fix. It locates at `src/main/resources/db/migration`
- DB migration file format: V<timestamp>__your_description.sql

## Build
`mvn clean package`
 
## Deployment
**DEV mode**
`mvn jetty:run`

**PROD mode**
copy `target\basicwebapp.war` to Servlet container (such as: [Tomcat](http://tomcat.apache.org/))
