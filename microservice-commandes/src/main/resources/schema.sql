CREATE TABLE commande (
    id INT AUTO_INCREMENT PRIMARY KEY, 
    commande_payee BOOLEAN, 
    date_commande TIMESTAMP NOT NULL, 
    product_id INT NOT NULL, 
    quantite INT NOT NULL
);