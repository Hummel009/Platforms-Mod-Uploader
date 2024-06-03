plugins {
	id("org.jetbrains.kotlin.jvm")
	id("application")
}

dependencies {
	implementation("com.google.code.gson:gson:2.11.0")
	implementation("org.apache.httpcomponents.client5:httpclient5:5.3.1")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(8)
	}
}