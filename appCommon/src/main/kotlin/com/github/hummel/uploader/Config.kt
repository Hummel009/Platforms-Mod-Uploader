package com.github.hummel.uploader

data class Config(
	val token: String, val projectIds: List<String>, val projectNames: List<String>
)