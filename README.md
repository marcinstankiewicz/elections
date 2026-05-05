## 🗳️ Election Voting System

A backend application for managing elections, voters, options, and voting, built with Spring Boot, Spring Data JPA, and PostgreSQL.

The system allows creating elections, managing voters, adding options, and securely casting votes with validation rules (e.g., preventing duplicate voting or blocked users).

## 🏗️ Tech Stack
Java 17+,
Spring Boot,
Spring Web,
Spring Data JPA,
Hibernate,
PostgreSQL,
Maven,
JUnit 5,
Mockito

## 📌 Main API Endpoints
Create a new election.\
`POST /elections?name={name}`

Add an option to an election.\
`POST /elections/{id}/options?name={name}`

Register a new voter.\
`POST /voters?name={name}`

Block or unblock a voter.\
`POST /voters/{id}/block`\
`POST /voters/{id}/unblock`

Vote/
`POST /votes`\
With Request body:
`{
"voterId": 1,
"electionId": 2,
"optionId": 3
}`

## ⚖️ Business Rules
A voter cannot vote if they are blocked\
A voter can vote only once per election\
An option must belong to the selected election\
Invalid operations throw runtime exceptions

## Run app
`mvn spring-boot:run`

## Generate DB changes
Check file target/generated-diff.xml and adjust the changelog\
`mvn clean compile liquibase:diff`