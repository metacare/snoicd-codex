package org.weso.snoicd.controllers;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.weso.snoicd.services.ConceptsService;
import org.weso.snoicd.types.ResponseToQuery;

@RestController
public class CoceptController {

	// The service that will communicate with the repository.
	@Autowired
	ConceptsService service;

	// The response object.
	private ResponseEntity<ResponseToQuery> response;

	// The response JSON to the query submitted.
	private ResponseToQuery rtq;

	/**
	 * Defines an entry point for the search of concepts in the system.
	 * 
	 * @param q      is the query to search.
	 * @param filter to apply if present.
	 * @return a ResponseEntity object with a ResponseToQuery object in the body
	 *         containing the result of executing the given query.
	 */
	@RequestMapping(value = "/api/v2/search", method = RequestMethod.GET)
	public ResponseEntity<ResponseToQuery> searchEntryPoint(@RequestParam @NotNull String q,
			@RequestParam @Nullable String filter) {

		// Initiating the JSON response object to que query.
		rtq = new ResponseToQuery();

		// Storing the query performed.
		rtq.setQuery(q + "&" + filter);

		// If the filter is present in the query...
		if (filter != null && filter != "" && filter != " ") {

			// If we filter by code.
			if (filter.equals("code")) {
				rtq.setResult(this.service.getConceptByCode(q));
				rtq.setStatus("OK");
				response = new ResponseEntity<ResponseToQuery>(rtq, HttpStatus.OK);

				// If we filter only by description.
			} else if (filter.equals("description")) {
				rtq.setResult(this.service.getConceptsByDescription(q));
				rtq.setStatus("OK");
				response = new ResponseEntity<ResponseToQuery>(rtq, HttpStatus.OK);

				// In any other case the query is not accepted.
			} else {
				rtq.setStatus("Invalid query");
				response = new ResponseEntity<ResponseToQuery>(rtq, HttpStatus.BAD_REQUEST);
			}

			// If there is no filter present, then...
		} else {

			// Get results for code.
			rtq.setResult(this.service.getConceptByCode(q));

			// Add with the results for description.
			rtq.getResult().addAll(this.service.getConceptsByDescription(q));

			// And finally set the status to OK.
			rtq.setStatus("OK");

			response = new ResponseEntity<ResponseToQuery>(rtq, HttpStatus.OK);
		}

		// Finally return the response.
		return response;
	}
}