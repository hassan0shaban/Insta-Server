val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val koin_version: String by project

plugins {
    application
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.0-RC"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("com.google.cloud.tools.appengine") version "2.4.2"
    id("org.gretty") version ("3.0.6")
    id("war")
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.example.ApplicationKt")
}
repositories {
    mavenCentral()
}
appengine {
    stage {
        setArtifact("build/libs/${project.name}-${project.version}-all.jar")
    }
    deploy {
        version = "GCLOUD_CONFIG"
        projectId = "GCLOUD_CONFIG"
    }
}
tasks {
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "com.example.ApplicationKt"))
        }
    }
}

gretty {
    servletContainer = "tomcat9"
    contextPath = '/'
    logbackConfigFile = "src/main/resources/logback.xml"
}
dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")

    // gson
    implementation("io.ktor:ktor-gson:$ktor_version")

    //servlet
    implementation("io.ktor:ktor-server-servlet:$ktor_version")

    // Koin
    testImplementation("io.insert-koin:koin-test:$koin_version")
    // Koin core features
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")

    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-auth-jwt:$ktor_version")

    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-websockets:$ktor_version")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")

    testImplementation("com.google.truth:truth:1.1.3")
    //exposed
    implementation("org.jetbrains.exposed:exposed:$exposed_version")
    //connector
    implementation("mysql:mysql-connector-java:8.0.26")
    //serialization
    implementation("io.ktor:ktor-serialization:$ktor_version")
}