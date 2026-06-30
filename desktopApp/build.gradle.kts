import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(projects.shared)

    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutinesSwing)

    implementation(libs.compose.uiToolingPreview)
}

compose.desktop {
    application {
        mainClass = "com.amonteiro.a06_ynov_kmp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "WeatherApp"
            packageVersion = "1.0.0"
            description = "Une application météo"
            vendor = "Cours Ynov"

            windows {
                shortcut = true
                menu = true
                // Icône pour l'application et l'installateur
                //iconFile.set(project.file("src/commonMain/composeResources/drawable/my_icone.ico"))
            }
            macOS {
                dockName = "WeatherApp"
            }
            linux {
                shortcut = true
            }
        }
    }
}