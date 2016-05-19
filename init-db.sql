DROP TABLE  transactions IF EXISTS;
DROP TABLE credit_requests IF EXISTS ;
DROP TABLE stock_requests IF EXISTS ;
DROP TABLE  stocks IF EXISTS ;
DROP TABLE customers IF EXISTS ;

CREATE TABLE customers (
  id VARCHAR(100) NOT NULL ,
  name VARCHAR(100) NOT NULL,
  family VARCHAR(100) NOT  NULL,
  deposit INTEGER DEFAULT 1000,
  PRIMARY KEY (id)
);

CREATE TABLE stocks(
  symbol VARCHAR(100),
  PRIMARY KEY (symbol)
);

CREATE TABLE customer_stock_shares (
  stock_symbol VARCHAR(100),
  customer_id INTEGER NOT NULL ,
  quantity INTEGER,
  FOREIGN KEY (stock_symbol) REFERENCES stocks,
  FOREIGN KEY (customer_id) REFERENCES customers,
  PRIMARY KEY (customer_id,symbol)
);

CREATE TABLE stock_request(
  id INTEGER NOT NULL,
  quantity INTEGER,
  is_buy BOOLEAN,
  base_price INTEGER,
  customer_id INTEGER,
  stock_symbol VARCHAR(100),
  PRIMARY KEY (id),
  FOREIGN KEY (customer_id) REFERENCES customers,
  FOREIGN KEY (stock_symbol) REFERENCES stocks
);



CREATE TABLE credit_request(
  id INTEGER NOT NULL ,
  status SMALLINT,
  amount INTEGER,
  customer_id INTEGER ,
  PRIMARY KEY (id),
  FOREIGN KEY (customer_id) REFERENCES customers
);

CREATE TABLE transaction (
  t_id INTEGER NOT NULL ,
  quantity INTEGER,
  buyer_money INTEGER,
  seller_money INTEGER,
  req_type VARCHAR(10),
  symbol VARCHAR(100),
  buyer_id INTEGER ,
  seller_id INTEGER,
  PRIMARY KEY (t_id, buyer_id, seller_id, symbol),
  FOREIGN KEY (buyer_id) REFERENCES customer,
  FOREIGN KEY (seller_id) REFERENCES customer,
  FOREIGN KEY (symbol) REFERENCES stocks
);


INSERT INTO customers (id, name, family) VALUES ('1', 'admin', 'admin zadeh');
