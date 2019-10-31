 
package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.QuestionDetailsResponse;
import com.upgrad.quora.api.model.QuestionRequest;
import com.upgrad.quora.api.model.QuestionResponse;
import com.upgrad.quora.service.business.AuthorizationService;
import com.upgrad.quora.service.business.CreateQuestionBusinessService;
import com.upgrad.quora.service.business.GetAllQuestionsBusinessService;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/")
public class QuestionController {

    @Autowired
    private CreateQuestionBusinessService createQuestionBusinessService;

    @Autowired
    private GetAllQuestionsBusinessService getAllQuestionsBusinessService;

    @Autowired
    AuthorizationService authorizationService;

    @RequestMapping(method = RequestMethod.POST, path = "/question/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionResponse> createQuestion(@RequestHeader("accessToken") final String accessToken, final QuestionRequest questionRequest) throws AuthorizationFailedException {
        String[] bearerToken = accessToken.split("Bearer ");
        UserAuthTokenEntity userAuthTokenEntity = createQuestionBusinessService.verifyAuthToken(bearerToken[1]);
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setUuid(UUID.randomUUID().toString());
        questionEntity.setUser(userAuthTokenEntity.getUser());
        questionEntity.setContent(questionRequest.getContent());
        final ZonedDateTime now = ZonedDateTime.now();
        questionEntity.setDate(now);
        final QuestionEntity createdQuestionEntity = createQuestionBusinessService.createQuestion(questionEntity);
        QuestionResponse questionResponse = new QuestionResponse().id(createdQuestionEntity.getUuid()).status("QUESTION CREATED");
        return new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/question/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionDetailsResponse>> getAllQuestions(@RequestHeader("accessToken") final String accessToken) throws AuthorizationFailedException {
        List<QuestionEntity> questionEntityList = getAllQuestionsBusinessService.getAllQuestions(accessToken);

        List<QuestionDetailsResponse> questionDetailsResponseList = new ArrayList<QuestionDetailsResponse>();
        if (!questionEntityList.isEmpty()) {

            for (QuestionEntity questionEntity : questionEntityList) {
                QuestionDetailsResponse questionDetailsResponse = new QuestionDetailsResponse();
                questionDetailsResponse.setId(questionEntity.getUuid());
                questionDetailsResponse.setContent(questionEntity.getContent());

                questionDetailsResponseList.add(questionDetailsResponse);
            }

        }
        return new ResponseEntity<>(questionDetailsResponseList, HttpStatus.OK);
    }
}