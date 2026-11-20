## ⚙️ Setup Instructions
Aman Kumar

### Prerequisites
- Node.js & npm (for Angular)
- Java 21 & Maven (for Spring Boot)
- MySQL Server

### 1. Database Setup
Create a MySQL database named `wastedb`:
```sql
CREATE DATABASE wastedb;
```

### 2. Backend Setup
1. Navigate to the backend directory:
```bash
   cd waste-segregation-backend
```
2. Create a `.env.properties` file in this directory (same folder as `pom.xml`) with your own values:
```properties
   DB_PASSWORD=your-mysql-password
   AI_API_KEY=your-gemini-api-key
```
   Get a free Gemini API key at [aistudio.google.com/apikey](https://aistudio.google.com/apikey).
3. Run the Spring Boot application:
```bash
   ./mvnw spring-boot:run
```
   *(The backend runs on port 8080)*

### 3. Frontend Setup
1. Navigate to the frontend directory:
```bash
   cd waste-segregation-ui
```
2. Install dependencies:
```bash
   npm install
```
3. Start the Angular development server:
```bash
   ng serve
```
   *(The frontend runs on http://localhost:4200)*
