plugins {
	java
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
}

group = "at.aau.ase"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("io.springfox:springfox-boot-starter:3.0.0")
	implementation("org.projectlombok:lombok:1.18.28")
	implementation("org.projectlombok:lombok:1.18.28")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation(kotlin("script-runtime"))

}

tasks.withType<Test> {
	useJUnitPlatform()
}
