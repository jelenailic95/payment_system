INSERT INTO client (client_id, merchant_id, merchant_password) VALUES (1, 'PoniLcvSpfCFawRwYiAXXdEeqndZKzzqwHUHqayhJKVlgUbMcYRDnUgboTUGrXWP', 'pgVIBbgQuRjgvvlP');
INSERT INTO client (client_id, merchant_id, merchant_password) VALUES (2, 'merch2', 'pass2');

INSERT INTO payment_method (name) VALUES ('paypal');
INSERT INTO payment_method (name) VALUES ('bank');
INSERT INTO payment_method (name) VALUES ('crypto');

INSERT INTO client_payment_methods (client_id, payment_methods_id) VALUES (1,1);
INSERT INTO client_payment_methods (client_id, payment_methods_id) VALUES (1,2);
INSERT INTO client_payment_methods (client_id, payment_methods_id) VALUES (1,3);

INSERT INTO client_payment_methods (client_id, payment_methods_id) VALUES (2,1);
INSERT INTO client_payment_methods (client_id, payment_methods_id) VALUES (2,3);

INSERT INTO payment_request (amount, merchant_id, merchant_password, merchant_order_id, merchant_timestamp, success_url, error_url, faild_url)
 VALUES (200,1,'pass',1,'2018-1-1 23:59:59', 'successUrl','errorUrl','faildUrl');

