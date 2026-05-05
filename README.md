# Application for Elections

## Run app
`mvn spring-boot:run`

## Generate DB changes
`mvn clean compile liquibase:diff`

Check file target/generated-diff.xml and adjust the changelog
