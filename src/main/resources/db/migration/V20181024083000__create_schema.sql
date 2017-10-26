CREATE TABLE PRODUCTS
(
    ID            SERIAL NOT NULL,
    NAME          VARCHAR(128),
    DESCRIPTION   VARCHAR(1024),
    PRICE         NUMERIC(10,2),
    CATEGORY      VARCHAR(128),
    CONSTRAINT PRODUCT_PK PRIMARY KEY (ID)
);

CREATE TABLE USERS
(
    USERNAME              VARCHAR(32) NOT NULL,
    PASSWORD              VARCHAR(32) NOT NULL,
    CONSTRAINT USER_PK PRIMARY KEY (USERNAME)
);

INSERT INTO PRODUCTS (NAME, DESCRIPTION, PRICE, CATEGORY) VALUES ('Galaxy Note8', 'See the bigger picture and communicate in a whole new way. With the Galaxy Note8 in your hand, bigger things are just waiting to happen.', 22490000.00, 'SMART_PHONE');
INSERT INTO PRODUCTS (NAME, DESCRIPTION, PRICE, CATEGORY) VALUES ('iPhone X', 'iPhone X features a new all-screen design. Face ID, which makes your face your password. And the most powerful and smartest chip ever in a smartphone.', 29990000.00, 'SMART_PHONE');

INSERT INTO USERS (USERNAME, PASSWORD) VALUES ('admin', 'Admin@123');