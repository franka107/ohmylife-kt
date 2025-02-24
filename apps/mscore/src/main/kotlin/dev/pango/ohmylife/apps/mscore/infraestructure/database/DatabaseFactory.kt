package dev.pango.ohmylife.apps.mscore.infraestructure.database

import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import dev.pango.ohmylife.core.app_data.db.AppDataDatabase


object DatabaseFactory {
    fun createDatabase(): AppDataDatabase {
        val dataSource = getDataSource()
        val driver: SqlDriver = dataSource.asJdbcDriver()

        val database = AppDataDatabase(driver)

        AppDataDatabase.Schema.migrate(
            driver = driver,
            oldVersion = 0,
            newVersion = AppDataDatabase.Schema.version,
        )

        return database
    }

}
