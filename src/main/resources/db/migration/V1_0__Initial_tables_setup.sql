--
CREATE TABLE IF NOT EXISTS artists (
   id BIGINT GENERATED BY DEFAULT AS IDENTITY,
   name VARCHAR(100) NOT NULL,
    profile TEXT,

    CONSTRAINT artist_pkey PRIMARY KEY (id)
);

--
CREATE TABLE IF NOT EXISTS albums (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY,
    title VARCHAR(100) NOT NULL,
    release_year INTEGER NOT NULL,
    artist_id BIGINT,

    CONSTRAINT album_pkey PRIMARY KEY (id),
    CONSTRAINT artist_fkey FOREIGN KEY (artist_id) REFERENCES artists(id)
);

--
CREATE INDEX idx_artist_id ON albums(artist_id);