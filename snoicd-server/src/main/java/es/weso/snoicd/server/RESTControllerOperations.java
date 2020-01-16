package es.weso.snoicd.server;

import es.weso.snoicd.core.Concept;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.stream.Stream;

@RequestMapping("/default")
public interface RESTControllerOperations {

    DeferredResult<ResponseEntity<Stream<Concept>>> search(String query);
}
