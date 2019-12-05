package org.weso.snoicd.core.util

import java.text.Normalizer

/**
 * Normalizes an string by:
 *      "-|/" -> " " (white space).
 *      ".,()[]" -> "" (just removing).
 *      (non-ascii characters) -> "" (just removing).
 *      all -> to lower case.
 */
fun String.normalize(): String {

    // Transform the string by removing "-|/", and ".,()[]".
    var ret = this.replace("-".toRegex(), " ")
            .replace(("/").toRegex(), " ")
            .replace(("[.,]").toRegex(), "")
            .replace(("[()]").toRegex(), "")
            .replace(("[\\[\\]]").toRegex(), "")

    // Transform non ascii characters to ascii ones or remove them.
    ret = Normalizer.normalize(ret, Normalizer.Form.NFD)
            .replace("[^\\p{ASCII}]".toRegex(), "")
            .toLowerCase()

    return ret
}
