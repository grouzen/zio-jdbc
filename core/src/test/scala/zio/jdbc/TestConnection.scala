package zio.jdbc

import zio._

import java.sql.{
  Blob,
  CallableStatement,
  Clob,
  Connection,
  DatabaseMetaData,
  NClob,
  PreparedStatement,
  SQLWarning,
  SQLXML,
  Savepoint,
  Statement,
  Struct
}
import java.util._
import java.{ sql, util }

class TestConnection extends Connection {
  private var closed = false

  def close(): Unit = closed = true

  def isClosed: Boolean = closed

  def createStatement(): Statement = ???

  def prepareStatement(sql: String): PreparedStatement = ???

  def prepareCall(sql: String): CallableStatement = ???

  def nativeSQL(sql: String): String = ???

  def setAutoCommit(autoCommit: Boolean): Unit = ???

  def getAutoCommit: Boolean = true

  def commit(): Unit = ???

  def rollback(): Unit = ???

  def getMetaData: DatabaseMetaData = ???

  def setReadOnly(readOnly: Boolean): Unit = ???

  def isReadOnly: Boolean = ???

  def setCatalog(catalog: String): Unit = ???

  def getCatalog: String = ???

  def setTransactionIsolation(level: RuntimeFlags): Unit = ???

  def getTransactionIsolation: RuntimeFlags = 1

  def getWarnings: SQLWarning = ???

  def clearWarnings(): Unit = ???

  def createStatement(resultSetType: RuntimeFlags, resultSetConcurrency: RuntimeFlags): Statement = ???

  def prepareStatement(
    sql: String,
    resultSetType: RuntimeFlags,
    resultSetConcurrency: RuntimeFlags
  ): PreparedStatement = ???

  def prepareCall(sql: String, resultSetType: RuntimeFlags, resultSetConcurrency: RuntimeFlags): CallableStatement =
    ???

  def getTypeMap: util.Map[String, Class[_]] = ???

  def setTypeMap(map: util.Map[String, Class[_]]): Unit = ???

  def setHoldability(holdability: RuntimeFlags): Unit = ???

  def getHoldability: RuntimeFlags = ???

  def setSavepoint(): Savepoint = ???

  def setSavepoint(name: String): Savepoint = ???

  def rollback(savepoint: Savepoint): Unit = ???

  def releaseSavepoint(savepoint: Savepoint): Unit = ???

  def createStatement(
    resultSetType: RuntimeFlags,
    resultSetConcurrency: RuntimeFlags,
    resultSetHoldability: RuntimeFlags
  ): Statement = ???

  def prepareStatement(
    sql: String,
    resultSetType: RuntimeFlags,
    resultSetConcurrency: RuntimeFlags,
    resultSetHoldability: RuntimeFlags
  ): PreparedStatement = ???

  def prepareCall(
    sql: String,
    resultSetType: RuntimeFlags,
    resultSetConcurrency: RuntimeFlags,
    resultSetHoldability: RuntimeFlags
  ): CallableStatement = ???

  def prepareStatement(sql: String, autoGeneratedKeys: RuntimeFlags): PreparedStatement = ???

  def prepareStatement(sql: String, columnIndexes: Array[RuntimeFlags]): PreparedStatement = ???

  def prepareStatement(sql: String, columnNames: Array[String]): PreparedStatement = ???

  def createClob(): Clob = ???

  def createBlob(): Blob = ???

  def createNClob(): NClob = ???

  def createSQLXML(): SQLXML = ???

  def isValid(timeout: RuntimeFlags): Boolean = ???

  def setClientInfo(name: String, value: String): Unit = ???

  def setClientInfo(properties: Properties): Unit = ???

  def getClientInfo(name: String): String = ???

  def getClientInfo: Properties = ???

  def createArrayOf(typeName: String, elements: Array[AnyRef]): sql.Array = ???

  def createStruct(typeName: String, attributes: Array[AnyRef]): Struct = ???

  def setSchema(schema: String): Unit = ???

  def getSchema: String = ???

  def abort(executor: concurrent.Executor): Unit = ???

  def setNetworkTimeout(executor: concurrent.Executor, milliseconds: RuntimeFlags): Unit = ???

  def getNetworkTimeout: RuntimeFlags = ???

  def unwrap[T](iface: Class[T]): T = ???

  def isWrapperFor(iface: Class[_]): Boolean = ???
}