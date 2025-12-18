package com.practicum.playlistmaker.utils

object ImageUtils {
    /**
     * Возвращает URL с указанным качеством, если это iTunes artwork.
     * Для локальных URI (content:// или file://) возвращает как есть.
     * @param url Оригинальный URL или URI-string.
     * @param size Размер в формате "NxN", например "600x600" или "10000x10000".
     * @return String? с указанным размером, или null.
     */
    fun getArtworkUrl(url: String?, size: String = "100x100"): String? {
        if (url == null) return null
        if (url.startsWith("http") && url.contains("100x100")) {
            return url.replace("100x100", size)
        }
        return url
    }
}