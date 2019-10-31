package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.AnswerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class AnswerDao {
    @PersistenceContext
    private EntityManager entityManager;

    public AnswerEntity createAnswer(AnswerEntity answerEntity) {
        entityManager.persist(answerEntity);
        return answerEntity;
    }

    public AnswerEntity getAnswerById(String questionId) {
        try {
            return entityManager.createNamedQuery("answerById", AnswerEntity.class).getSingleResult();

        } catch (NoResultException nre) {

            return null;
        }
    }

    public AnswerEntity updateAnswer(AnswerEntity answerEntity) {

        return entityManager.merge(answerEntity);

    }
}