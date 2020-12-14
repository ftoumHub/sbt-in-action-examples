// This sets the project name to the string literal "preowned-kittens"
name := "preowned-kittens"
version in ThisBuild := "1.0"
organization in ThisBuild := "com.preownedkittens"

// Custom keys for this build.
// on peut ensuite, après avoir lancé sbt, exécuter:
// sbt:preowned-kittens> show gitHeadCommitSha
// ou sbt:preowned-kittens> gitHeadCommitSha
// sbt:preowned-kittens> show makeVersionProperties
// ou sbt:preowned-kittens> gitHeadCommitSha
val gitHeadCommitSha = taskKey[String]("Determines the current git commit SHA")
// génère un fichier version.properties ici :
// D:\DevHome\sbt-in-action-examples\chapter3\target\scala-2.12\resource_managed\main\version.properties
val makeVersionProperties = taskKey[Seq[File]]("Creates a version.properties file we can find at runtime.")

// Common settings/definitions for the build

def PreownedKittenProject(name: String): Project = (
  Project(name, file(name))
  settings(
    libraryDependencies += "org.specs2" % "specs2_2.12" % "2.5" % "test"
  )
)

gitHeadCommitSha in ThisBuild := scala.sys.process.Process("git rev-parse HEAD").lines.head

// Projects in this build

lazy val common = (
  PreownedKittenProject("common")
  settings(
    makeVersionProperties := {
      val propFile = (resourceManaged in Compile).value / "version.properties"
      val content = "version=%s" format (gitHeadCommitSha.value)
      IO.write(propFile, content)
      Seq(propFile)
    },
    resourceGenerators in Compile += makeVersionProperties
  )
)

lazy val analytics = (
  PreownedKittenProject("analytics")
  dependsOn(common)
  settings()
)

lazy val website = (
  PreownedKittenProject("website")
  dependsOn(common)
  settings()
)
