# SunuXam

Application web de gestion de concours de recrutement, développée avec Spring Boot (backend) et Angular (frontend).

Réalisée dans le cadre de la validation de l'année de Licence 3 Génie Logiciel.

## Stack technique

- **Backend** : Spring Boot 4.1.1 (Java 25), Spring Security (JWT), Spring Data JPA
- **Frontend** : Angular
- **Base de données** : PostgreSQL
- **Documentation API** : Swagger / OpenAPI
- **CI/CD** : GitHub Actions

## Prérequis

- Java 25
- Node.js 22+
- PostgreSQL (une instance locale, avec une base nommée `sunuxam`)
- Angular CLI (`npm install -g @angular/cli`)

## Installation du backend

1. Créer la base de données PostgreSQL :
   ```sql
   CREATE DATABASE sunuxam;
   ```

2. Se placer dans le dossier `sunuxamxam/` et configurer `src/main/resources/application.properties` :
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/sunuxam
   spring.datasource.username=postgres
   spring.datasource.password=votre_mot_de_passe
   spring.jpa.hibernate.ddl-auto=update
   server.port=8081
   ```

3. Lancer l'application (via IntelliJ, bouton Run, ou en ligne de commande) :
   ```bash
   mvn spring-boot:run
   ```

4. L'API est accessible sur `http://localhost:8081`.

Au premier démarrage, un jeu de données de test est automatiquement créé (voir section [Comptes de test](#comptes-de-test)).

## Installation du frontend

1. Se placer dans le dossier `sunuxam_frontend/` :
   ```bash
   npm install
   ng serve
   ```

2. L'application est accessible sur `http://localhost:4200`.

Le backend doit être démarré avant le frontend pour que les appels API fonctionnent.

## Comptes de test

Un seeder crée automatiquement les comptes suivants au premier lancement du backend (base vide) :

| Rôle      | Email                     | Mot de passe   |
|-----------|---------------------------|----------------|
| Admin     | admin@sunuxam.sn          | admin123       |
| Candidat  | awa.diop@example.com      | candidat123    |

Deux concours et deux épreuves de test sont également créés.

## Documentation de l'API

Une fois le backend lancé, la documentation Swagger est disponible sur :
```
http://localhost:8081/swagger-ui.html
```

## Fonctionnalités principales

- Authentification par JWT (inscription/connexion candidat, compte admin créé en base)
- Consultation des concours ouverts et dépôt de candidature avec upload de documents (CV, photo, diplôme)
- Suivi du statut de candidature et consultation des résultats après publication
- Gestion des concours et des épreuves (admin)
- Gestion des candidatures : changement de statut, saisie des notes (admin)
- Publication des résultats, avec protection empêchant toute modification après publication
- Accès aux pages protégé par rôle (guards Angular + sécurité Spring Security côté backend)

## Tests et CI/CD

Les tests backend se lancent avec :
```bash
mvn test
```

Un workflow GitHub Actions (`.github/workflows/ci.yml`) exécute automatiquement les tests backend et le build frontend à chaque push sur la branche `main`.