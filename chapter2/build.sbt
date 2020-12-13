name := "preowned-kittens"

version := "1.0"

// https://alvinalexander.com/scala/sbt-how-specify-main-method-class-to-run-in-project/
// on peut spécifier la classe à lancer pour 'sbt run'
//mainClass in (Compile, run) := Some("PreownedKittenJava")
//mainClass in (Compile, run) := Some("PreownedKittenMain")

libraryDependencies ++= Seq(
  "org.specs2" % "specs2_2.12" % "2.5" % "test"
)