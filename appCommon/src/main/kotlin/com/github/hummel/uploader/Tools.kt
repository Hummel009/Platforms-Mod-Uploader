package com.github.hummel.uploader

import java.io.File

fun String.extractMcVersion(): String {
	val versionRegex = Regex("""\[(.+)\]""")
	val versionMatch = versionRegex.find(this)
	return versionMatch?.groupValues?.get(1) ?: ""
}

fun String.extractModLoader(): String {
	val versionRegex = Regex("""\((.+)\)""")
	val versionMatch = versionRegex.find(this)
	return versionMatch?.groupValues?.get(1) ?: ""
}

fun Array<out File>.sortAlphabetically() {
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

	sortWith(fileComparator)
}