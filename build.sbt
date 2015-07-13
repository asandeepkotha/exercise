name := "exercise"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= {
  Seq(
    "io.spray" %% "spray-json" % "1.3.2",
	"junit" % "junit" % "4.12",
	"org.skyscreamer" % "jsonassert" % "1.2.3"
	
  )
}

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-Ywarn-dead-code",
  "-language:_",
  "-target:jvm-1.7",
  "-encoding", "UTF-8"
)
