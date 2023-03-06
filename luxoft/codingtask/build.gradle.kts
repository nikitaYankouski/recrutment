plugins {
	java
	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "com.hudman"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	testImplementation("org.springframework.boot:spring-boot-starter-test")


	implementation("org.slf4j:slf4j-api:2.0.6")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.14.2")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")


	compileOnly("org.projectlombok:lombok:1.18.24")
	annotationProcessor("org.projectlombok:lombok:1.18.24")

	runtimeOnly("org.postgresql:postgresql")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
