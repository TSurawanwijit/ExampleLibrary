plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    id("maven-publish")
    id("signing")
    id("com.vanniktech.maven.publish") version "0.29.0"
}

android {
    namespace = "com.blu.me.librarya"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

publishing {
    publications {
        register<MavenPublication>("libraryARelease") {
            groupId = "com.github.tsurawanwijit"
            artifactId = "examplelibrary"
            version = "1.0.18"

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name = "Library A"
                description = "A concise description of library A"
                url = "https://github.com/TSurawanwijit/ExampleLibrary.git"
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id = "tsurawanwijit"
                        name = "thianchai surawanwijit"
                        email = "t.surawanwijit@gmail.com"
                        url = "https://github.com/TSurawanwijit"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/TSurawanwijit/ExampleLibrary.git"
                    developerConnection = "scm:git:ssh://git@github.com/TSurawanwijit/ExampleLibrary.git"
                    url = "https://github.com/TSurawanwijit/ExampleLibrary.git"
                }
            }
        }
    }
    repositories {
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/TSurawanwijit/ExampleLibrary")
            credentials {
                username = project.findProperty("gpr.user")?.toString() ?: ""
                password = project.findProperty("gpr.token")?.toString() ?: ""
            }
//            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
//            credentials {
//                username = project.findProperty("mavenCentralUsername") as String? ?: ""
//                password = project.findProperty("mavenCentralPassword") as String? ?: ""
//            }
        }
    }
}

signing {
    useGpgCmd()
    val signingKey: String = project.findProperty("signing.keyId")?.toString() ?: ""
    val secretKey: String = project.findProperty("signing.secretKey")?.toString() ?: ""
    val signingPassword: String = project.findProperty("signing.password")?.toString() ?: ""
    useInMemoryPgpKeys(signingKey, secretKey, signingPassword)
    sign(publishing.publications["libraryARelease"])
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}