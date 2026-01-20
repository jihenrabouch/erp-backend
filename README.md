 ERP Backend

[![Java](https://img.shields.io/badge/Java-21-blue)](https://www.java.com/) 
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-green)](https://spring.io/projects/spring-boot) 
[![License](https://img.shields.io/badge/License-MIT-lightgrey)](LICENSE)

Backend de l’ERP pour gérer **projets, tâches, équipes** avec JWT et Swagger.

---

 🚀 Technologies

- Java 21, Spring Boot 3.2  
- Spring Security + JWT  
- Spring Data JPA (MySQL)  
- Swagger UI pour tester les API  
- Maven

---

 ⚙️ Installation

1. Cloner le dépôt :

```bash
git clone https://github.com/jihenrabouch/erp-backend.git
cd erp-backend
Construire et lancer le backend :

mvn clean install
mvn spring-boot:run


Configurer la base de données dans src/main/resources/application.properties.

🧪 Tester les API

Swagger UI est disponible sur :

http://localhost:8081/swagger-ui.html

🔐 Authentification

Endpoints sécurisés avec JWT

Ajouter le token dans l’header :

Authorization: Bearer <token>
