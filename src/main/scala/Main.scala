import java.sql.Connection

import scalikejdbc.ConnectionPool

import java.util.Date

import anorm._
import anorm.SqlParser._


object Main extends App {
  Class.forName("org.postgresql.Driver")
  ConnectionPool.singleton("jdbc:postgresql://localhost/pullbox", "timothyklim", "")

  object DB {
    def withConnection[A](block: Connection => A): A = {
      val connection: Connection = ConnectionPool.borrow()

      try {
        block(connection)
      } finally {
        connection.close()
      }
    }
  }

  case class User(
    id: Int = 0,
    full_name: String,
    email: String,
    password: String,
    password_salt: String,
    created_at: Date
  )

  object User {

    val user = {
      get[Int]("users.id") ~
      get[String]("users.full_name") ~
      get[String]("users.email") ~
      get[String]("users.password") ~
      get[String]("users.password_salt") ~
      get[Date]("users.created_at") map {
        case id ~ full_name ~ email ~ password ~ password_salt ~ created_at =>
          User(
            id = id,
            full_name = full_name,
            email = email,
            password = password,
            password_salt = password_salt,
            created_at = created_at
          )
      }
    }

    def findAll(): Seq[User] = {
      DB.withConnection {
        implicit connection: Connection =>
          SQL("select * from users").as(User.user *)
      }
     }
  }

  println(User.findAll)
}
