plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.sqdelight)
    alias(libs.plugins.kotlin.serialization)
}

group = "dev.pango.ohmylife"
version = "0.0.1"

application {

    mainClass = "io.ktor.server.netty.EngineMain"
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    google()
}

sqldelight {
    databases {
        create("AppDataDatabase") {
            dialect(libs.sqldelight.postgresql.dialect)
            packageName.set("dev.pango.ohmylife.core.app_data.db")
            srcDirs("src/main/sqldelight_app_data")
            deriveSchemaFromMigrations = true
            verifyMigrations = true
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.arrow.core)
    implementation(libs.arrow.fxcoroutines)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.contentnegotiation)
    implementation(libs.ktor.serializationkotlinxjson)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.sqldelight.jdbc.driver)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)

    // Ktor Koin
    implementation(libs.koin.ktor)
    implementation(libs.koin.core)
    implementation(libs.koin.logger.slf4j)

    // https://github.com/brettwooldridge/HikariCP#artifacts
    implementation("com.zaxxer:HikariCP:5.0.1")

    // This is needed for the PostgreSQL driver
    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.6.0")
}
