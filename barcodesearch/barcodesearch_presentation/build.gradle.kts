apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.barcodesearchDomain))
    "implementation"(project(Modules.coreUi))

    "implementation"(Coil.coilCompose)
}