package es.weso.snoicd.search;

import es.weso.snoicd.core.Query;

public interface Search {

    Query executeQuery(Query query);
}
