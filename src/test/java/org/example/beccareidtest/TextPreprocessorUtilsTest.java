package org.example.beccareidtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TextPreprocessorUtilsTest {

    @Autowired
    private TextPreprocessorUtils textPreprocessor;

    @Test
    void testPreprocessRemovesSpecialCharacters() {
        //given
        String input = "C# Engineer!";

        //when
        String result = textPreprocessor.preprocess(input);

        //then
        assertThat(result).isEqualTo("c engineer");
    }

    @Test
    void testPreprocessSplitIntoIndividualWords() {
        //given
        String input = "Software Engineer";

        //when
        String[] result = textPreprocessor.splitIntoWords(input);

        //then
        assertThat(result).containsExactly("software", "engineer");
    }
}