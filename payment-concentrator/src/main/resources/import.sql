INSERT INTO payment_method (method_name, method) VALUES ('paypal', 'paypal');
INSERT INTO payment_method (method_name, method) VALUES ('bank1', 'bank');
INSERT INTO payment_method (method_name, method) VALUES ('bank2', 'bank');
INSERT INTO payment_method (method_name, method) VALUES ('crypto', 'crypto');

-- company 1

INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company1', 'Laguna','o86NEznTFvhD8Cg8HHtE7g==VAw0URM7zALP1ELZDkayJQFaNfTOxFkOpnW2xs67moPh329qoOBp3Dh35PEUONN5rWkuYT3je2FQxWab9xeen2vpnjws/MMgtVrpeVSHVbSGsmXeUM4aeedJujPFt/1C',
  'h6WtzIOpmJQa2lNvGkKAIw==b7iY26KDuDa6+t4do0x1bmZK3QXCdZW98xD8x6QB277CHQuZgUnZmvGK4TWZ0dqKVHFtGQinjPSTPXbZcjMrmcQlgqMVd00hHpir4exwdySWJNpGrnp2MlZxbaqd6cWD',
  1);

INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company1', 'Laguna','lNz6XtZ55SHl8x/fdBbXGA==PNSjtZ1jeE2ITTd3qSaI8a9Db/e55esmL9HqmX9RI6Q=',
 'oRTJA+fA7lNpxwV51xPllA==+I0y5DS0nNupgKtpWM3+Mt4JDYyGo0jwSaUAszSREUQKVrD3s6WktyJglzzoTYPNH5Ob0V9lD09YlP33raBHTQ==', 2);

INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company1', 'Laguna','Y7jo2mqW3AU4PYit56NjKw==bzLFQWnDG5vdGqo2B5JqDxi3J9WbbD9bIY6/ID6u4uizWe6D5qBSheUDH76tTHUw', null, 4);

-- company 2

INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company1', 'eLife','I5Q9TgFr4yGSCud7g2cZGA==bjZOWDXTUG2wV2kS1NvkL/OhBkNgQUIwlKPhVPbgKjuWFuZggV2AeHSc3Rjx6P5yifIT7TNEZ/z6m3K0zb96CwTMJGGp/Q9sqtxsxIZUXWT2c8eCHO2k99lf7B4SW0Pr',
  'S3Z9tXZj70Cgjs4DssEfUA==AZHnYqOsGAHpZdr58B9O8+Lifk6/F72uMu56jTigjIZfw6Prw/sNuIwFN5XWwk0mBPk/Qi9RE6G/DvopFRNt7aWB4QPQaLNEQO7L5xwCaaGealCsn2G2V1wMSvftqEnA',
  1);

INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company1', 'eLife', 'lh+Ehc3xb6J5e619Rsg2Ng==MkutNFDGocA997Ug9WyXgpH42VThM1FT5Zqpz6+gUHU=',
  'niFUIpOpLcBOUMLjo7yObA==6O0vjzQaoLVDw/uHmshGvFVfOMbzx2reroBukJ7hjGd34LslacrruCLMhhkoZ7XTTr+nQfrZ3w41rnEB0V4jtA==', 2);


INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company1', 'eLife','FTqx0obT5AU6OGns0VkH4w==q7x8Y9fy1KKPti0dbVhBUVD4ocK/iJrC9sKac9ca93ilj8dlvq186pfAZ6emkGUw', null, 4);


-- company 3

 INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company2', 'Science','YIlk8UChsXk3ynI7RCks9A==l/9KZTCwkr6L9ZQnFK+qLX1/6Z/j8TARAhTLzH1xz9oH0PBPMF8z8aH8ZQ8bikqxifQY7y7aGNB80xBQ+Ue3odYP6CJq/eqQSqKoN+SJQmxErRpAeFmM7yYmwWqlF4Rl',
 '7FbXxeJ/VgSDfF3EulLRrQ==eD4v54plU/o3bzNj4GHnKg8ithFUyyZLJTP+b4oKZGaD6jsugRfJci/kFbPY5ZDt0r5ibfuO72n2T5ueDQ/SgwrDz4VEPBNwjcs/NXBhvkGyblqtEiCq2zBifraqZe2t',
 1);

INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company2', 'Science', 'BlITocLkykNyo/8PJPsSaw==/yrlXStGxmmxDbNu6ZpdX1jsuGL5j0lIRj5OoyCuim8=',
 'jAaPbkOT3vf13DigLzBuRg==2zeazVAaZKRFqiJxlpWnt86ojCeZ7+Z4Z9ZKQ3taCWuRCXBZA6YmTiTz8m2JZQP6Tga27921ZEAombY7y9IPOg==', 2);

 INSERT INTO client (client,journal, client_id, client_password, payment_method_id) VALUES
 ('Company2', 'Science','TcQG510Pdq6IEqwGIjiKvw==RNuYmdwFnOxxCd3z7Cw/UYzRrxWfJGi2N9AuJUC7gP+T4kCwYoqQddipoeYsqTeb', null, 4);


INSERT INTO payment_request (amount, merchant_id, merchant_password, merchant_order_id, merchant_timestamp,
success_url, error_url, failed_url)  VALUES (200, '$2a$10$2Jcq6I5x/TYxbX/VaQy2Au9',
'$2a$10$7hj4ykizKokjPQ3b3tcK2OsW67BXxMDEcU7wi3nkG25C6lo6eappS', 1000000000, '2018-05-05 23:59:59',
'successUrl','errorUrl','failedUrl');
