/*******************************************************************************
 * MIT License
 * 
 * Copyright (c) 2019 CODE OWNERS (See CODE_OWNERS.TXT)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.weso.snoicd.controllers;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.weso.snoicd.search.AllFieldsSearch;
import org.weso.snoicd.search.CodeSearch;
import org.weso.snoicd.search.DescriptionSearch;
import org.weso.snoicd.search.InvalidSearch;
import org.weso.snoicd.services.ConceptsService;
import org.weso.snoicd.types.ResponseToQuery;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ConceptsController {

	// The service that will communicate with the repository.
	@Autowired
	ConceptsService service;

	/**
	 * Defines an entry point for the search of concepts in the system.
	 * 
	 * @param q      is the query to search.
	 * @param filter to apply if present.
	 * @return a ResponseEntity object with a ResponseToQuery object in the body
	 *         containing the result of executing the given query.
	 */
	@RequestMapping(value = "/api/search", method = RequestMethod.GET)
	public ResponseEntity<ResponseToQuery> searchEntryPoint(@RequestParam @NotNull String q,
			@RequestParam(required = false) @Nullable String filter) {

		log.info("SEARCH request received.");
		
		// The response JSON to the query submitted.
		ResponseToQuery rtq;
	
		// If the filter is present in the query...
		if (filter != null && filter != "" && filter != " ") {
			log.info("Filter found.");

			// If we filter by code.
			if (filter.equals("code")) {
				log.info("Filter was: code.");
				rtq = new CodeSearch(this.service, q).execute();
				return new ResponseEntity<ResponseToQuery>(rtq, rtq.getStatus());

				// If we filter only by description.
			} else if (filter.equals("description")) {
				log.info("Filter was: description.");
				rtq = new DescriptionSearch(this.service, q).execute();
				return new ResponseEntity<ResponseToQuery>(rtq, rtq.getStatus());

				// In any other case the query is not accepted.
			} else {
				rtq = new InvalidSearch().execute();
				return new ResponseEntity<ResponseToQuery>(rtq, rtq.getStatus());
			}

			// If there is no filter present, then...
		} else {
			log.info("No filter found.");
			rtq = new AllFieldsSearch(this.service, q).execute();
			return new ResponseEntity<ResponseToQuery>(rtq, rtq.getStatus());
		}
	}
	
	/**
	 * Defines an entry point for the search of concepts in the system.
	 * 
	 * @param q      is the query to search.
	 * @param filter to apply if present.
	 * @return a ResponseEntity object with a ResponseToQuery object in the body
	 *         containing the result of executing the given query.
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<ResponseToQuery> search(@RequestParam @NotNull String q,
			@RequestParam(required = false) @Nullable String filter) {

		log.info("SEARCH request received.");
		System.out.println(q);
		// The response JSON to the query submitted.
		ResponseToQuery rtq;
	
		// If the filter is present in the query...
		if (filter != null && filter != "" && filter != " ") {
			log.info("Filter found.");

			// If we filter by code.
			if (filter.equals("code")) {
				log.info("Filter was: code.");
				rtq = new CodeSearch(this.service, q).execute();
				return new ResponseEntity<ResponseToQuery>(rtq, rtq.getStatus());

				// If we filter only by description.
			} else if (filter.equals("description")) {
				log.info("Filter was: description.");
				System.out.println(q);
				rtq = new DescriptionSearch(this.service, q).execute();
				return new ResponseEntity<ResponseToQuery>(rtq, rtq.getStatus());

				// In any other case the query is not accepted.
			} else {
				rtq = new InvalidSearch().execute();
				return new ResponseEntity<ResponseToQuery>(rtq, rtq.getStatus());
			}

			// If there is no filter present, then...
		} else {
			log.info("No filter found.");
			rtq = new AllFieldsSearch(this.service, q).execute();
			return new ResponseEntity<ResponseToQuery>(rtq, rtq.getStatus());
		}
	}
}
