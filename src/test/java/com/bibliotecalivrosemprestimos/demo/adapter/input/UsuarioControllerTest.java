//package br.com.efactor.chat.adapter.input;
//
//import br.com.efactor.chat.adapter.in.FeedbackController;
//import br.com.efactor.chat.domain.usecase.FeedbackEntrevistaUseCase;
//import br.com.efactor.chat.port.input.FeedbackEntrevistaInputPort;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ExtendWith(SpringExtension.class)
//public class FeedControllerTest {
//
//    @InjectMocks
//    private FeedbackController feedbackController;
//
//    //    @Mock
//    private FeedbackEntrevistaInputPort feedbackEntrevistaInputPort;
//
//    @BeforeEach
//    void setup() {
//        feedbackEntrevistaInputPort = Mockito.mock(FeedbackEntrevistaUseCase.class);
//    }
//
//    @Test
//    @DisplayName("Deve retornar feedback de entrevista")
//    void testFeedBackByUserSucess() {
//        //given
//        String comentario = "Comentario...";
//
//        //when
//        long id = feedbackController.feedbackEntrevista(1, comentario);
//
//
//        //then
//        Assertions.assertEquals(1, id);
//    }
//
//
//
//}
//