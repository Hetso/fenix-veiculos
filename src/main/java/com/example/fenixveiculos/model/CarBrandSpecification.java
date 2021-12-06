package com.example.fenixveiculos.model;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

public class CarBrandSpecification {

	public static Specification<CarBrandModel> name(String name) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.like(root.get("name"), "%" + name + "%");
	}

	public static Specification<CarBrandModel> isDisabled(boolean disabled) {
		return (root, criteriaQuery,
				criteriaBuilder) -> getBrandIsDisabledCriteria(
						root.get("isDisabled")
								.as(Boolean.class),
						criteriaBuilder, disabled);
	}

	public static Predicate getBrandIsDisabledCriteria(
			Expression<Boolean> expression,
			CriteriaBuilder criteriaBuilder, boolean disabled) {
		return disabled ? criteriaBuilder.isTrue(expression)
				: criteriaBuilder.isFalse(expression);
	}

}
