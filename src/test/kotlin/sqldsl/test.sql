-- Expected
SELECT (MAX(a.total_sale))                         AS max_sale,
       (MIN(a.total_sale))                         AS min_sale,
       ((MAX(a.total_sale)) - (MIN(a.total_sale))) AS sale_difference
FROM ((SELECT sales.company_id,
              (SUM((sales.qtr1_sale) + (sales.qtr2_sale) + (sales.qtr3_sale) + (sales.qtr4_sale))) AS total_sale
       FROM sales
       GROUP BY sales.company_id)) AS a


-- actual
SELECT MAX(max_sale), MIN(min_sale), MAX(sale_difference)
FROM ((SELECT sales.company_id,
              (SUM((sales.qtr1_sale) + (sales.qtr2_sale) + (sales.qtr3_sale) + (sales.qtr4_sale))) AS total_sale
       FROM sales
       GROUP BY sales.company_id)) AS a