SELECT b.book_id AS my_author_id
FROM book AS b
         INNER JOIN book_language AS lang ON (b.language_id = lang.language_id)