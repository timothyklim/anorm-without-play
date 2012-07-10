name := "Scala Anorm"

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/snapshots"

libraryDependencies ++= Seq(
  "play" %% "anorm" % "2.1-SNAPSHOT",
  "com.github.seratch" %% "scalikejdbc" % "[0.5,)",
  "postgresql" % "postgresql" % "9.1-901.jdbc4"
)
