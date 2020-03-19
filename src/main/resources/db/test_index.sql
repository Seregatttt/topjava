SET enable_seqscan TO off;

EXPLAIN (ANALYZE) SELECT * FROM users u  JOIN meals m  ON u.id=m.user_id;

SET enable_seqscan TO on;

ANALYZE users;

ANALYZE meals;