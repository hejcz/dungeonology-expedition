package io.github.hejcz.dungeonology.game

interface Event

data class ErrorEvent(val errorCode: ErrorCode) : Event