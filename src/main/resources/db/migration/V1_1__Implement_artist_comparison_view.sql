-- Implementing artist comparison view
CREATE VIEW artist_comparison_view AS
SELECT
    a.id AS artist_id,
    a.name AS artist_name,
    COUNT(al.id) AS number_of_albums,
    MIN(al.release_year) AS first_release_year,
    MAX(al.release_year) AS last_release_year,
    (MAX(al.release_year) - MIN(al.release_year) + 1) AS active_years,
    a.genre AS genre
FROM
    artists a
        LEFT JOIN
    albums al ON a.id = al.artist_id
GROUP BY
    a.id;


