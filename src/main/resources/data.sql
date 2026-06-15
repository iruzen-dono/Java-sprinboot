-- Admin par d�faut (login: admin, mot de passe: admin)
-- Utilis� pour la premi�re connexion
INSERT INTO users (login, password, name)
SELECT 'admin', 'admin', 'Administrateur'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE login = 'admin');
