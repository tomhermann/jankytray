package com.zombietank.support;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.eclipse.swt.widgets.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InvokingListenerTest {
	@Mock
	private Event event;
	@Mock
	private Tester tester;

	@Test
	public void willInvokePublicNoArgsMethod() {
		InvokingListener.invokingListener(tester, "test").handleEvent(event);
		
		verify(tester, times(1)).test();
	}

	@Test
	public void willInvokePublicMethodWithArgs() {
		InvokingListener.invokingListener(tester, "testArgs", "poo").handleEvent(event);
		
		verify(tester, times(1)).testArgs("poo");
	}
	
	private interface Tester {
		public void test();
		public void testArgs(String poo);
	}
}
