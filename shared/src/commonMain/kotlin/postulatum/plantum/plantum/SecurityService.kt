package postulatum.plantum.plantum

/**
 * Very simple, cross‑platform pseudo hash.
 * Not secure – used only to avoid storing clear text passwords until real hashing (BCrypt/Argon2) is added.
 */
object SecurityService {

    // Simple reversible‑resistant mixing; still NOT cryptographically secure.
    fun hashPassword(password: String): String {
        var h1 = 0x9E3779B9u
        var h2 = 0x85EBCA6Bu
        var h3 = 0xC2B2AE35u

        for ((i, ch) in password.withIndex()) {
            val x = ch.code.toUInt() + (i.toUInt() shl 1)
            h1 = (h1 xor (x * 0x27D4EB2Du)) rotateLeft 5
            h2 = (h2 + x * 0x165667B1u) rotateLeft 7
            h3 = (h3 xor (x * 0x9E3779B9u)) rotateLeft 11
        }

        val a = (h1 + (h2 shl 1) + (h3 shr 1))
        val b = (h2 xor (h3 + 0x7F4A7C15u))
        val c = (h3 + (h1 xor 0xDEADBEEFu))

        return toHex(a) + toHex(b) + toHex(c)
    }

    fun verifyPassword(password: String, storedHash: String): Boolean =
        hashPassword(password) == storedHash

    private infix fun UInt.rotateLeft(bits: Int): UInt =
        (this shl bits) or (this shr (32 - bits))

    private fun toHex(v: UInt): String = buildString(8) {
        val hex = "0123456789abcdef"
        var x = v
        repeat(8) {
            val nibble = (x shr 28).toInt() and 0xF
            append(hex[nibble])
            x = x shl 4
        }
    }
}