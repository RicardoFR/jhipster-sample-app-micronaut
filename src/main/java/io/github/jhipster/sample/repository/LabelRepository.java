package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.BankAccount;
import io.github.jhipster.sample.domain.Label;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * Micronaut Predator repository for the Label entity.
 */
@SuppressWarnings("unused")
@Repository
public abstract class LabelRepository implements JpaRepository<Label, Long> {

    private EntityManager entityManager;

    public LabelRepository(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Label mergeAndSave(Label label) {
        label = entityManager.merge(label);
        return save(label);
    }
}
