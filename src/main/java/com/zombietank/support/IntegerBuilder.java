package com.zombietank.support;

public class IntegerBuilder implements Builder<Integer>, Validatable {
	private final String input;

	public static IntegerBuilder forInput(String input) {
		return new IntegerBuilder(input);
	}

	public IntegerBuilder(String input) {
		this.input = input;
	}
	
	@Override
	public boolean isValid() {
		try {
			return input != null && Integer.valueOf(input) > 0;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	@Override
	public Integer build() {
		return Integer.valueOf(input);
	}
}
