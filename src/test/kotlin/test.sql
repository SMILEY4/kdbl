SELECT *,
       book.*,
       book.title,
       publisher_id      AS publisher,
       orders.order_id,
       orders.order_date AS date,
       COUNT(*)          AS colCount
FROM book,
     cust_order AS orders,
     (SELECT book.title FROM book) AS titles,
     book
         JOIN cust_order AS orders ON (book.language_id) == (book_language.language_id)
WHERE ((publisher) == (42))
   OR ((orders.order_date) < (1990))
GROUP BY orders.shipping_method_id
HAVING ((colCount) - (2)) > (8)
ORDER BY book.title ASC, orders.shipping_method_id DESC
LIMIT 50
