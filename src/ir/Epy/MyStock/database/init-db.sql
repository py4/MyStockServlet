DROP TABLE  transaction IF EXISTS;
DROP TABLE credit_request IF EXISTS ;
DROP TABLE stock_request IF EXISTS ;
DROP TABLE  stocks IF EXISTS ;
DROP TABLE customer IF EXISTS ;

CREATE TABLE customer (
  c_id INTEGER NOT NULL ,
  name VARCHAR(100) NOT NULL,
  family VARCHAR(100) NOT  NULL,
  deposit INTEGER DEFAULT 1000,
  is_admin BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (c_id)
);

CREATE TABLE stocks(
  symbol VARCHAR(100),
  PRIMARY KEY (symbol)
);

CREATE TABLE customer_stock_shares (
  symbol VARCHAR(100),
  c_id INTEGER NOT NULL ,
  quantity INTEGER,
  FOREIGN KEY (symbol) REFERENCES stocks,
  FOREIGN KEY (c_id) REFERENCES customer,
  PRIMARY KEY (c_id,symbol)
);

CREATE TABLE stock_request(
  s_rec_id INTEGER NOT NULL,
  quantity INTEGER,
  req_type VARCHAR(10),
  is_buy BOOLEAN,
  base_price INTEGER,
  c_id INTEGER,
  symbol VARCHAR(100),
  PRIMARY KEY (s_rec_id, symbol, c_id),
  FOREIGN KEY (c_id) REFERENCES customer,
  FOREIGN KEY (symbol) REFERENCES stocks
);

CREATE TABLE credit_request(
  c_req_id INTEGER NOT NULL ,
  status SMALLINT,
  quantity INTEGER,
  is_deposit BOOLEAN,
  c_id INTEGER ,
  PRIMARY KEY (c_req_id, c_id),
  FOREIGN KEY (c_id) REFERENCES customer
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


INSERT INTO customer (c_id, name, family, is_admin) VALUES ('1', 'admin', 'admin zadeh', 'true');
