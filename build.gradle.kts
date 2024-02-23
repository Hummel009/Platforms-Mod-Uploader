import java.time.LocalDate
import java.time.format.DateTimeFormatter

plugins {
	id("org.jetbrains.kotlin.jvm") version "1.9.22"
	id("application")
}

group = "com.github.hummel"
version = LocalDate.now().format(DateTimeFormatter.ofPattern("yy.MM.dd"))

val embed: Configuration by configurations.creating

dependencies {
	embed("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
	embed("com.google.code.gson:gson:2.10.1")
	embed("org.apache.httpcomponents.client5:httpclient5:5.3")
	implementation("com.google.code.gson:gson:2.10.1")
	implementation("org.apache.httpcomponents.client5:httpclient5:5.3")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(8)
	}
}

application {
	mainClass = "com.github.hummel.cfmu.MainKt"
}

tasks {
	named<JavaExec>("run") {
		standardInput = System.`in`
	}
	jar {
		manifest {
			attributes(
				mapOf(
					"Main-Class" to "com.github.hummel.cfmu.MainKt"
				)
			)
		}
		from(embed.map {
			if (it.isDirectory) it else zipTree(it)
		})
		duplicatesStrategy = DuplicatesStrategy.EXCLUDE
	}
}
