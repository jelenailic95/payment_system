INSERT INTO bank (name, address, email, account_number, phone_number,unique_bank_number)
VALUES ('Erste', 'Bulevar oslobodjenja 88', 'erste@gmail.com', '3405558887', '0215874965', '180376');

INSERT INTO general_sequence_number (payment_counter, acquirer_counter, issuer_counter) VALUES (1000000000, 1000000000,
1000000000);

-- journal 1

INSERT INTO user (full_name, email) VALUES ('Laguna', 'laguna@mailinator.com');

-- todo: dodati merch Id i pass
INSERT INTO account (amount, account_number, card_holder_id, merchant_id, merchant_password) VALUES
(5000, 'QTfwEGopKGhsyyWF', 1, '', '');

INSERT INTO bank_accounts (bank_id, accounts_id) VALUES (1, 1);

-- journal 2

INSERT INTO user (full_name, email) VALUES ('National Geography', 'national@mailinator.com');

-- todo: dodati merch Id i pass
INSERT INTO account (amount, account_number, card_holder_id, merchant_id, merchant_password) VALUES
(10000, 'lkjcHjYuoIosKlkJ', 1, '', '');

INSERT INTO bank_accounts (bank_id, accounts_id) VALUES (1, 1);


-- client and it's card

INSERT INTO user (full_name, email) VALUES ('Jelena Ilic', 'jelena95@hotmail.rs');

-- todo: dodati merch Id i pass
INSERT INTO account (amount, account_number, card_holder_id, merchant_id, merchant_password) VALUES
(10000, 'EWdgwTIGIrzEuYkb', 2, '',
'');

INSERT INTO bank_accounts (bank_id, accounts_id) VALUES (1, 2);

INSERT INTO card (pan, security_code, expiration_date, card_holder_name, account_id)
VALUES ('cGe+y54REOeuj8uicSdoew==q6Ypxlzr1wFn1bLXJhdP44BMor6mYmT/3BYNuRbPz0I=',
'n3jntj15MXKxkNT0MzmxQg==79F65uEvhE1cJxR+mW7Pog==', 'bWIdCav6AuZUFtb/pR/EYw==KY+N/6ZYSrLSKO6ZmHOAcQ==',
'newWxAALXoVcI0gH7CAFgA==qNms+coSZNYZJ5zkD27oeg==', 2);


-- transactions

INSERT INTO transaction (acquirer_order_id, acquirer_timestamp, amount, merchant_order_id, payment_id, status)
VALUES (null, null, 200, 1000000000, 1000000000, "PAID");
