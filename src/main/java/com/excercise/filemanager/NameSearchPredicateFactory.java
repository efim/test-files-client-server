package com.excercise.filemanager;

import org.springframework.stereotype.Component;

import com.google.common.base.Predicate;

@Component
public class NameSearchPredicateFactory implements PredicateFactory<String>{

	@Override
	public Predicate<String> getPredicate(String pattern) {
	    String regex = ("\\Q" + pattern + "\\E").replace("*", "\\E.*\\Q");
	    return (String e) -> e.matches(regex);
	}

}
