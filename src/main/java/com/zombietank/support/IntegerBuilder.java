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
		if(input == null) {
			return false;
		}
		try {
			Integer.valueOf(input);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	@Override
	public Integer build() {
		return Integer.valueOf(input);
	}
}
