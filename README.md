# Java-sprinboot — Gestion d'Utilisateurs (Spring Boot 4.1.0)

> ⚠️ **Projet d'apprentissage** — Identifiants de démo (`admin/admin`) en dur, à ne jamais utiliser en production.

Application web Spring Boot permettant de gérer des utilisateurs (CRUD) avec authentification par session HTTP.

## Stack technique

| Technologie   | Version     | Rôle                                |
|---------------|-------------|--------------------------------------|
| Spring Boot   | 4.1.0       | Framework Java pour applications web |
| Java          | 17          | Langage de programmation             |
| Thymeleaf     | —           | Moteur de templates HTML côté serveur|
| Spring Data JDBC | —        | Accès BDD via JdbcTemplate           |
| PostgreSQL    | 16          | Base de données (Docker)             |
| Maven         | —           | Gestionnaire de dépendances et build |
| Apache Tomcat | 11          | Serveur web embarqué                 |

## Architecture MVC

```
Java-sprinboot/
├── pom.xml
├── src/
│   ├── main/java/com/example/javasprinboot/
│   │   ├── JavaSprinbootApplication.java    ← Point d'entrée
│   │   ├── model/User.java                 ← Modèle de données
│   │   ├── repository/UserRepository.java  ← Accès BDD (DAO)
│   │   └── controller/
│   │       ├── LoginController.java        ← Connexion / Déconnexion
│   │       └── UserController.java         ← CRUD utilisateurs
│   └── main/resources/
│       ├── application.properties          ← Configuration
│       ├── schema.sql                      ← Création de la table
│       ├── data.sql                        ← Données initiales (admin)
│       └── templates/
│           ├── login.html                  ← Page de connexion
│           ├── users.html                  ← Liste des utilisateurs
│           ├── add-user.html               ← Ajout d'un utilisateur
│           └── edit-user.html              ← Modification d'un utilisateur
└── target/                                 ← Build généré
```

## Flux d'une requête

```
Navigateur → HTTP Request → Contrôleur → Repository → PostgreSQL
                                 ↓
Navigateur ← HTTP Response ← Template Thymeleaf ← Données (Model)
```

## Fonctionnalités

- **Authentification** : connexion/déconnexion par session HTTP (pas de Spring Security)
- **CRUD complet** : Create, Read, Update, Delete sur la table `users`
- **Protection des routes** : chaque page restreinte vérifie la session active
- **Base de données** : PostgreSQL avec initialisation automatique (schema.sql + data.sql)
- **Templates Thymeleaf** : HTML dynamique rendu côté serveur

## Prérequis

- Java 17+
- Docker (pour PostgreSQL)
- Maven (ou utiliser le wrapper `mvnw`)

## Lancement

```bash
# 1. Démarrer PostgreSQL
docker start postgres-odoo

# 2. Lancer l'application
./mvnw spring-boot:run

# 3. Ouvrir le navigateur
http://localhost:8080/

# Login : admin / admin
```

## Routes

| URL                | Méthode | Protégée | Description                |
|--------------------|---------|----------|----------------------------|
| `/`                | GET     | Non      | Redirige vers /login       |
| `/login`           | GET     | Non      | Page de connexion          |
| `/login`           | POST    | Non      | Authentification           |
| `/users`           | GET     | Oui      | Liste des utilisateurs     |
| `/users/add`       | GET     | Oui      | Formulaire d'ajout         |
| `/users/add`       | POST    | Oui      | Ajouter un utilisateur     |
| `/users/edit/{id}` | GET     | Oui      | Formulaire de modification |
| `/users/edit/{id}` | POST    | Oui      | Modifier un utilisateur    |
| `/users/delete/{id}`| GET    | Oui      | Supprimer un utilisateur   |
| `/logout`          | GET     | Non      | Déconnexion                |
