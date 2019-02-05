INSERT INTO payment_method (method_name, method) VALUES ('paypal', 'paypal');
INSERT INTO payment_method (method_name, method) VALUES ('bank1', 'bank');
INSERT INTO payment_method (method_name, method) VALUES ('bank2', 'bank');
INSERT INTO payment_method (method_name, method) VALUES ('crypto', 'crypto');

--INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
-- ('Laguna', 'Laguna','$2a$10$2Jcq6I5x/TYxbX/VaQy2Au9', '$2a$10$7hj4ykizKokjPQ3b3tcK2OsW67BXxMDEcU7wi3nkG25C6lo6eappS', 2);

--INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
-- ('Laguna', 'Laguna','$2a$10$2Jcq6I5x/TYxbX/VaQy2Au9', '$2a$10$7hj4ykizKokjPQ3b3tcK2OsW67BXxMDEcU7wi3nkG25C6lo6eappS', 3);

-- INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
-- ('Laguna', 'Laguna','AXoO1kivoRi6_dU06wjmz2OJGGCQu9WSUfVVweds4st1lgbxFSCKe4Qpj6p_8JCxYbI4RWmykpTmXj0N',
--  'ENZPc9EBuTTXM2jGIXoaNUxykTMu6iJNItY-1gn6K6bZqUuk9TRtg_uhxR0bGnNs5CBRXvLmE6BnNVh7', 1);

-- INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
-- ('Laguna', 'Laguna','kcepNwZbZD4bQegiCmuwyAg4RKzrDPn1z1V4LkGy', null, 4);

INSERT INTO payment_request (amount, merchant_id, merchant_password, merchant_order_id, merchant_timestamp,
success_url, error_url, failed_url)  VALUES (200, '$2a$10$2Jcq6I5x/TYxbX/VaQy2Au9',
'$2a$10$7hj4ykizKokjPQ3b3tcK2OsW67BXxMDEcU7wi3nkG25C6lo6eappS', 1000000000, '2018-05-05 23:59:59',
'successUrl','errorUrl','failedUrl');
