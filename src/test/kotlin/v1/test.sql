SELECT cust_order.order_date
FROM cust_order;

SELECT cust_order.order_date
FROM cust_order
ORDER BY cust_order.order_date ASC;

SELECT cust_order.order_date, COUNT(*) AS count
FROM cust_order
GROUP BY cust_order.order_date
ORDER BY cust_order.order_date ASC;

SELECT *
FROM cust_order
WHERE (cust_order.order_id) == (3);

SELECT co.order_date                 AS order_day,
       COUNT(DISTINCT {co.order_id}) AS num_orders,
       COUNT(DISTINCT {ol.order_id}) AS num_books
FROM cust_order AS co
         INNER JOIN order_line AS ol ON (co.order_id) == (ol.order_id)
GROUP BY co.order_date
ORDER BY co.order_date ASC;
