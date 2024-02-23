package com.github.hummel.cfmu

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.apache.hc.client5.http.classic.methods.HttpGet
import org.apache.hc.client5.http.classic.methods.HttpPost
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.core5.http.ContentType
import org.apache.hc.core5.http.io.entity.EntityUtils
import java.io.File

fun main() {
	val config = Gson().fromJson(File("config.json").readText(), Config::class.java)
	val idToVersion = getMappingsFromAPI(config.token)

	config.projectIds.forEach { project ->
		publishProject(project, idToVersion, config.token)
	}
}

private fun publishProject(
	project: Int, idToVersion: MutableMap<Int, String>, token: String
) {
	val files = File("folders/$project").listFiles() ?: return
	val fileComparator = Comparator<File> { file1, file2 ->
		val version1 = file1.name.extractMcVersion()
		val version2 = file2.name.extractMcVersion()

		val parts1 = version1.split('.').map { it.toInt() }
		val parts2 = version2.split('.').map { it.toInt() }

		return@Comparator (0 until 3).asSequence().map {
			parts1[it].compareTo(parts2[it])
		}.firstOrNull {
			it != 0
		} ?: 0
	}

	files.sortWith(fileComparator)

	files.forEach { jar ->
		val (mcVersionId, modLoaderId) = getJarInfo(jar, idToVersion)
		val serverSidenessId = 9639
		val clientSidenessId = 9638

		HttpClients.createDefault().use {
			val url = "https://minecraft.curseforge.com/api/projects/$project/upload-file"

			val request = HttpPost(url)
			request.addHeader("X-Api-Token", token)

			val multipartEntityBuilder = MultipartEntityBuilder.create()
			multipartEntityBuilder.addTextBody(
				"metadata", """
										{
											"changelog": "",
											"gameVersions": [$mcVersionId, $modLoaderId, $serverSidenessId, $clientSidenessId],
											"releaseType": "release"
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

private val modLoaderMappings = mapOf(
	"NeoForge" to 10150,
	"Forge" to 7498,
	"Fabric" to 7499,
	"Quilt" to 9153
)

private fun getJarInfo(
	jar: File, idToVersion: MutableMap<Int, String>
): Pair<Int, Int> {
	val mcVersion = jar.name.extractMcVersion()
	val modLoader = jar.name.extractModLoader()

	var mcVersionId = 0

	for ((id, value) in idToVersion) {
		if (value == mcVersion) {
			mcVersionId = id
			break
		}
	}

	require(mcVersionId != 0)

	return mcVersionId to (modLoaderMappings[modLoader] ?: error("Wrong ModLoader ID!"))
}

private fun getMappingsFromAPI(token: String): MutableMap<Int, String> {
	HttpClients.createDefault().use {
		val request = HttpGet("https://minecraft.curseforge.com/api/game/versions")
		request.addHeader("X-Api-Token", token)

		val response = it.execute(request) { response ->
			val entity = response.entity

			EntityUtils.toString(entity)
		}

		return@getMappingsFromAPI parseMappings(response)
	}
}

private fun parseMappings(json: String): MutableMap<Int, String> {
	val gson = Gson()

	val listType = object : TypeToken<List<GameVersion>>() {}.type
	val gameVersions = gson.fromJson<List<GameVersion>>(json, listType)

	val idToVer = mutableMapOf<Int, String>()
	gameVersions.forEach {
		idToVer[it.id] = it.name
	}

	return idToVer
}

private fun String.extractMcVersion(): String {
	val versionRegex = Regex("""\[(.+)\]""")
	val versionMatch = versionRegex.find(this)
	return versionMatch?.groupValues?.get(1) ?: ""
}

private fun String.extractModLoader(): String {
	val versionRegex = Regex("""\((.+)\)""")
	val versionMatch = versionRegex.find(this)
	return versionMatch?.groupValues?.get(1) ?: ""
}

data class GameVersion(
	val id: Int, val gameVersionTypeID: Int, val name: String, val slug: String, val apiVersion: String?
)

data class Config(
	val token: String,
	val projectIds: List<Int>,
	val projectNames: List<String>
)