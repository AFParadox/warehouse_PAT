CREATE TABLE IF NOT EXISTS prodotti (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('MAGLIA', 'PANTALONE', 'SCARPE')),
    descrizione TEXT NOT NULL,
    prezzo DECIMAL(10, 2) NOT NULL CHECK (prezzo >= 0),
    quantita_disponibile INTEGER NOT NULL CHECK (quantita_disponibile >= 0),
    quantita_minima INTEGER NOT NULL CHECK (quantita_minima >= 0),
    taglia VARCHAR(5),
    numero INTEGER
);
