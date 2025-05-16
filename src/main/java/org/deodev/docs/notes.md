# 25 - 04 - 2025

- Made schemas for entities in the facebook_clone database

### Schemas
- `users(id, name, email, password, created_at)`
- `posts(id, user_id, content, created_at, updated_at)`
- `comments(id, post_id, user_id, content, created_at, updated_at)`
- `likes(id, post_id, comment_id, user_id, created_at)`  


- Added DatabaseUtil class

# 27 - 04 - 2025

- Created a DAO for User entity, wrote some methods and tests
- Created a User entity/model
- Created a DTO for User 
- Created a UserService class


# 28 - 04 - 2025

- Created UserController 
- add init and doPost methods

# 02 - 05 - 2025

- Created Response DTO's
- Created Validation exception and DTOValidator and change validation logic
- Update classes to match new Validation Logic
- Fix Tomcat integration issues 

# 05 - 05 - 2025
- Updated UserService to AuthService
- Created Login controller and dto
- Updated Validation classes and created a Validator interface and a login dto validator
- Created Post entity and PostDAO with save method and logic
- Created CreatePostDTO and validator 
- Created endpoint for creating a post with the controller and service


# 08 - 05 - 2025
- Created endpoint for getting post by id
- Updated PostDao with getById method and logic
- Created endpoint for getting all posts 
- Added a new response Generic Api Response
- Created endpoint for updating posts by id
- Created endpoint for deleting post by id

# 12 - 05 - 2025
- Created endpoint for creating a comment:   
Created the comment entity, the dao, the dto, the service, a validator and the controller
- Fixed an error in the post DAO and endpoints


# 13 - 05 - 2025 
- Fixed a bug in the createComment controller
- Created endpoint for getting comment by id:   
Created a controller and added methods in the dao and in the service


# 15 - 05 - 2025 
- Fixed maven cache bug
- updated entities, dto's and responses that used Lombok, and changed all to use manual
- Added new feature and created endpoint for getting all comments
- Added new feature and created endpoint for updating a comment by id
- Added new feature and created endpoint for deleting a comment by id

# 16 - 05 - 2025
- Added new feature and created endpoint for creating a like for post and comment
- Added new feature and created endpoint for deleting a like by id
- Updated the get post by id endpoint by add a comments field, to add the list of comments of the post
- 