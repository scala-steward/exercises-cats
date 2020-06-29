import com.jsuereth.sbtpgp.PgpKeys.publishSigned

ThisBuild / organization := "org.scala-exercises"
ThisBuild / githubOrganization := "47degrees"
ThisBuild / scalaVersion := "2.13.2"

publish / skip := true

// This is required by the exercises compiler:
publishLocal := (publishLocal dependsOn compile).value
publishSigned := (publishSigned dependsOn compile).value

addCommandAlias("ci-test", "scalafmtCheckAll; scalafmtSbtCheck; test")
addCommandAlias("ci-docs", "github; documentation/mdoc; headerCreateAll")
addCommandAlias("ci-publish", "github; ci-release")

lazy val exercises = (project in file("."))
  .settings(name := "exercises-cats")
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-exercises"        %% "exercise-compiler"         % "0.6.3",
      "org.scala-exercises"        %% "definitions"               % "0.6.3",
      "org.typelevel"              %% "cats-core"                 % "2.1.1",
      "com.chuusai"                %% "shapeless"                 % "2.3.3",
      "org.scalatest"              %% "scalatest"                 % "3.2.0",
      "org.scalacheck"             %% "scalacheck"                % "1.14.3",
      "org.scalatestplus"          %% "scalacheck-1-14"           % "3.2.0.0",
      "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.5"
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)
  )
  .enablePlugins(ExerciseCompilerPlugin)

lazy val documentation = project
  .settings(mdocOut := file("."))
  .settings(publish / skip := true)
  .enablePlugins(MdocPlugin)
