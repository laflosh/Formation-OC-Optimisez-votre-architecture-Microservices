CREATE TABLE paiement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_commande INT UNIQUE,
    montant INT,
    numero_carte BIGINT
);