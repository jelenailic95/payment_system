INSERT INTO bank (name, address, email, account_number,  phone_number,unique_bank_number)
VALUES ('UniCredit', 'Bulevar oslobodjenja 5', 'unicredit@gmail.com', '1704567899', '021555444', '170');
INSERT INTO bank (name, address, email, account_number, phone_number,unique_bank_number)
VALUES ('Erste', 'Bulevar oslobodjenja 88', 'erste@gmail.com', '3405558887', '0215874965', '340');

INSERT INTO user (full_name, email)
VALUES ('Laguna', 'laguna@gmail.com');

INSERT INTO user (full_name, email)
VALUES ('Jelena Ilic', 'jelena@gmail.com');

INSERT INTO account (amount, card_holder_id, merchant_id, merchant_password) VALUES (5000, 1, 'PoniLcvSpfCFawRwYiAXXdEeqndZKzzqwHUHqayhJKVlgUbMcYRDnUgboTUGrXWP',
'pgVIBbgQuRjgvvlP');

INSERT INTO bank_accounts (bank_id, accounts_id) VALUES (1, 1);

INSERT INTO card (pan, security_code, expiration_date, card_holder_name, account_id)
VALUES ('1234567891234567', '1234', '2018-12-15', 'Jelena Ilic', 1);


