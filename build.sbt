lazy val commonSettings = Seq(
  organization := "com.github.SymphonyNo9",
  version := "0.1.0",
  scalaVersion := "2.11.8"
)

val commonDependencies = Seq()
// add dependencies on standard Scala modules, in a way
// supporting cross-version publishing
// taken from: http://github.com/scala/scala-module-dependency-sample
libraryDependencies := {
  CrossVersion.partialVersion(scalaVersion.value) match {
    // if Scala 2.12+ is used, use scala-swing 2.x
    case Some((2, scalaMajor)) if scalaMajor >= 12 =>
      libraryDependencies.value ++ Seq(
        "org.scala-lang.modules" %% "scala-xml" % "1.1.1",
        "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.1",
        "org.scala-lang.modules" %% "scala-swing" % "2.0.3")
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      libraryDependencies.value ++ Seq(
        "org.scala-lang.modules" %% "scala-xml" % "1.1.1",
        "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.1",
        "org.scala-lang.modules" %% "scala-swing" % "1.0.2")
    case _ =>
      // or just libraryDependencies.value if you don't depend on scala-swing
      libraryDependencies.value :+ "org.scala-lang" % "scala-swing" % scalaVersion.value
  }
}

lazy val root = (project in file("."))
  .aggregate(fpis, core, catsp)
  .dependsOn(core)
  .settings(
    commonSettings,
    name := "fp"
  )

lazy val fpis = (project in file("fpis"))
  .settings(
    commonSettings
    // other settings
  )

lazy val core = (project in file("core"))
  .settings(
    commonSettings
    // other settings
  )

lazy val catsp = (project in file("catsp"))
  .settings(commonSettings).
  dependsOn(core).
  settings(libraryDependencies ++= Seq("org.typelevel" %% "cats-core" % "2.0.0"))

