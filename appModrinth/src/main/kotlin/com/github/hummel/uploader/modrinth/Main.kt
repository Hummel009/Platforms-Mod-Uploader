package com.github.hummel.uploader.modrinth

import com.github.hummel.uploader.Config
import com.github.hummel.uploader.extractMcVersion
import com.github.hummel.uploader.extractModLoader
import com.github.hummel.uploader.sortAlphabetically
import com.google.gson.Gson
import org.apache.hc.client5.http.classic.methods.HttpPost
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.core5.http.ContentType
import org.apache.hc.core5.http.io.entity.EntityUtils
import java.io.File

fun main() {
	val config = Gson().fromJson(File("config.json").readText(), Config::class.java)

	config.projectIds.forEach { project ->
		publishProject(project, config.token)
		readln()
	}
}

private fun publishProject(
	project: String, token: String
) {
	val files = File("folders/$project").listFiles() ?: return

	files.sortAlphabetically()

	files.forEach { jar ->
		val mcVersion = jar.name.extractMcVersion()
		val modLoader = jar.name.extractModLoader().lowercase()

		HttpClients.createDefault().use {
			val url = "https://api.modrinth.com/v2/version"

			val request = HttpPost(url)
			request.addHeader("Authorization", token)
			request.addHeader("User-Agent", "Hummel009/SUS/9.99.0 (h.t@g.c)")

			val multipartEntityBuilder = MultipartEntityBuilder.create()
			multipartEntityBuilder.addTextBody(
				"data", """
				{
					"name": "${jar.name}",
					"version_number": "24.02.29",
					"dependencies": [],
					"version_type": "release",
					"featured": true,
					"project_id": "$project",
					"file_parts": ["file"],
					"loaders": ["$modLoader"],
					"game_versions": ["$mcVersion"]
				}
				""".trimIndent(), ContentType.APPLICATION_JSON
			)
			multipartEntityBuilder.addBinaryBody(
				"file", jar, ContentType.DEFAULT_BINARY, jar.name
			)

			request.entity = multipartEntityBuilder.build()

			val response = it.execute(request) { response ->
				val entity = response.entity
				EntityUtils.toString(entity)
			}

			println(response)
		}
	}
}