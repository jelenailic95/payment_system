INSERT INTO bank (name, address, email, account_number,  phone_number,unique_bank_number)
VALUES ('Uni Credit', 'Bulevar oslobodjenja 5', 'unicredit@gmail.com', '1234567899', '021555444', '123');
INSERT INTO bank (name, address, email, account_number, phone_number,unique_bank_number)
VALUES ('Erste', 'Bulevar oslobodjenja 88', 'erste@gmail.com', '1255558887', '0215874965', '125');

INSERT INTO user (full_name, email)
VALUES ('Casopis1', 'casopis@gmail.com');

INSERT INTO account (ammount, card_holder_id, merchant_id, merchant_password) VALUES (5000, 1, "PoniLcvSpfCFawRwYiAXXdEeqndZKzzqwHUHqayhJKVlgUbMcYRDnUgboTUGrXWP",
"pgVIBbgQuRjgvvlP");

INSERT INTO bank_accounts (bank_id, accounts_id) VALUES (1, 1);