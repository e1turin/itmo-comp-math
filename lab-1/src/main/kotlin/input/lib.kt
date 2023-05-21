package input

import java.util.*
import kotlin.random.Random

fun Scanner.nextIntOrNull(isRandom: Boolean = false): Int? = if (isRandom) {
    Random.nextInt()
} else {
    try {
        this.nextInt()
    } catch (e: Exception) {
        null
    }
}

fun Scanner.nextDoubleOrNull(isRandom: Boolean = false): Double? = if (isRandom) {
    Random.nextDouble()// * 2_000 - 1_000
} else {
    try {
        this.nextDouble()
    } catch (e: Exception) {
        null
    }
}
