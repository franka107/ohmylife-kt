package dev.pango.ohmylife.apps.mscore.infraestructure.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource


fun getDataSource(): DataSource {

    return try {
        createDataSource(
            driverClassName = "org.postgresql.Driver",
            jdbcUrl = "jdbc:postgresql://pango.dev:5432/ohmylife_development",
            password = "Dn8cNL7rxXS4G9nsfVbxS7mIPGJvwzvgnpxZ89FehFS1AGjh1KKnUP3LWGUmv4NA",
            username = "postgres",
        )
    } catch (e: Exception) {
        println("Database connection error: ${e.message}")
        throw e
    }
}

private fun createDataSource(
    driverClassName: String,
    jdbcUrl: String,
    password: String,
    username: String,
): DataSource {
    val config = HikariConfig()
    config.driverClassName = driverClassName
    config.password = password
    config.jdbcUrl = jdbcUrl
    config.username = username

    //config.validate()
    return HikariDataSource(config)
}
