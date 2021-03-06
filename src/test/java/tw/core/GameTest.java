package tw.core;

/**
 * 在GameTest文件中完成Game中对应的单元测试
 */

import org.junit.Before;
import org.junit.Test;
import tw.core.generator.AnswerGenerator;
import tw.core.model.GuessResult;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameTest {
	private final Answer actualAnswer = Answer.createAnswer("1 2 3 4");
    private Game game;

    @Before
    public void setUp() throws Exception {
        AnswerGenerator answerGenerator = mock(AnswerGenerator.class);
        when(answerGenerator.generate()).thenReturn(actualAnswer);
        game = new Game(answerGenerator);
    }

    @Test
    public void should_get_record_of_every_guess_result_when_call_guessHistory() {
        //given
        game.guess(Answer.createAnswer("2 1 6 7"));
        game.guess(Answer.createAnswer("1 2 3 4"));

        //when
        List<GuessResult> guessResults = game.guessHistory();

        //then
        assertThat(guessResults.size(), is(2));
        assertThat(guessResults.get(0).getResult(), is("0A2B"));
        assertThat(guessResults.get(0).getInputAnswer().toString(), is("2 1 6 7"));

        assertThat(guessResults.get(1).getResult(), is("4A0B"));
        assertThat(guessResults.get(1).getInputAnswer().toString(), is("1 2 3 4"));
    }

    @Test
    public void should_get_the_success_status_when_guess_input_is_correct() throws Exception {

        //given
        excuteSuccessGuess();
        //when
        String statusOfGame = game.checkStatus();
        //then
        assertThat(statusOfGame, is("success"));

    }


    @Test
    public void should_get_the_fail_status_when_guess_action_count_over_or_equal_6() throws Exception {

        //given
        excuteSixErrorGuess();
        //when
        String statusOfGame = game.checkStatus();
        //then
        assertThat(statusOfGame, is("fail"));

    }

    @Test
    public void should_get_the_continue_status_when_guess_action_count_less_than_6() throws Exception {

        //given
        excuteErrorGuessLessThanSixTimes();
        //when
        String statusOfGame = game.checkStatus();
        //then
        assertThat(statusOfGame, is("continue"));

    }

    @Test
    public void should_get_ture_when_incorrect_guess_action_number_less_than_6() throws Exception {
        //given
        game.guess(Answer.createAnswer("2 1 9 3"));
        //when
        Boolean isContinue = game.checkCoutinue();
        //then
        assertThat(isContinue, is(true));

    }

    @Test
    public void should_get_ture_when_incorrect_guess_action_number_over_or_equal_6() throws Exception {
        //given
        excuteSixErrorGuess();
        //when
        Boolean isContinue = game.checkCoutinue();
        //then
        assertThat(isContinue, is(false));

    }

    @Test
    public void should_get_false_when_correct_guess() throws Exception {
        //given
        excuteSuccessGuess();
        //when
        Boolean isContinue = game.checkCoutinue();
        //then
        assertThat(isContinue, is(false));

    }

    private void excuteSuccessGuess() {
        game.guess(Answer.createAnswer("5 2 7 4"));
        game.guess(Answer.createAnswer("1 2 3 4"));
    }

    private void excuteErrorGuessLessThanSixTimes() {
        game.guess(Answer.createAnswer("2 7 3 4"));
        game.guess(Answer.createAnswer("1 5 3 4"));
        game.guess(Answer.createAnswer("1 8 2 1"));
    }

    private void excuteSixErrorGuess() {
        game.guess(Answer.createAnswer("2 9 3 4"));
        game.guess(Answer.createAnswer("1 5 3 4"));
        game.guess(Answer.createAnswer("1 8 2 1"));
        game.guess(Answer.createAnswer("1 2 3 9"));
        game.guess(Answer.createAnswer("4 3 2 1"));
        game.guess(Answer.createAnswer("1 5 6 4"));
    }
}
