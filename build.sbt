seq(gwtSettings: _*)

scalaHome <<= baseDirectory { base => Some(base / "lib" / "scala") }

autoCompilerPlugins := true

scalacOptions ++= Seq(
  "-g:notailcalls",
  "-Xplugin:lib/scala/factorymanifests.jar",
  "-Xplugin:lib/scala/continuations.jar",
  "-P:continuations:enable",
  "-Yjribble-text"
)

webappResources <<= baseDirectory (_ / "war")

unmanagedJars in Compile <++= baseDirectory map { base =>
	val baseDirectories = (base / "lib" / "gwt") +++ (base / "lib" / "scala")
	(baseDirectories ** "*.jar").classpath
}

gwtModules := Seq(
  "com.google.gwt.sample.jribble.Hello",
  "com.google.gwt.sample.mnemonics.Mnemonics",
  "com.google.gwt.sample.showcase.Showcase",
  "com.google.gwt.sample.gwtdlx.gwtdlx"
)

libraryDependencies += "org.mortbay.jetty" % "jetty" %"6.1.22" % "jetty"