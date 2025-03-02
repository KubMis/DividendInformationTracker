CREATE TABLE IF NOT EXISTS ticker
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    ticker TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS portfolio
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    portfolio_name TEXT NOT NULL,
    total_dividend_amount REAL,
    total_portfolio_value REAL
);

CREATE TABLE IF NOT EXISTS stock
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    ticker TEXT NOT NULL,
    name TEXT,
    quantity INTEGER,
    dividend_yield REAL,
    share_price REAL,
    portfolio_id INTEGER,
    FOREIGN KEY (portfolio_id) REFERENCES portfolio(id) ON DELETE CASCADE
);