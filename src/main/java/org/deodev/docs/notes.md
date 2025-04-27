# 25-04-2025

- Made schemas for entities in the facebook_clone database

### Schemas
- `users(id, name, email, password, created_at)`
- `posts(id, user_id, content, created_at, updated_at)`
- `comments(id, post_id, user_id, content, created_at, updated_at)`
- `likes(id, post_id, comment_id, user_id, created_at)`  


- Added DatabaseUtil class
- 


