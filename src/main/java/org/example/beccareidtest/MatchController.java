package org.example.beccareidtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling HTTP requests related to matching job titles.
 */

@RestController
@RequestMapping("/api/match")
public class MatchController {

    private final MatchService matchService;

    /**
     * Creates a new MatchController with the given MatchService.
     *
     * @param matchService the MatchService to use
     */
    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    /**
     * Finds the best match for the given input.
     *
     * @param input the input string to match
     * @return the best match result
     */
    @GetMapping("/find")
    public ResponseEntity<MatchResult> findBestMatch(@RequestParam String input) {
        // this controller only allows for one input at a time, it would be good to allow for multiple inputs
        MatchResult matchResult = matchService.findBestMatch(input);
        return ResponseEntity.ok(matchResult);
    }
}