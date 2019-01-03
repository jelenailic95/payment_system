INSERT INTO bank (name, address, email, account_number,  phone_number,unique_bank_number)
VALUES ('Intesa', 'Bulevar oslobodjenja 100', 'intesa@gmail.com', '1704567899', '021555444', '170');
INSERT INTO bank (name, address, email, account_number, phone_number,unique_bank_number)
VALUES ('Vojvodjanska', 'Bulevar oslobodjenja 1', 'vojvodjanskabanka@gmail.com', '3405558887', '0215874965', '340');

INSERT INTO user (full_name, email)
VALUES ('Science Mag', 'sciencemag@gmail.com');

INSERT INTO user (full_name, email)
VALUES ('Marija Kovacevic', 'marija@gmail.com');

INSERT INTO account (amount, card_holder_id, merchant_id, merchant_password) VALUES (5000, 1, 'PoniLcvSpfCFawRwYiAXXdEeqndZKzzqwHUHqayhJKVlgUbMcYRDnUgboTUGrXWP',
'pgVIBbgQuRjgvvlP');

INSERT INTO bank_accounts (bank_id, accounts_id) VALUES (1, 1);

INSERT INTO card (pan, security_code, expiration_date, card_holder_name, account_id)
VALUES ('qceB+BHMWyqtrOWIyzpPQuL3S8ukuSbAOTMGpgJ2MC4=', 'fkbbG5Zuf+F9cBj5T6SsQg==', 'kM5UYGEUAta8EcXEnRzV6Q==',
 '5pEgKW4LWCMdUwUYJrb3HrzJtsvkOFHg8Vfj58I326M=', 1);
