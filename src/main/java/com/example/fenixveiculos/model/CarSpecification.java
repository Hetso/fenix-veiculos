package com.example.fenixveiculos.model;

import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

public class CarSpecification {

	public static Specification<CarModel> isBrandDisabled(boolean disabled) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			Join<CarModel, CarBrandModel> brandJoin = root.join("brand");

			return CarBrandSpecification
					.getBrandIsDisabledCriteria(brandJoin.get("isDisabled")
							.as(Boolean.class), criteriaBuilder, disabled);
		};
	}
}
