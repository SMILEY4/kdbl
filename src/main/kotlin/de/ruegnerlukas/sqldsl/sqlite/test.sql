SELECT *,
       book.*,
       cust_order.order_id   AS order_id,
       cust_order.order_date AS date
FROM book AS book,
     cust_order AS orders,
     (SELECT book.title AS title FROM book AS book) AS titles,
     book AS b
         JOIN book_language AS l ON book.language_id == book_language.language_id
