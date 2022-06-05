--QUERY 1:
SELECT orders.ord_no,
       orders.purch_amt,
       (((100) * (orders.purch_amt)) / (6000))            AS archived_perc,
       (((100) * ((6000) - (orders.purch_amt))) / (6000)) AS unarchived_perc
FROM orders
WHERE (((100) * (orders.purch_amt)) / (6000)) > (50);

--QUERY 2:
SELECT SUM(orders.purch_amt)
FROM orders;

--QUERY 3:
SELECT orders.salesman_id, MAX(orders.purch_amt)
FROM orders
WHERE (orders.ord_date) = ('2012-08-17')
GROUP BY orders.salesman_id;

--QUERY 4:
SELECT orders.customer_id, orders.ord_date, MAX(orders.purch_amt)
FROM orders
GROUP BY orders.customer_id, orders.ord_date
HAVING (MAX(orders.purch_amt)) > (2000);

--QUERY 5:
SELECT (COUNT(*)) AS num_products
FROM item_mast
WHERE (item_mast.pro_price) >= (350);

--QUERY 6:
SELECT (AVG(item_mast.pro_price)) AS avg_price, (item_mast.pro_com) AS company_id
FROM item_mast
GROUP BY item_mast.pro_com;
