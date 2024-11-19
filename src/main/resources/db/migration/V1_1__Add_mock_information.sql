-- Artists information
INSERT INTO artists(id, name, profile, genre) VALUES (1000, 'Adele', 'Long profile description 1 ...',  'Pop');
INSERT INTO artists(id, name, profile, genre) VALUES (1001, 'Drake', 'Long profile description 1 ...',  'Hip Hop');
INSERT INTO artists(id, name, profile, genre) VALUES (1002, 'Taylor Swift', 'Long profile description 1 ...',  'Country/Pop');
INSERT INTO artists(id, name, profile, genre) VALUES (1003, 'Ed Sheeran', 'Long profile description 1 ...',  'Pop');
INSERT INTO artists(id, name, profile, genre) VALUES (1004, 'Beyoncé', 'Long profile description 1 ...',  'R&B');
INSERT INTO artists(id, name, profile, genre) VALUES (1005, 'The Weeknd', 'Long profile description 1 ...',  'R&B');
INSERT INTO artists(id, name, profile, genre) VALUES (1006, 'Bruno Mars', 'Long profile description 1 ...',  'Pop/R&B');
INSERT INTO artists(id, name, profile, genre) VALUES (1007, 'Kendrick Lamar', 'Long profile description 1 ...',  'Hip Hop');
INSERT INTO artists(id, name, profile, genre) VALUES (1008, 'Billie Eilish', 'Long profile description 1 ...',  'Pop');
INSERT INTO artists(id, name, profile, genre) VALUES (1009, 'Justin Bieber', 'Long profile description 1 ...',  'Pop/R&B');
INSERT INTO artists(id, name, profile, genre) VALUES (1010, 'Post Malone', 'Long profile description 1 ...',  'Hip Hop/Rock');
INSERT INTO artists(id, name, profile, genre) VALUES (1011, 'Lady Gaga', 'Long profile description 1 ...',  'Pop');
INSERT INTO artists(id, name, profile, genre) VALUES (1012, 'Rihanna', 'Long profile description 1 ...',  'Pop/R&B');
INSERT INTO artists(id, name, profile, genre) VALUES (1013, 'Sam Smith', 'Long profile description 1 ...',  'Pop/Soul');
INSERT INTO artists(id, name, profile, genre) VALUES (1014, 'Sia', 'Long profile description 1 ...',  'Pop');
INSERT INTO artists(id, name, profile, genre) VALUES (1015, 'Imagine Dragons', 'Long profile description 1 ...',  'Rock');
INSERT INTO artists(id, name, profile, genre) VALUES (1016, 'Coldplay', 'Long profile description 1 ...',  'Rock');
INSERT INTO artists(id, name, profile, genre) VALUES (1017, 'Maroon 5', 'Long profile description 1 ...',  'Pop/Rock');
INSERT INTO artists(id, name, profile, genre) VALUES (1018, 'Dua Lipa', 'Long profile description 1 ...',  'Pop');
INSERT INTO artists(id, name, profile, genre) VALUES (1019, 'Hozier', 'Long profile description 1 ...',  'Rock/Folk');
INSERT INTO artists(id, name, profile, genre) VALUES (1020, 'Shawn Mendes', 'Long profile description 1 ...',  'Pop');
INSERT INTO artists(id, name, profile, genre) VALUES (1021, 'Lil Nas X', 'Long profile description 1 ...',  'Country/Hip Hop');
INSERT INTO artists(id, name, profile, genre) VALUES (1022, 'Travis Scott', 'Long profile description 1 ...',  'Hip Hop');
INSERT INTO artists(id, name, profile, genre) VALUES (1023, 'Nicki Minaj', 'Long profile description 1 ...',  'Hip Hop');
INSERT INTO artists(id, name, profile, genre) VALUES (1024, 'Katy Perry', 'Long profile description 1 ...',  'Pop');
INSERT INTO artists(id, name, profile, genre) VALUES (1025, 'P!nk', 'Long profile description 1 ...',  'Pop/Rock');
INSERT INTO artists(id, name, profile, genre) VALUES (1026, 'Zayn Malik', 'Long profile description 1 ...',  'Pop/R&B');
INSERT INTO artists(id, name, profile, genre) VALUES (1027, 'Tones and I', 'Long profile description 1 ...',  'Pop');
INSERT INTO artists(id, name, profile, genre) VALUES (1028, 'BTS', 'Long profile description 1 ...',  'K-Pop');


-- Albums information
-- Adele
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2000, '19', 2008, 1000);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2001, '21', 2011, 1000);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2002, '25', 2015, 1000);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2003, '30', 2021, 1000);

-- Drake
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2004, 'Thank Me Later', 2010, 1001);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2005, 'Take Care', 2011, 1001);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2006, 'Nothing Was the Same', 2013, 1001);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2007, 'Scorpion', 2018, 1001);

-- Taylor Swift
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2008, 'Taylor Swift', 2006, 1002);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2009, 'Fearless', 2008, 1002);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2010, 'Red', 2012, 1002);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2011, '1989', 2014, 1002);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2012, 'Folklore', 2020, 1002);

-- Ed Sheeran
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2013, '+', 2011, 1003);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2014, 'x', 2014, 1003);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2015, '+', 2021, 1003);

-- Beyonce
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2016, 'Dangerously in Love', 2003, 1004);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2017, 'B''Day', 2006, 1004);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2018, 'I Am... Sasha Fierce', 2008, 1004);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2019, 'Lemonade', 2016, 1004);

-- The Weeknd
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2020, 'Kiss Land', 2013, 1005);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2021, 'Beauty Behind the Madness', 2015, 1005);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2022, 'Starboy', 2016, 1005);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2023, 'After Hours', 2020, 1005);

-- “Bruno Mars
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2024, 'Doo-Wops & Hooligans', 2010, 1006);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2025, 'Unorthodox Jukebox', 2012, 1006);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2026, '24K Magic', 2016, 1006);

-- Kendrick Lamar
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2027, 'good kid, m.A.A.d city', 2012, 1007);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2028, 'To Pimp a Butterfly', 2015, 1007);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2029, 'DAMN.', 2017, 1007);

-- Billie Eilish
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2030, 'When We All Fall Asleep, Where Do We Go?', 2019, 1008);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2031, 'Happier Than Ever', 2021, 1008);

-- Justin Bieber
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2032, 'My World 2.0', 2010, 1009);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2033, 'Purpose', 2015, 1009);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2034, 'Changes', 2020, 1009);

-- Post Malone
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2035, 'Stoney', 2016, 1010);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2036, 'Beerbongs & Bentleys', 2018, 1010);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2037, 'Hollywood''s Bleeding', 2019, 1010);

-- Lady Gaga
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2038, 'The Fame', 2008, 1011);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2039, 'Born This Way', 2011, 1011);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2040, 'ARTPOP', 2013, 1011);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2041, 'Joanne', 2016, 1011);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2042, 'Chromatica', 2020, 1011);

-- Rihanna
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2043, 'Music of the Sun', 2005, 1012);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2044, 'A Girl Like Me', 2006, 1012);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2045, 'Good Girl Gone Bad', 2007, 1012);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2046, 'Rated R', 2009, 1012);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2047, 'Loud', 2010, 1012);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2048, 'Talk That Talk', 2011, 1012);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2049, 'Unapologetic', 2012, 1012);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2050, 'Anti', 2016, 1012);

-- Sam Smith
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2051, 'In the Lonely Hour', 2014, 1013);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2052, 'The Thrill of It All', 2017, 1013);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2053, 'Love Goes', 2020, 1013);

-- Sia
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2054, 'Healing Is Difficult', 2001, 1014);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2055, 'Colour the Small One', 2004, 1014);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2056, 'Some People Have Real Problems', 2008, 1014);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2057, 'We Are Born', 2010, 1014);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2058, '1000 Forms of Fear', 2014, 1014);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2059, 'This Is Acting', 2016, 1014);

-- Imagine Dragons
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2060, 'Night Visions', 2012, 1015);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2061, 'Smoke + Mirrors', 2015, 1015);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2062, 'Evolve', 2017, 1015);

-- Coldplay
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2063, 'Parachutes', 2000, 1016);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2064, 'A Rush of Blood to the Head', 2002, 1016);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2065, 'X&Y', 2005, 1016);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2066, 'Viva la Vida or Death and All His Friends', 2008, 1016);

-- Maroon 5
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2067, 'Songs About Jane', 2002, 1017);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2068, 'It Won''t Be Soon Before Long', 2007, 1017);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2069, 'Hands All Over', 2010, 1017);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2070, 'Overexposed', 2012, 1017);

-- Dua Lipa
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2071, 'Dua Lipa', 2017, 1018);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2072, 'Future Nostalgia', 2020, 1018);

-- Hozier
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2073, 'Hozier', 2014, 1019);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2074, 'Wasteland, Baby!', 2019, 1019);

-- Shawn Mendes
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2075, 'Handwritten', 2015, 1020);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2076, 'Illuminate', 2016, 1020);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2077, 'Shawn Mendes', 2018, 1020);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2078, 'Wonder', 2020, 1020);

-- Lil Nas X
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2079, '7', 2019, 1021);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2080, 'Montero', 2021, 1021);

-- Travis Scott
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2081, 'Rodeo', 2015, 1022);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2082, 'Birds in the Trap Sing McKnight', 2016, 1022);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2083, 'Astroworld', 2018, 1022);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2084, 'Utopia', 2023, 1022);

-- Nicki Minaj
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2085, 'Pink Friday', 2010, 1023);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2086, 'Pink Friday: Roman Reloaded', 2012, 1023);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2087, 'The Pinkprint', 2014, 1023);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2088, 'Queen', 2018, 1023);

-- Katy Perry
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2089, 'Katy Hudson', 2001, 1024);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2090, 'One of the Boys', 2008, 1024);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2091, 'Teenage Dream', 2010, 1024);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2092, 'Prism', 2013, 1024);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2093, 'Smile', 2020, 1024);

-- P!nk
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2094, 'Can''t Take Me Home', 2000, 1025);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2095, 'M!ssundaztood', 2001, 1025);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2096, 'Try This', 2003, 1025);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2097, 'I''m Not Dead', 2006, 1025);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2098, 'Funhouse', 2008, 1025);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2099, 'The Truth About Love', 2012, 1025);

-- Zayn Malik
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2100, 'Mind of Mine', 2016, 1026);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2101, 'Icarus Falls', 2018, 1026);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2102, 'Nobody Is Listening', 2021, 1026);

-- Tones and I
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2103, 'The Kids Are Coming', 2019, 1027);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2104, 'Welcome to the Madhouse', 2021, 1027);

-- BTS
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2105, '2 Cool 4 Skool', 2013, 1028);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2106, 'Wings', 2016, 1028);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2107, 'Love Yourself: Tear', 2018, 1028);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2108, 'Map of the Soul: Persona', 2019, 1028);
INSERT INTO albums(id, title, release_year, artist_id) VALUES (2109, 'BE', 2020, 1028);