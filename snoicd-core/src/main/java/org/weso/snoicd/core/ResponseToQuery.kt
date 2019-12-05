package org.weso.snoicd.core

import java.io.Serializable

/**
 * Represents the response to a query for the snoicd systyem. Every response will
 * have the executed query and the result. The result can be an empty set if no
 * hit for the query is get.
 */
data class ResponseToQuery(var query: String = "",
                           var result: Set<Concept> = HashSet<Concept>()) : Serializable
