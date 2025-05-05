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
- 