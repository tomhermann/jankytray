package com.zombietank.support;

import static org.mockito.Mockito.*;

import org.eclipse.swt.widgets.Event;
import org.junit.Test;

public class InvokingListenerTest {

	@Test
	public void willInvokePublicNoArgsMethod() {
		Event event = mock(Event.class);
		Tester tester = mock(Tester.class);
		
		InvokingListener.invokingListener(tester, "test").handleEvent(event);
		
		verify(tester, times(1)).test();
	}

	@Test
	public void willInvokePublicMethodWithArgs() {
		Event event = mock(Event.class);
		Tester tester = mock(Tester.class);
		
		InvokingListener.invokingListener(tester, "testArgs", "poo").handleEvent(event);
		
		verify(tester, times(1)).testArgs("poo");
	}
	
	private class Tester {
		public void test() {
		}

		public void testArgs(String poo) {
		}
	}
}
