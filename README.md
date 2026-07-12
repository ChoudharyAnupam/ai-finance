# AI-Project: Enterprise Compliance Auditing System

This project implements a distributed, AI-driven auditing system designed to automate financial compliance checks. It follows an **AI-native architecture** using Java 21 and Spring Boot 4.1.0 to bridge the gap between core backend engineering and secure AI orchestration.

## Architecture Overview
The system is divided into two primary microservices to ensure a clean separation of concerns and maximum data isolation:

*   **AI Microservice (`ai`)**:
    - Acts as the intelligence layer using **Spring AI** and **AWS Bedrock**.
    - Implements a **RAG (Retrieval-Augmented Generation)** pipeline.
    - Utilizes a local **PostgreSQL `pgvector`** store for semantic matching of UAE regulatory laws.
*   **Core Microservice (`core`)**:
    - Serves as the operational data holder.
    - Manages raw transactional records and serves them via REST endpoints.

### Data Flow
1. User triggers an audit via the AI Microservice.
2. AI MS calls the Core MS via `WebClient` to fetch raw transaction data.
3. AI MS performs a similarity search in `pgvector` to find matching UAE Central Bank/DFSA rules.
4. AI MS bundles the raw data and legal context to generate a structured audit report via **Anthropic Claude 3.5**.

---

## Tech Stack
- **Framework**: Spring Boot 4.1.0 (utilizing Lazy JDBC connection fetching)
- **Language**: Java 21
- **AI/LLM**: AWS Bedrock (Anthropic Claude 3.5 Sonnet, Amazon Titan V2 Embeddings)
- **Database**: PostgreSQL with `pgvector` (running in Docker)
- **Infrastructure**: Docker, Docker Compose

---

## Project Structure
```text
├── core-microservice/     # Port 8081: Operational DB and Transaction Management
├── ai-microservice/       # Port 8082: AI Orchestration and RAG Pipeline
└── docker-compose.yml     # Infrastructure (PostgreSQL + pgAdmin)
```

---

## Getting Started

### Prerequisites
- JDK 21
- Docker and Docker Compose
- AWS Access Keys with Bedrock model access

### Installation & Setup
1. **Infrastructure**: Start the database containers.
   ```bash
   docker-compose up -d
   ```
2. **Configuration**: Set your AWS credentials as environment variables.
   ```bash
   export AWS_ACCESS_KEY_ID="your_key"
   export AWS_SECRET_ACCESS_KEY="your_secret"
   ```
3. **Run Services**:
   - Navigate to `core/` and run `./mvnw spring-boot:run` (Starts on port 8081).
   - Navigate to `ai/` and run `./mvnw spring-boot:run` (Starts on port 8080).

---

## Usage & API Endpoints
To trigger a compliance audit for a specific transaction:

**Request:**
```http
GET http://localhost:8080/api/ai/audit/{transaction_id}
```

**Example Output:**
The system returns a structured **UAE Regulatory Audit Report** including Risk Rating, UAE Central Bank Violation status, and required compliance actions.

---

## Contributing
Contributions for improving regional rule sets or adding more smart tool-calling functions are welcome. Please ensure new features follow the established two-microservice communication pattern.
