CREATE TABLE Resena (
    id INTEGER PRIMARY KEY,
    re_title TEXT,
    re_content TEXT,
    re_start TEXT,
    restaurant_id INTEGER,
    created_at TEXT,
    user_name TEXT,
    user_last_name TEXT,
    profile_user TEXT,
    FOREIGN KEY(restaurant_id) REFERENCES Cerca(id)
);
