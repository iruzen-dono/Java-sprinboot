-- Admin par défaut (login: admin, mot de passe: admin)
-- ⚠️ DÉMO UNIQUEMENT — Ne pas utiliser en production
-- Utilisé pour la première connexion
INSERT INTO users (login, password, name)
SELECT 'admin', 'admin', 'Administrateur'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE login = 'admin');
