import nu.studer.gradle.jooq.JooqGenerate

plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"

    id("nu.studer.jooq") version "8.0"
    id("org.flywaydb.flyway") version "9.8.2"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Databases
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j:8.0.31")

    // Helpers
    implementation("io.vavr:vavr:0.10.4")
    implementation("org.jooq:jooq:3.17.5")

    // Mapstruct
    implementation("org.mapstruct:mapstruct:1.5.3.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")

    // HTTP
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation(platform("org.testcontainers:testcontainers-bom:1.17.6"))
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mysql")
    testImplementation("com.github.tomakehurst:wiremock-jre8:2.35.0")
    testImplementation("org.skyscreamer:jsonassert:1.5.1")
    testImplementation("io.rest-assured:rest-assured:4.5.1")
    testImplementation("net.datafaker:datafaker:1.6.0")

    jooqGenerator("com.h2database:h2:2.1.214")
    jooqGenerator("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
}

flyway {
    url = "jdbc:h2:file:${project.buildDir.toPath()}/jooq;MODE=MYSQL"
    user = "sa"
    password = ""
    baselineOnMigrate = true
    locations = arrayOf("filesystem:src/main/resources/db/migration")
    schemas = arrayOf("EVA")
}

jooq {
    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN
                jdbc.apply {
                    driver = "org.h2.Driver"
                    url = flyway.url
                    user = flyway.user
                    password = flyway.password
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.h2.H2Database"
                        inputSchema = "EVA"
                        includes = ".*"
                        excludes = ""
                    }
                }
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JooqGenerate> {
    dependsOn("flywayMigrate")
}

tasks.withType<JavaCompile> {
    dependsOn("generateJooq")
}
