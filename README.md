# Player Service Architecture Diagram (Pro-Level)

```mermaid
flowchart LR
    %% Clients
    subgraph "Clients"
        APIClients[(API Consumers / Postman - Sends REST API requests - For testing & consumption)]:::external
    end

    %% API Test Collection
    subgraph "API Test Collection"
        GetAll["GetAllPlayers.http - Example GET request"]:::external
        GetBy["GetPlayerById.http - Example GET by ID"]:::external
        ChatReq["chat_requests.txt - Example POST chat requests"]:::external
    end

    %% Java Application
    subgraph "Player Service (Spring Boot Java App)"
        direction TB

        %% Main App
        AppEntry["PlayerServiceJavaApplication - Spring Boot main class - Starts the app"]:::java

        %% Configuration
        subgraph "Configuration Layer"
            Config["ChatClientConfiguration - Configures HTTP client for Ollama LLM"]:::java
        end

        %% Controllers
        subgraph "Controller Layer"
            PC["PlayerController - getAllPlayers() - getPlayerById(id)"]:::java
            CC["ChatController - generateChat(prompt)"]:::java
        end

        %% Services
        subgraph "Service Layer"
            PS["PlayerService - fetchAllPlayers() - fetchPlayerById(id)"]:::java
            CCS["ChatClientService - generateChat(prompt) - callOllamaLLM()"]:::java
        end

        %% Repository
        subgraph "Repository Layer"
            PR["PlayerRepository - findAll() - findById(id)"]:::java
        end

        %% Models
        subgraph "Model Layer"
            PlayerModel["Player.java - Entity mapping to Player table"]:::java
            PlayersModel["Players.java - DTO wrapper for multiple players"]:::java
        end

        %% Layer connections
        AppEntry --> PC
        AppEntry --> CC
        PC --> PS
        PS --> PR
        PS --> PlayerModel
        PS --> PlayersModel
        CC --> CCS
        Config --> CCS
        CCS --> Ollama
    end

    %% Resources
    subgraph "Resources"
        Schema["schema.sql - Initializes H2 DB schema"]:::db
        Yml["application.yml - Spring Boot config (DB, server, properties)"]:::java
    end

    %% Runtime Components
    H2["H2 Database - In-memory DB - Stores Player data"]:::db
    Ollama["Ollama LLM Container - AI Chat Engine - Port 11434"]:::external

    %% Python Model Trainer (Build-time)
    subgraph "Python Model Trainer"
        PM["player-service-model/ - Code & Training Scripts"]:::build
        ModelCode["model.py - AI Model definition"]:::build
        Notebook["train.ipynb - Train notebook"]:::build
        JobLib["team_model.joblib - Trained model artifact"]:::build
        DockerTrainer["Dockerfile - Build container for training"]:::build

        PM --> ModelCode
        PM --> Notebook
        ModelCode --> JobLib
        PM --> DockerTrainer
        JobLib -.-> Ollama
    end

    %% Client -> API
    APIClients --> GetAll
    APIClients --> GetBy
    APIClients --> ChatReq
    APIClients --> PC
    APIClients --> CC

    %% Repository -> DB
    PR --> H2
    Schema --> H2
    Yml --> AppEntry

    %% Styles
    classDef java fill:#AED6F1,stroke:#1F618D,color:#1F618D
    classDef db fill:#ABEBC6,stroke:#196F3D,color:#196F3D
    classDef external fill:#F9E79F,stroke:#B9770E,color:#B9770E
    classDef build fill:#D5D8DC,stroke:#566573,color:#566573

