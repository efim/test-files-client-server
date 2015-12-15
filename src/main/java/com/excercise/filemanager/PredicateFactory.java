package com.excercise.filemanager;

import com.google.common.base.Predicate;

public interface PredicateFactory<T> {
	public Predicate<T> getPredicate(T param);
}
