# 💼 SmartHire API

Backend de la plateforme de recrutement SmartHire développé avec Spring Boot.

## 🚀 Présentation

SmartHire est une plateforme de recrutement permettant à une entreprise de gérer l'ensemble de son processus de recrutement.

Les candidats peuvent :

* Créer un compte
* Se connecter avec email/mot de passe
* Se connecter avec Google OAuth2
* Compléter leur profil
* Déposer leur CV
* Consulter les offres d'emploi
* Postuler aux offres

Les administrateurs peuvent :

* Gérer les offres d'emploi
* Consulter les candidats
* Consulter les CV
* Gérer les candidatures
* Modifier le statut des candidatures

---

## 🌐 Démo & Liens

- 🔗 Frontend : https://github.com/ton-user/smarthire.client  
- 🔗 Backend : https://github.com/ton-user/smartHire.api  
- 📘 Swagger API : http://localhost:8080/swagger-ui/index.html 

## 🛠 Technologies

* Java 21
* Spring Boot 3
* Spring Security
* JWT Authentication
* OAuth2 Google Login
* Spring Data JPA
* PostgreSQL
* Lombok
* MapStruct
* OpenAPI / Swagger
* Maven
* JUnit 5
* Mockito

---

## 📂 Architecture

```plaintext
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

Architecture basée sur :

* DTO
* Mapper
* Service Layer
* Repository Pattern

---

## 🔐 Sécurité

* Authentification JWT
* Autorisation basée sur les rôles
* OAuth2 Google Login
* Spring Security

Rôles disponibles :

```java
ADMIN
CANDIDAT
```

---

## 📄 Documentation API

Swagger :

```plaintext
http://localhost:8080/swagger-ui/index.html
```

---

## ⚙️ Installation

### Cloner le projet

```bash
git clone https://github.com/VOTRE_USERNAME/smarthire-backend.git
```

### Configurer PostgreSQL

Créer une base :

```sql
CREATE DATABASE smarthire;
```

Configurer :

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/smarthire
    username: postgres
    password: votre_password
```

### Lancer le projet

```bash
mvn spring-boot:run
```

---

## 🧪 Tests

```bash
mvn test
```

Tests réalisés avec :

* JUnit 5
* Mockito

---

## 👨‍💻 Auteur

Amiri Soilihi

Développeur Full Stack Java / Angular
