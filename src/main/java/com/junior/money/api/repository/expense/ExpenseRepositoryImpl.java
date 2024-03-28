package com.junior.money.api.repository.expense;

import java.util.ArrayList;
import java.util.List;

import com.junior.money.api.models.Expense;
import com.junior.money.api.models.Expense_;
import com.junior.money.api.repository.filter.ExpenseFilter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ExpenseRepositoryImpl implements ExpenseRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Expense> filter(ExpenseFilter filter) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Expense> criteria = builder.createQuery(Expense.class);

        Root<Expense> root = criteria.from(Expense.class);

        // Create restrictions
        Predicate[] predicates = createRestrictions(filter, builder, root);
        criteria.where(predicates);
        TypedQuery<Expense> query = manager.createQuery(criteria);
        return query.getResultList();
    }

    private Predicate[] createRestrictions(ExpenseFilter filter, CriteriaBuilder builder, Root<Expense> root) {

        List<Predicate> predicates = new ArrayList<>();
        if (filter.getDescription() != null) {
            predicates.add(builder.like(
                    builder.lower(root.get(Expense_.description)),
                    "%" + filter.getDescription().toLowerCase() + "%"));
        }

        if (filter.getDueDateFrom() != null) {
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get(Expense_.dueDate), filter.getDueDateFrom()));
        }

        if (filter.getDueDateTo() != null) {
            predicates.add(
                    builder.lessThanOrEqualTo(root.get(Expense_.dueDate), filter.getDueDateTo()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
