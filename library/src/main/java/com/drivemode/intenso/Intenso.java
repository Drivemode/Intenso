/*
 * Copyright (C) 2017 Drivemode, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at

 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.drivemode.intenso;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Small set of utilities to call a hidden method, get/set a hidden field, and overwrite value of final field.
 * @author KeithYokoma
 */
@SuppressWarnings("unused") // public APIs.
public final class Intenso {
	private Intenso() {
		// no instance
		throw new AssertionError("no instance!");
	}

	/**
	 * Alias method to call {@link Intenso#invoke(Object, String, Class[], Object[])} without any of arguments.
	 * @see Intenso#invoke(Object, String, Class[], Object[])
	 */
	public static Object invoke(Object target, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return invoke(target, methodName, null, null);
	}

	/**
	 * Invokes a method declared in a target object.
	 * @param target to call the method.
	 * @param methodName the method name.
	 * @param argTypes array of types for the method argument.
	 * @param args actual arguments.
	 * @return a value returned from the method.
	 * @see Class#getDeclaredMethod(String, Class[])
	 * @see Method#invoke(Object, Object...)
	 */
	public static Object invoke(Object target, String methodName, Class[] argTypes, Object[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Class<?> clazz = target.getClass();
		Method method = clazz.getDeclaredMethod(methodName, argTypes);
		method.setAccessible(true);
		return method.invoke(target, args);
	}

	/**
	 * Alias method to call {@link Intenso#invoke(Class, String, Class[], Object[])} without any of arguments.
	 * @see Intenso#invoke(Class, String, Class[], Object[])
	 */
	public static Object invoke(Class<?> clazz, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return invoke(clazz, methodName, null, null);
	}

	/**
	 * Invokes a static method declared in a specified class.
	 * @param clazz to call the static method.
	 * @param methodName the method name.
	 * @param argTypes array of types for the method argument.
	 * @param args actual arguments
	 * @return a value returned from the method.
	 * @see Class#getDeclaredMethod(String, Class[])
	 * @see Method#invoke(Object, Object...)
	 */
	public static Object invoke(Class<?> clazz, String methodName, Class[] argTypes, Object[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = clazz.getDeclaredMethod(methodName, argTypes);
		method.setAccessible(true);
		return method.invoke(null, args);
	}

	/**
	 * Get a value of a specified hidden field in a target object.
	 * @param target a target object having a field.
	 * @param fieldName to get a value.
	 * @return a value set in the specified field.
	 * @see Class#getDeclaredField(String)
	 * @see Field#get(Object)
	 */
	public static Object get(Object target, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		Field f = target.getClass().getDeclaredField(fieldName);
		f.setAccessible(true);
		return f.get(target);
	}

	/**
	 * Set a value to a specified hidden field in a target object.
	 * @param target a target object having a field.
	 * @param fieldName to set a value.
	 * @param value new value to be set.
	 * @see Class#getDeclaredField(String)
	 * @see Field#set(Object, Object)
	 */
	public static void set(Object target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
		Field f = target.getClass().getDeclaredField(fieldName);
		f.setAccessible(true);
		f.set(target, value);
	}

	/**
	 * Overwrite a value of a static-final field in a specified class.
	 * @param clazz a target class.
	 * @param fieldName to be modified.
	 * @param newValue to be set in the specified field.
	 * @see Class#getDeclaredField(String)
	 * @see Field#set(Object, Object)
	 */
	public static void overwrite(Class<?> clazz, String fieldName, Object newValue) throws NoSuchFieldException, IllegalAccessException {
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(null, newValue);
	}
}
