package com.example.fenixveiculos.utils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;

public class MapperUtils {

	private MapperUtils() {
	}

	private static void setConfiguration() {
		modelMapper.getConfiguration()
				.setFieldAccessLevel(AccessLevel.PRIVATE)
				.setFieldMatchingEnabled(true);
	}

	private static final ModelMapper modelMapper = new ModelMapper();

	/**
	 * Convert a object in another object
	 * 
	 */
	public static <D> D convert(Object from, Class<D> to) {
		setConfiguration();
		return modelMapper.map(from, to);
	}

	/**
	 * Convert a optional object in another object
	 * 
	 */
	public static <D> D convert(Optional<?> from, Class<D> to) {
		setConfiguration();

		if (from.isPresent()) {
			return modelMapper.map(from.get(), to);
		}

		return null;
	}

	/**
	 * Convert a list object in another list object
	 * 
	 */
	public static <D> List<D> convert(List<?> from, Class<D> to) {
		setConfiguration();
		return from.stream()
				.map(f -> modelMapper.map(f, to))
				.collect(Collectors.toList());
	}
}
