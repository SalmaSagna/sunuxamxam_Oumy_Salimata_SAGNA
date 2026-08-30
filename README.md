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

1. Créer la base de données PostgreSQL avec pgAdmin (ou un autre client de votre choix) :
   ```sql
   CREATE DATABASE sunuxam;
   ```

2. Ouvrir le dossier `sunuxamxam/` dans IntelliJ IDEA et configurer `src/main/resources/application.properties` :
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/sunuxam
   spring.datasource.username=postgres
   spring.datasource.password=votre_mot_de_passe
   spring.jpa.hibernate.ddl-auto=update
   server.port=8081
   ```

3. Lancer l'application avec le bouton **Run** d'IntelliJ (sur la classe `SunuxamxamApplication`).

4. L'API est accessible sur `http://localhost:8081`. Les tables sont créées automatiquement au démarrage (`ddl-auto=update`).

## Installation du frontend

1. Se placer dans le dossier `sunuxam_frontend/` :
   ```bash
   npm install
   ng serve
   ```

2. L'application est accessible sur `http://localhost:4200`.

Le backend doit être démarré avant le frontend pour que les appels API fonctionnent.

## Créer un compte admin

Le cahier des charges impose que le compte gestionnaire (admin) soit créé directement en base, sans auto-inscription. Procédure :

1. Inscrire un compte normalement via le frontend (page Inscription) ou via Postman :
   ```
   POST http://localhost:8081/api/auth/register
   ```

2. Passer son rôle en `ADMIN` directement en base avec pgAdmin :
   ```sql
   UPDATE utilisateur SET role = 'ADMIN' WHERE email = 'votre-email@example.com';
   ```

3. Se reconnecter avec ce compte pour obtenir un token incluant le rôle `ADMIN`.

Les comptes candidats s'inscrivent normalement via le frontend, sans manipulation particulière.

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

## Démo vidéo

Une vidéo de démonstration du projet est disponible ici : [Voir la vidéo](https://drive.google.com/file/d/1nqa9UpwJEmBRzGsp2A0lZkYPWM-yJZMO/view?usp=sharing)

## Tests et CI/CD

Les tests backend se lancent depuis IntelliJ : clic droit sur le dossier `src/test/java` (ou sur une classe de test) → **Run 'All Tests'**.

Un workflow GitHub Actions (`.github/workflows/ci.yml`) exécute automatiquement ces mêmes tests ainsi que le build frontend à chaque push sur la branche `main`.