package repo

import com.datastax.oss.driver.api.core.`type`.DataTypes
import com.datastax.oss.driver.api.core.cql.{ResultSet, SimpleStatement}
import com.datastax.oss.driver.api.core.{CqlIdentifier, CqlSession}
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace
import config.AppConfig.appConfig.cassandra._
import config.DBConfig.{closeDBInitSession, openDBInitSession}
import utils.Logging

import scala.util.{Failure, Success, Try}

object CassandraDB extends Logging {

  private lazy val session: CqlSession = openDBInitSession(host, port, datacentre)

  def init: Try[ResultSet] = {
    logger.info("Configuring Keyspace and Schema in Cassandra...")
    Try {
      createKeyspaceIfNotExists()
      useKeyspace
      createInventoryTableIfNotExists
      createOrderTableIfNotExists
    }
  }

  def closeDBSession(): Unit = closeDBInitSession(session)

  private def createKeyspaceIfNotExists(): Unit = {
    val cks: CreateKeyspace = SchemaBuilder
      .createKeyspace(keyspace)
      .ifNotExists
      .withSimpleStrategy(replicas)
    session.execute(cks.build)
  }

  private def useKeyspace: ResultSet =
    session.execute("USE " + CqlIdentifier.fromCql(keyspace))

  private def createInventoryTableIfNotExists: ResultSet = {
    logger.info(s"Creating $inventorytablename table if it does not already exist")
    val statement: SimpleStatement = SchemaBuilder
      .createTable(inventorytablename)
      .ifNotExists
      .withPartitionKey("itemId", DataTypes.TEXT)
      .withColumn("description", DataTypes.TEXT)
      .withColumn("quantity", DataTypes.INT)
      .build
    session.execute(statement)
  }

  private def createOrderTableIfNotExists: ResultSet = {
    logger.info(s"Creating $ordertablename table if it does not already exist")
    val statement: SimpleStatement = SchemaBuilder
      .createTable(ordertablename)
      .ifNotExists
      .withPartitionKey("userId", DataTypes.TEXT)
      .withClusteringColumn("orderId", DataTypes.TEXT)
      .withColumn("itemId", DataTypes.TEXT)
      .withColumn("quantity", DataTypes.INT)
      .withColumn("orderDate", DataTypes.TIMESTAMP)
      .build
    session.execute(statement)
  }
}
