package com.zombietank.support;

public class IntegerBuilder implements Builder<Integer> {

	private String input;

	public IntegerBuilder(String input) {
		this.input = input;
	}

	public IntegerBuilder withNumber(String input) {
		this.input = input;
		return this;
	}
	
	public boolean isValidInteger() {
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
