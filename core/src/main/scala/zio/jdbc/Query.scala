/*
 * Copyright 2022 John A. De Goes and the ZIO Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zio.jdbc

import zio._
import zio.stream._

final case class Query[+A](sql: SqlFragment, decode: ZResultSet => A) {

  def as[B](implicit decoder: JdbcDecoder[B]): Query[B] =
    Query(sql, zrs => decoder.unsafeDecode(1, zrs.resultSet)._2)

  def map[B](f: A => B): Query[B] =
    Query(sql, zrs => f(decode(zrs)))

  /**
   * Performs a SQL select query, returning all results in a chunk.
   */
  def selectAll: ZIO[ZQuery, Throwable, Chunk[A]] =
    ZIO.scoped {
      for {
        zrs   <- executeQuery(sql)
        chunk <- ZIO.attempt {
                   val builder = ChunkBuilder.make[A]()
                   while (zrs.next())
                     builder += decode(zrs)
                   builder.result()
                 }
      } yield chunk
    }

  /**
   * Performs a SQL select query, returning the first result, if any.
   */
  def selectOne: ZIO[ZQuery, Throwable, Option[A]] =
    ZIO.scoped {
      for {
        zrs    <- executeQuery(sql)
        option <- ZIO.attempt {
                    if (zrs.next()) Some(decode(zrs)) else None
                  }
      } yield option
    }

  /**
   * Performs a SQL select query, returning a stream of results.
   */
  def selectStream: ZStream[ZQuery, Throwable, A] =
    ZStream.unwrapScoped {
      for {
        zrs   <- executeQuery(sql)
        stream = ZStream.repeatZIOOption {
                   ZIO
                     .suspend(if (zrs.next()) ZIO.attempt(Some(decode(zrs))) else ZIO.none)
                     .mapError(Option(_))
                     .flatMap {
                       case None    => ZIO.fail(None)
                       case Some(v) => ZIO.succeed(v)
                     }
                 }
      } yield stream
    }

  def withDecode[B](f: ZResultSet => B): Query[B] =
    Query(sql, f)

  private def executeQuery(sql: SqlFragment): ZIO[Scope with ZQuery, Throwable, ZResultSet] =
    for {
      connection <- ZIO.service[ZConnection]
      zrs        <- connection.executeSqlWith(sql) { ps =>
                      ZIO.acquireRelease {
                        ZIO.attempt(ZResultSet(ps.executeQuery()))
                      }(_.close)
                    }
    } yield zrs

}

object Query {

  def fromSqlFragment[A](sql: SqlFragment)(implicit decoder: JdbcDecoder[A]): Query[A] =
    Query[A](sql, zrs => decoder.unsafeDecode(1, zrs.resultSet)._2)

}
