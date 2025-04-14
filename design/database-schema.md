## Memes Table

| Column Name   | Data Type                              | Required | Description                                   |
|---------------|----------------------------------------|----------|-----------------------------------------------|
| `id`          | `INT` (Primary Key, Auto-Increment)    | Yes      | Unique identifier for each meme               |
| `ur`          | `TEXT`                                 | Yes      | URL of the meme image                         |
| `text_top`    | `VARCHAR(55)`                          | No       | Text displayed at the top of the meme         |
| `text_bottom` | `VARCHAR(55)`                          | No       | Text displayed at the bottom of the meme      |
| `created_at`  | `TIMESTAMP` (Default: CURRENT_TIMESTAMP) | No    | Timestamp of when the meme was created        |

### Notes:
- This table stores metadata for memes including image URL and optional text.
- `id` is auto-generated and serves as the primary key.
- `created_at` will automatically set the time the record was inserted.
