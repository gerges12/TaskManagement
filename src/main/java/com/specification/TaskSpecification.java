package com.specification;

import org.springframework.data.jpa.domain.Specification;

import com.models.Task;

public class TaskSpecification {
    public static Specification<Task> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> 
                title == null ? null : criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Task> hasDescription(String description) {
        return (root, query, criteriaBuilder) -> 
                description == null ? null : criteriaBuilder.like(root.get("description"), "%" + description + "%");
    }

    public static Specification<Task> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> 
                status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Task> hasDueDate(String dueDate) {
        return (root, query, criteriaBuilder) -> 
                dueDate == null ? null : criteriaBuilder.equal(root.get("dueDate"), dueDate);
    }
}
