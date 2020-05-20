import EndpointsUzhttpSettings._

val `uzhttp-server` = LocalProject("uzhttp-server")

lazy val apiDoc = project
  .in(file("api-doc"))
  .settings(
    `scala 2.12 to latest`,
    unidocProjectFilter in (ScalaUnidoc, unidoc) := inProjects(
      `uzhttp-server`
    ),
    siteSubdirName in ScalaUnidoc := "latest/api",
    addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), siteSubdirName in ScalaUnidoc),
    git.remoteRepo := "git@github.com:jkobejs/endpoints-uzhttp.git",
    paradoxProperties ++= Map(
      "version"           -> version.value,
      "scaladoc.base_url" -> s".../latest/api"
    )
  )
  .enablePlugins(ScalaUnidocPlugin)
  .enablePlugins(GhpagesPlugin)
  .enablePlugins(ParadoxSitePlugin)
  .enablePlugins(ParadoxMaterialThemePlugin)

val `example-documented` =
  project
    .in(file("examples/documented"))
    .settings(
      noPublishSettings,
      `scala 2.12 to latest`,
      publishArtifact := false,
      libraryDependencies ++= Seq(
        "org.julienrf" %% "endpoints-algebra"             % endpointsVersion,
        "org.julienrf" %% "endpoints-json-schema-generic" % endpointsVersion
      )
    )
    .dependsOn(`uzhttp-server`)

// Basic example
val `example-basic-shared` =
  project
    .in(file("examples/basic/shared"))
    .settings(
      noPublishSettings,
      `scala 2.12 to latest`,
      libraryDependencies ++= Seq(
        "io.circe"     %% "circe-generic"           % circeVersion,
        "org.julienrf" %% "endpoints-algebra"       % endpointsVersion,
        "org.julienrf" %% "endpoints-algebra-circe" % endpointsVersion
      ),
      macroParadiseDependency
    )

val `example-basic-uzhttp-server` =
  project
    .in(file("examples/basic/uzhttp-server"))
    .settings(
      commonSettings,
      `scala 2.12 to latest`,
      publishArtifact := false
    )
    .dependsOn(`uzhttp-server`, `example-basic-shared`)
