package dev.marshi.jetmemo.ui.player

data class PlayerState(
    val type: Type = Type.PAUSE
) {
    enum class Type { PLAY, PAUSE }

    fun isPlay(): Boolean {
        return this.type == Type.PLAY
    }
}