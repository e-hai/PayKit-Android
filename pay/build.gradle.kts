plugins {
    alias(libs.plugins.android.library)
    `maven-publish`
}

android {
    namespace = "com.kit.pay"
    compileSdk = 37

    defaultConfig {
        minSdk = 21

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

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

val publishVersion: String = providers.gradleProperty("versionName")
    .orElse(providers.gradleProperty("version"))
    .orElse(providers.gradleProperty("VERSION_NAME"))
    .orElse("1.0.1")
    .get()
    .removePrefix("v")

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.e-hai"
                artifactId = "PayKit-Android"
                version = publishVersion

                pom {
                    name.set("PayKit-Android")
                    description.set("Lightweight Android Google Play Billing SDK with Kotlin Coroutines.")
                    url.set("https://github.com/e-hai/PayKit-Android")
                    licenses {
                        license {
                            name.set("The MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/e-hai/PayKit-Android.git")
                        developerConnection.set("scm:git:ssh://github.com/e-hai/PayKit-Android.git")
                        url.set("https://github.com/e-hai/PayKit-Android")
                    }
                }
            }
        }
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/e-hai/PayKit-Android")
                credentials {
                    username = providers.gradleProperty("gpr.user")
                        .orElse(providers.environmentVariable("GITHUB_ACTOR"))
                        .orNull
                    password = providers.gradleProperty("gpr.key")
                        .orElse(providers.environmentVariable("GITHUB_TOKEN"))
                        .orNull
                }
            }
        }
    }
}

dependencies {
    implementation(libs.bundles.pay)
    testImplementation(libs.junit)
}
