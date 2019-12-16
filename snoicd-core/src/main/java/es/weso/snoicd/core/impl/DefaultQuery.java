package es.weso.snoicd.core.impl;

import es.weso.snoicd.core.Concept;
import es.weso.snoicd.core.Query;
import scala.collection.immutable.Stream;
import scala.util.Either;
import scala.util.Left;

import java.util.Objects;

/** The type Default query. */
public class DefaultQuery implements Query {

    private String queryString;
    private Either<Exception, Stream<Concept>> queryResult;

  /**
   * Instantiates a new Default query.
   *
   * @param queryString the query string
   */
  public DefaultQuery(final String queryString) {
        setQueryString(queryString);
    }

    @Override
    public void setQueryString(final String queryString) {
        if( Objects.isNull(queryString)) {
            queryResult = new Left(new IllegalArgumentException("The query string cannot be null"));
        } else if(queryString.isEmpty()) {
            queryResult = new Left(new IllegalArgumentException("The query string cannot be empty"));
        } else {
            this.queryString = queryString;
        }
    }

    @Override
    public String getQueryString() {
        return this.queryString;
    }

    @Override
    public void setResult(final Either<Exception, Stream<Concept>> result) {
        if(Objects.isNull(result))
            this.queryResult = result;
    }

    @Override
    public Either<Exception, Stream<Concept>> getResult() {
        return this.queryResult;
    }
}
