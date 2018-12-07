INSERT INTO payment_method (name) VALUES ('paypal');
INSERT INTO payment_method (name) VALUES ('bank1');
INSERT INTO payment_method (name) VALUES ('bank2');
INSERT INTO payment_method (name) VALUES ('crypto');

INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Laguna', 'Laguna','PoniLcvSpfCFawRwYiAXXdEeqndZKzzqwHUHqayhJKVlgUbMcYRDnUgboTUGrXWP', 'pgVIBbgQuRjgvvlP', 2);
INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Laguna', 'Laguna','PoniLcvSpfCFawRwYiAXXdEeqndZKzzqwHUHqayhJKVlgUbMcYRDnUgboTUGrXWP', 'pgVIBbgQuRjgvvlP', 3);
 INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Laguna', 'Laguna','davidvuletas@gmail.com', null, 1);

INSERT INTO payment_request (amount, merchant_id, merchant_password, merchant_order_id, merchant_timestamp, success_url, error_url, failed_url)
 VALUES (200,1,'pass',1,'2018-1-1 23:59:59', 'successUrl','errorUrl','failedUrl');

