package io.github.e1turin.shared.lib.cringe

/**
 * From JSON to CON
 * So called CON - cringe object notation: literally JSON with replaced commas with semicolons and dots replaced with commas.
 */
@DelicateCringeApi
internal fun String.toCringeFormat(): String = replace(',', ';').replace('.', ',')

/**
 * From CON to JSON
 * So-called CON - cringe object notation: literally JSON with replaced commas with semicolons and dots replaced with commas.
 */
@DelicateCringeApi
internal fun String.fromCringeFormat(): String = replace(',', '.').replace(';', ',')


@Retention(value = AnnotationRetention.BINARY)
@RequiresOptIn(
    level = RequiresOptIn.Level.WARNING,
    message = "This is a delicate API and its use requires care and insensitivity to the cringe."
            + " Make sure you fully read and understand documentation of the declaration that is marked as a delicate API."
)
annotation class DelicateCringeApi