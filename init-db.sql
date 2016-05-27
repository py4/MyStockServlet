-- noinspection SqlNoDataSourceInspectionForFile
DROP TABLE  transactions IF EXISTS;
DROP TABLE credit_requests IF EXISTS ;
DROP TABLE stock_requests IF EXISTS ;
DROP TABLE stock_shares IF EXISTS ;
DROP TABLE stocks IF EXISTS ;
DROP TABLE roles IF EXISTS ;
DROP TABLE config IF EXISTS ;
DROP TABLE customers IF EXISTS ;

CREATE TABLE customers (
  id VARCHAR(100) NOT NULL ,
  username VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  name VARCHAR(100) NOT NULL,
  family VARCHAR(100) NOT  NULL,
  deposit INTEGER DEFAULT 1000,
  PRIMARY KEY (id),
  UNIQUE (username)
);

CREATE TABLE roles (
  username varchar(100) not null,
  role_name varchar(100) not null,
	primary key (username, role_name),
	constraint users_username_fk FOREIGN KEY (username) REFERENCES customers(username)
);

CREATE TABLE stocks(
  symbol VARCHAR(100),
  status SMALLINT,
  owner_id varchar(100) not null,
  PRIMARY KEY (symbol),
  FOREIGN KEY (owner_id) REFERENCES customers
);

CREATE TABLE stock_shares (
  stock_symbol VARCHAR(100),
  customer_id VARCHAR(100) NOT NULL ,
  quantity INTEGER,
  FOREIGN KEY (stock_symbol) REFERENCES stocks,
  FOREIGN KEY (customer_id) REFERENCES customers,
  PRIMARY KEY (customer_id,stock_symbol)
);

CREATE TABLE stock_requests(
  id INTEGER NOT NULL,
  quantity INTEGER,
  is_buy BOOLEAN,
  type VARCHAR(10),
  base_price INTEGER,
  customer_id VARCHAR(100) NOT NULL,
  stock_symbol VARCHAR(100),
  status SMALLINT,
  PRIMARY KEY (id),
  FOREIGN KEY (customer_id) REFERENCES customers,
  FOREIGN KEY (stock_symbol) REFERENCES stocks
);



CREATE TABLE credit_requests(
  id INTEGER NOT NULL ,
  status SMALLINT,
  amount INTEGER,
  is_deposit BOOLEAN,
  customer_id VARCHAR(100) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (customer_id) REFERENCES customers
);

CREATE TABLE config (
  limit INTEGER NOT NULL,
  id INTEGER NOT NULL,
  PRIMARY  KEY(id)
);

INSERT INTO customers (id, username, password, name, family, deposit)
VALUES ('1','admin','admin','The ','One', 100000);

INSERT INTO roles(username, role_name)
VALUES ('admin', 'admin');

INSERT INTO customers (id, username, password, name, family, deposit)
VALUES ('2','user1','user1','name1','family1', 100000);

INSERT INTO roles(username, role_name)
VALUES ('user1', 'customer');

INSERT INTO customers (id, username, password, name, family, deposit)
VALUES ('3','user2','user2','name2','family2', 100000);

INSERT INTO roles(username, role_name)
VALUES ('user2', 'customer');

INSERT INTO customers (id, username, password, name, family, deposit)
VALUES ('4','owner1','owner1','owner1','owner1', 100000);

INSERT INTO roles(username, role_name)
VALUES ('owner1', 'owner');

INSERT INTO customers (id, username, password, name, family, deposit)
VALUES ('5','accountant1','accountant1','accountant1','accountant1', 100000);

INSERT INTO roles(username, role_name)
VALUES ('accountant1', 'accountant');


INSERT INTO config(id, limit)
 VALUES ('1', 1000000);



-- CREATE TABLE transaction (
--   t_id INTEGER NOT NULL ,
--   quantity INTEGER,
--   buyer_money INTEGER,
--   seller_money INTEGER,
--   req_type VARCHAR(10),
--   symbol VARCHAR(100),
--   buyer_id INTEGER ,
--   seller_id INTEGER,
--   PRIMARY KEY (t_id, buyer_id, seller_id, symbol),
--   FOREIGN KEY (buyer_id) REFERENCES customer,
--   FOREIGN KEY (seller_id) REFERENCES customer,
--   FOREIGN KEY (symbol) REFERENCES stocks
-- );
--
--
-- INSERT INTO customers (id, name, family) VALUES ('1', 'admin', 'admin zadeh');
