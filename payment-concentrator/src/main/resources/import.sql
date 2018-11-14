INSERT INTO payment_client (client_id) VALUES (1);
INSERT INTO payment_client (client_id) VALUES (2);

INSERT INTO payment_method (name) VALUES ('paypal');
INSERT INTO payment_method (name) VALUES ('bank');
INSERT INTO payment_method (name) VALUES ('crypto');

INSERT INTO payment_client_payment_methods (payment_client_id, payment_methods_id) VALUES (1,1);
INSERT INTO payment_client_payment_methods (payment_client_id, payment_methods_id) VALUES (1,2);
INSERT INTO payment_client_payment_methods (payment_client_id, payment_methods_id) VALUES (1,3);

INSERT INTO payment_client_payment_methods (payment_client_id, payment_methods_id) VALUES (2,1);
INSERT INTO payment_client_payment_methods (payment_client_id, payment_methods_id) VALUES (2,3);

