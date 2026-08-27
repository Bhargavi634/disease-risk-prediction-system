# Disease Risk Prediction System (minimal scaffold)

Overview:
- Java Spring Boot app (backend + HTML frontend via Thymeleaf)
- MySQL for user storage
- Python Flask microservice (simple scikit-learn models) for predictions
- Bootstrap + vanilla JS for UI
- Two questionnaires: Diabetes and Heart Attack — each returns low/medium/high risk and a suggestion to consult a doctor.

Requirements:
- Java 17+ and Maven
- Python 3.9+
- MySQL
- pip

Steps:
1. Start MySQL and create a database:
   - CREATE DATABASE disease_risk;
   - CREATE USER 'druser'@'localhost' IDENTIFIED BY 'drpass';
   - GRANT ALL PRIVILEGES ON disease_risk.* TO 'druser'@'localhost';
2. Configure `src/main/resources/application.properties` if your DB credentials differ.
3. Start Python ML microservice:
   - cd ml_service
   - python -m venv venv
   - source venv/bin/activate (or venv\Scripts\activate on Windows)
   - pip install -r requirements.txt
   - python ml_service.py
   - By default it listens on http://localhost:5000
4. Start Spring Boot:
   - mvn spring-boot:run
   - App runs on http://localhost:8080
5. Open the app:
   - Register a user, login, fill forms:
     - /diabetes
     - /heart
   - Results display risk (low/medium/high) and suggestion to consult a doctor.

Notes:
- This is a minimal demo. Passwords are stored in plain text for brevity — do NOT do this in production; use hashing.
- The Python models are synthetic; for production you would train on real datasets and persist models with joblib.
