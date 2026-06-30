import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}
dependencies {
    implementation(projects.shared)

    implementation(libs.androidx.activity.compose)

    implementation(libs.compose.uiToolingPreview)
    debugImplementation(libs.compose.uiTooling)

    implementation("io.insert-koin:koin-android:4.1.+")
}

// Read API key from local.properties
val localProperties = Properties() //import java.utils
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}


android {
    namespace = "com.amonteiro.a06_ynov_kmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.amonteiro.a06_ynov_kmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    defaultConfig {
        //Mettre une valeur plus élevée à chaque mise à jour
        versionCode = 1
        //N'importe quelle chaîne de caractères
        versionName = "0.5-beta"
    }

    //Récupérer les informations du keystore dans local.properties pour signer l'exécutable de sortie
    //A mettre avant buildTypes
    signingConfigs {
        val keystoreFile = localProperties.getProperty("KEYSTORE_FILE")
        if (!keystoreFile.isNullOrBlank()) {
            create("release") {
                // rootProject.file -> chemin résolu depuis la racine (où est le keystore)
                storeFile = rootProject.file(keystoreFile)
                storePassword = localProperties.getProperty("KEYSTORE_PASSWORD")
                keyAlias = localProperties.getProperty("KEY_ALIAS")
                keyPassword = localProperties.getProperty("KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.findByName("release")
        }
    }
}