package org.example.beccareidtest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.beccareidtest.JobTitles.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class MatchServiceTest {

    @Autowired
    private MatchService matchService;

    @Test
    void testFindBestMatchWithSameString() {
        //given
        String input = "Architect";

        //when
        MatchResult result = matchService.findBestMatch(input);

        //then
        assertThat(result.match()).isEqualTo(JobTitles.ARCHITECT.getJobTitle());
        assertThat(result.qualityScore()).isEqualTo(1.0);
    }

    @Test
    void testFindBestMatchWithOneWordInStringMatch() {
        //given
        String input = "C# Engineer";

        //when
        MatchResult result = matchService.findBestMatch(input);

        //then
        assertThat(result.match()).isEqualTo(JobTitles.SOFTWARE_ENGINEER.getJobTitle());
        assertThat(result.qualityScore()).isEqualTo(1.0);
    }

    @Test
    void testFindBestMatchWithSmallTypo() {
        //given
        String input = "architecy";

        //when
        MatchResult result = matchService.findBestMatch(input);

        //then
        assertThat(result.match()).isEqualTo(JobTitles.ARCHITECT.getJobTitle());
        assertThat(result.qualityScore()).isGreaterThan(0.8);
    }

    @Test
    void shouldNotFindMatchIfMatchIsNotPresent() {
        //given
        String input = "Random";

        //when
        MatchResult result = matchService.findBestMatch(input);

        //then
        assertThat(result.match()).isEqualTo("No match found");
        assertThat(result.qualityScore()).isEqualTo(0.0);
    }

    @Test
    void testReturnsNoMatchIfInputIsBlank() {
        //given
        String input = "";

        //when
        MatchResult result = matchService.findBestMatch(input);

        //then
        assertThat(result.match()).isEqualTo("No match found");
        assertThat(result.qualityScore()).isEqualTo(0.0);
    }

    // possible edge case here eg Java Developer- doesn't look anything like software engineer
}