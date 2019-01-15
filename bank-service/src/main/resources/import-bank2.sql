INSERT INTO bank (name, address, email, account_number,  phone_number,unique_bank_number)
VALUES ('UniCredit', 'Bulevar oslobodjenja 5', 'unicredit@gmail.com', '1704567899', '021555444', '181245');

INSERT INTO general_sequence_number (payment_counter, acquirer_counter, issuer_counter) VALUES (1000000000, 1000000000,
1000000000);

INSERT INTO user (full_name, email)
VALUES ('Science Mag', 'sciencemag@mailinator.com');

INSERT INTO account (amount, account_number, card_holder_id, merchant_id, merchant_password) VALUES
(3200000, 'mFJWKwtIzLUDHhtU', 1, '$2a$10$PTC873N7twSq.I8cyPplLuj',
'$2a$10$m2QfQaRrmJrxhxzVT3QxTuGOk4MO/JqRsW8qwo09izliZUm2DGYki');

INSERT INTO bank_accounts (bank_id, accounts_id) VALUES (1, 1);

INSERT INTO user (full_name, email)
VALUES ('Marija Kovacevic', 'koralina24@hotmail.com');

INSERT INTO account (amount, account_number, card_holder_id, merchant_id, merchant_password) VALUES
(50000, 'mqXAGNPmaJhWStWl', 2, '$2a$10$N9HE2A.Yt7XH3amGKx0k6e0',
'$2a$10$t.cw/4lgLqaIWqVeJ24Y7.Is477rs4v4tHYZQwHdFRWzR1OOu0V8m');

INSERT INTO bank_accounts (bank_id, accounts_id) VALUES (1, 2);

INSERT INTO card (pan, security_code, expiration_date, card_holder_name, account_id)
VALUES ('puJrZnCa0IYgO1Xx/d8OxQ==5joTtnZmKQwedevaVLzyvBE68p99Ryj78wHJQUeRptM=',
'XuLeY1WoCm1CJRgCsiYolA==kFX7jIJ0u//yc7LcLNx18g==', '4LL72B+8JDZUmkDNdDdZZw==LGJQbPRGEILYqa6htpXZ+g==',
'nGSXlhuoGf3zeZgWv1r51A==OmZgIauKH7ROLrVhPZVgpIEXI+CYWPbMS2r0MxNv1Y8=', 2);