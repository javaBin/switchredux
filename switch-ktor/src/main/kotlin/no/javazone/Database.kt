package no.javazone

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.http.*
import no.javazone.errors.InternalException
import org.flywaydb.core.Flyway
import org.jsonbuddy.JsonObject
import java.sql.Connection
import java.util.concurrent.ConcurrentHashMap
import javax.sql.DataSource

enum class DataBaseType(val driverClass:String?=null) {
    POSTGRES,SQLLITE,PGINMEM("no.anksoft.pginmem.PgInMemDatasource");

    fun jdbcUrl(dbHost:String, dbPort:String, dataSourceName:String) = when (this) {
        POSTGRES -> "jdbc:postgresql://$dbHost:$dbPort/$dataSourceName"
        SQLLITE -> "jdbc:sqlite::memory:"
        PGINMEM -> "jdbc:pginmem::memory:"
    }
}

private class MyConnection(private val connection: Connection):Connection by connection {
}

object Database {
    val datasource:DataSource by lazy {
        val dbHost = Setup.readValue(SetupValue.DBHOST)
        val dbPort = Setup.readValue(SetupValue.DBPORT)
        val dataSourceName = Setup.readValue(SetupValue.DATASOURCENAME)
        val dbUser = Setup.readValue(SetupValue.DBUSER)
        val dbPassword = Setup.readValue(SetupValue.DBPASSWORD)

        val dataBaseType:DataBaseType = DataBaseType.valueOf(Setup.readValue(SetupValue.DATABASE_TYPE))

        if (dataBaseType == DataBaseType.PGINMEM) {
            Class.forName("no.anksoft.pginmem.PgInMemDatasource").getDeclaredConstructor().newInstance() as DataSource
        } else {

            val hikariConfig = HikariConfig()
            if (dataBaseType.driverClass != null) {
                hikariConfig.dataSourceClassName = dataBaseType.driverClass
            }
            hikariConfig.jdbcUrl = dataBaseType.jdbcUrl(dbHost, dbPort, dataSourceName)
            if (dbUser.isNotEmpty()) {
                hikariConfig.username = dbUser
                hikariConfig.password = dbPassword
            }
            hikariConfig.maximumPoolSize = 10
            val ds = HikariDataSource(hikariConfig)
            ds
        }
    }

    fun migrateWithFlyway(spesialSetup:((Flyway) -> Unit)?=null, preset:((Connection)->Unit)?=null) {
        if (preset != null) {
            connection().use {
                preset.invoke(it)
            }
        }
        val flyway:Flyway = Flyway.configure().dataSource(datasource).load()
        spesialSetup?.invoke(flyway)
        flyway.migrate()
    }


    private val connStore = ConcurrentHashMap<Long,Connection>()

    fun doWithConnection(toDo:()->Pair<HttpStatusCode,JsonObject>) {
        val conn = datasource.connection
        conn.autoCommit = false
        val threadId = Thread.currentThread().id
        connStore[threadId] = conn
        var haveCommited = false
        try {
            val result = toDo()
            if (result.first.value >= 200 && result.first.value < 300) {
                conn.commit()
                haveCommited = true
            }
        } finally {
            if (!haveCommited) {
                conn.rollback();
            }
            conn.close()
            connStore.remove(threadId)
        }
    }

    fun connection():Connection = connStore[Thread.currentThread().id]?:throw InternalException("Getting connection when not created")

}