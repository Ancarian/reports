package org.openpreservation.api.utils;

public interface Converter<T, K> {

	T convert(K value);
}
