package com.github.hummel.uploader.curseforge

data class GameVersion(
	val id: Int, val gameVersionTypeID: Int, val name: String, val slug: String, val apiVersion: String?
)