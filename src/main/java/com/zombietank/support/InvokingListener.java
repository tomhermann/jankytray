package com.zombietank.support;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Method;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.springframework.beans.BeanUtils;

/***
 * An event handler that invokes a method via reflection when *any* event is
 * received.
 * 
 * @author Tom Hermann
 */
public class InvokingListener<T> implements Listener {
	private final T instance;
	private final Method method;
	private final Object[] args;

	public static <T> Listener invokingListener(T instance, String methodName, Object... args) {
		Method method = BeanUtils.findMethodWithMinimalParameters(instance.getClass(), methodName);
		checkNotNull(method, "Unable to find method named: " + methodName + ", on: " + instance);
		return new InvokingListener<T>(instance, method, args);
	}

	public InvokingListener(T instance, Method method, Object... args) {
		this.instance = instance;
		this.method = method;
		this.args = args;
	}

	public void handleEvent(Event event) {
		try {
			method.invoke(instance, args);
		} catch (Exception e) {
			throw new RuntimeException("Unable to invoke method: " + method.getName() + ", on object: " + instance, e);
		}
	}
}
