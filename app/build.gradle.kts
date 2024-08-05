import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
	application
	jacoco
	id("io.freefair.lombok") version "8.6"
	id("com.github.johnrengelman.shadow") version "8.1.1"
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "hexlet.code"
version = "0.0.1-SNAPSHOT"

//java {
//	toolchain {
//		languageVersion = JavaLanguageVersion.of(21)
//	}
//}

application {
	mainClass.set("hexlet.code.app.AppApplication")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-devtools")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	implementation("net.datafaker:datafaker:2.0.1")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
	implementation("org.instancio:instancio-junit:3.3.0")
	runtimeOnly("com.h2database:h2:2.2.224")
	implementation("org.postgresql:postgresql:42.7.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	testImplementation(platform("org.junit:junit-bom:5.10.0"))
	testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
	testImplementation("net.javacrumbs.json-unit:json-unit-assertj:3.2.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
// Понадобится когда мы начнем работать с аутентификацией
//	testImplementation("org.springframework.security:spring-security-test")

}

tasks.test {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
	testLogging {
		displayGranularity = -1
		exceptionFormat = TestExceptionFormat.FULL
		events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
		showStackTraces = true
		showCauses = true
		showStandardStreams = true
	}
}

tasks.jacocoTestReport {
	reports {
		xml.required = true
		csv.required = false
		html.required = false
	}
}
