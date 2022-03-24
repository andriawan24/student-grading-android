package com.andriawan.sistempenilaianmahasiswa.utils

/**
 * Kelas ini digunakan untuk mengatasi pemanggilan
 * Ganda dari kelas LiveData
 *
 * @author Naufal Fawwaz Andriawan
 */
class SingleEvents<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Fungsi ini digunakan untuk mengambil konten pertama kali ketika LiveData
     * Memasuki state onChanged()
     *
     * @return class generic optional
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}