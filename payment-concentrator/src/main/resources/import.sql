INSERT INTO payment_method (method_name, method) VALUES ('paypal', 'paypal');
INSERT INTO payment_method (method_name, method) VALUES ('bank1', 'bank');
INSERT INTO payment_method (method_name, method) VALUES ('bank2', 'bank');
INSERT INTO payment_method (method_name, method) VALUES ('crypto', 'crypto');

-- company 1

INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company1', 'Laguna','AXoO1kivoRi6_dU06wjmz2OJGGCQu9WSUfVVweds4st1lgbxFSCKe4Qpj6p_8JCxYbI4RWmykpTmXj0N',
  'ENZPc9EBuTTXM2jGIXoaNUxykTMu6iJNItY-1gn6K6bZqUuk9TRtg_uhxR0bGnNs5CBRXvLmE6BnNVh7',
  1);

INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company1', 'Laguna','lNz6XtZ55SHl8x/fdBbXGA==PNSjtZ1jeE2ITTd3qSaI8a9Db/e55esmL9HqmX9RI6Q=',
 'oRTJA+fA7lNpxwV51xPllA==+I0y5DS0nNupgKtpWM3+Mt4JDYyGo0jwSaUAszSREUQKVrD3s6WktyJglzzoTYPNH5Ob0V9lD09YlP33raBHTQ==', 2);

INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company1', 'Laguna','Y7jo2mqW3AU4PYit56NjKw==bzLFQWnDG5vdGqo2B5JqDxi3J9WbbD9bIY6/ID6u4uizWe6D5qBSheUDH76tTHUw', null, 4);

-- company 2


INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company1', 'eLife','AbDBRgpt51kWyAJRRqcIbr-2_LtkRM-1UkReJVr_4ZmCIrQ-lVDUTNLGwM4QiR_hep_cB280EGjub1TJ',
  'EFBL8TXuELjUwRSyveeTfnuOOrVkWGuJiwSmarEF9SO2l5bJSULX2cD7bqM-Y5sSUEfE2GTMtLsKzegP',
  1);

INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company1', 'eLife', 'lh+Ehc3xb6J5e619Rsg2Ng==MkutNFDGocA997Ug9WyXgpH42VThM1FT5Zqpz6+gUHU=',
  'niFUIpOpLcBOUMLjo7yObA==6O0vjzQaoLVDw/uHmshGvFVfOMbzx2reroBukJ7hjGd34LslacrruCLMhhkoZ7XTTr+nQfrZ3w41rnEB0V4jtA==', 2);


INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company1', 'eLife','FTqx0obT5AU6OGns0VkH4w==q7x8Y9fy1KKPti0dbVhBUVD4ocK/iJrC9sKac9ca93ilj8dlvq186pfAZ6emkGUw', null, 4);


-- company 3

 INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company2', 'Science','AchqbciXvEdfMdyOIfhOLHyiFn2YYyfciAiXmrz5eHjgdNmSFUkEHYnSV13BIsHwvaseRRiPc9VQFfWv',
 'ECieBEm8xwYN5xYViwYv1CARX9zbqENGYNmAj2vrnclJP5IRn0dw1_Mmu93m1OoST5nKcZYdNPqB1nuY', 1);

INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company2', 'Science', 'BlITocLkykNyo/8PJPsSaw==/yrlXStGxmmxDbNu6ZpdX1jsuGL5j0lIRj5OoyCuim8=',
 'jAaPbkOT3vf13DigLzBuRg==2zeazVAaZKRFqiJxlpWnt86ojCeZ7+Z4Z9ZKQ3taCWuRCXBZA6YmTiTz8m2JZQP6Tga27921ZEAombY7y9IPOg==', 2);

 INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company2', 'Science','TcQG510Pdq6IEqwGIjiKvw==RNuYmdwFnOxxCd3z7Cw/UYzRrxWfJGi2N9AuJUC7gP+T4kCwYoqQddipoeYsqTeb', null, 4);


INSERT INTO payment_request (amount, merchant_id, merchant_password, merchant_order_id, merchant_timestamp,
success_url, error_url, failed_url)  VALUES (200, '$2a$10$2Jcq6I5x/TYxbX/VaQy2Au9',
'$2a$10$7hj4ykizKokjPQ3b3tcK2OsW67BXxMDEcU7wi3nkG25C6lo6eappS', 1000000001, '2018-05-05 23:59:59',
'successUrl','errorUrl','failedUrl');
