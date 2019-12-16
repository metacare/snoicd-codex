package es.weso.snoicd.core

/**
 * A query will represent the input / output object in a search
 * operation. Will contain at least the following information:
 * - query string -> containing the text to look for
 * - result -> by using either type it wraps the stream of matching
 *             concepts or an error (ex.: persistence failure)
 */
trait Query {

  /**
   * Sets the text to look for.
   *
   * @param queryString is the text that will be used to look for hits
   *                    over all the concepts.
   */
  def setQueryString(queryString: String)

  /**
   * Gets the text that is being used to search for hits over all the
   * concepts.
   *
   * @return text that is being used to search for hits over all the
   *         concepts.
   */
  def getQueryString(): String

  /**
   * Sets the result of the query after being executed. As the result of
   * the execution might be an error or the set of concepts that matches
   * the given text query, the result is an either type where Right is an
   * string containing the error description and Left the stream of concepts
   * that match the query.
   *
   * @param result is the result object, can be Right or Left as it is wrapped
   *               in an Either type.
   */
  def setResult(result: Either[Exception, Stream[Concept]])

  /**
   * Gets the result of the query after being executed. As the result of
   * the execution might be an error or the set of concepts that matches
   * the given text query, the result is an either type where Right is an
   * string containing the error description and Left the stream of concepts
   * that match the query.
   *
   * @return the result object, can be Right or Left as it is wrapped in an
   *         Either type.
   */
  def getResult(): Either[Exception, Stream[Concept]]

}
