package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.BankAccount;
import io.github.jhipster.sample.domain.Operation;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.spring.tx.annotation.Transactional;


import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * Micronaut Predator repository for the Operation entity.
 */
@Repository
public abstract class OperationRepository implements JpaRepository<Operation, Long> {

    private EntityManager entityManager;

    @Query(value = "select distinct operation from Operation operation left join fetch operation.labels",
        countQuery = "select count(distinct operation) from Operation operation")
    public abstract Page<Operation> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct operation from Operation operation left join fetch operation.labels")
    public abstract List<Operation> findAllWithEagerRelationships();

    @Query("select operation from Operation operation left join fetch operation.labels where operation.id =:id")
    public abstract Optional<Operation> findOneWithEagerRelationships(Long id);

    public OperationRepository(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Operation mergeAndSave(Operation operation) {
        operation = entityManager.merge(operation);
        return save(operation);
    }
}
