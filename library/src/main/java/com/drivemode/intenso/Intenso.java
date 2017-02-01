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

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Small set of utilities to call a hidden method, get/set a hidden field, and overwrite value of final field.
 * @author KeithYokoma
 */
@SuppressWarnings({"unused", "WeakerAccess"}) // public APIs.
public final class Intenso {
	private static final String TAG = Intenso.class.getSimpleName();

	private Intenso() {
		// no instance
		throw new AssertionError("no instance!");
	}

	/**
	 * Alias method to call {@link Intenso#invoke(Object, String)} with try-catch clause.
	 * @return null if nothing returned or exception received.
	 */
	public static <T> T tryInvoke(Object target, String methodName) {
		try {
			return invoke(target, methodName);
		} catch (NoSuchMethodException e) {
			Log.e(TAG, "no such method " + methodName, e);
		} catch (IllegalAccessException e) {
			Log.e(TAG, "illegal access to " + methodName, e);
		} catch (InvocationTargetException e) {
			Log.e(TAG, "exception thrown " + methodName, e);
		}
		return null;
	}

	/**
	 * Alias method to call {@link Intenso#invoke(Object, String, Class[], Object[])} with try-catch clause.
	 * @return null if nothing returned or exception received.
	 */
	public static <T> T tryInvoke(Object target, String methodName, Class[] argTypes, Object[] args) {
		try {
			return invoke(target, methodName, argTypes, args);
		} catch (NoSuchMethodException e) {
			Log.e(TAG, "no such method " + methodName, e);
		} catch (IllegalAccessException e) {
			Log.e(TAG, "illegal access to " + methodName, e);
		} catch (InvocationTargetException e) {
			Log.e(TAG, "exception thrown " + methodName, e);
		}
		return null;
	}

	/**
	 * Alias method to call {@link Intenso#invoke(Object, String, Class[], Object[])} without any of arguments.
	 * @see Intenso#invoke(Object, String, Class[], Object[])
	 */
	public static <T> T invoke(Object target, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
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
	public static <T> T invoke(Object target, String methodName, Class[] argTypes, Object[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Class<?> clazz = target.getClass();
		Method method = clazz.getDeclaredMethod(methodName, argTypes);
		method.setAccessible(true);
		return (T) method.invoke(target, args);
	}

	/**
	 * Alias method to call {@link Intenso#invoke(Class, String)} with try-catch clause.
	 * @return null if nothing returned or exception received.
	 */
	public static <T> T tryInvoke(Class<?> clazz, String methodName) {
		try {
			return invoke(clazz, methodName);
		} catch (NoSuchMethodException e) {
			Log.e(TAG, "no such method " + methodName, e);
		} catch (IllegalAccessException e) {
			Log.e(TAG, "illegal access to " + methodName, e);
		} catch (InvocationTargetException e) {
			Log.e(TAG, "exception thrown " + methodName, e);
		}
		return null;
	}

	/**
	 * Alias method to call {@link Intenso#invoke(Class, String, Class[], Object[])} with try-catch clause.
	 * @return null if nothing returned or exception received.
	 */
	public static <T> T tryInvoke(Class<?> clazz, String methodName, Class[] argTypes, Object[] args) {
		try {
			return invoke(clazz, methodName, argTypes, args);
		} catch (NoSuchMethodException e) {
			Log.e(TAG, "no such method " + methodName, e);
		} catch (IllegalAccessException e) {
			Log.e(TAG, "illegal access to " + methodName, e);
		} catch (InvocationTargetException e) {
			Log.e(TAG, "exception thrown " + methodName, e);
		}
		return null;
	}

	/**
	 * Alias method to call {@link Intenso#invoke(Class, String, Class[], Object[])} without any of arguments.
	 * @see Intenso#invoke(Class, String, Class[], Object[])
	 */
	public static <T> T invoke(Class<?> clazz, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
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
	public static <T> T invoke(Class<?> clazz, String methodName, Class[] argTypes, Object[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = clazz.getDeclaredMethod(methodName, argTypes);
		method.setAccessible(true);
		return (T) method.invoke(null, args);
	}

	/**
	 * Alias method to call {@link Intenso#get(Object, String)} with try-catch clause.
	 * @return null if nothing returned or exception received.
	 */
	public static <T> T tryGet(Object target, String fieldName) {
		try {
			return get(target, fieldName);
		} catch (NoSuchFieldException e) {
			Log.e(TAG, "no such field " + fieldName, e);
		} catch (IllegalAccessException e) {
			Log.e(TAG, "illegal access to " + fieldName, e);
		}
		return null;
	}

	/**
	 * Get a value of a specified hidden field in a target object.
	 * @param target a target object having a field.
	 * @param fieldName to get a value.
	 * @return a value set in the specified field.
	 * @see Class#getDeclaredField(String)
	 * @see Field#get(Object)
	 */
	public static <T> T get(Object target, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		Field f = target.getClass().getDeclaredField(fieldName);
		f.setAccessible(true);
		return (T) f.get(target);
	}

	/**
	 * Alias method to call {@link Intenso#set(Object, String, Object)} with try-catch clause.
	 */
	public static void trySet(Object target, String fieldName, Object value) {
		try {
			set(target, fieldName, value);
		} catch (NoSuchFieldException e) {
			Log.e(TAG, "no such field " + fieldName, e);
		} catch (IllegalAccessException e) {
			Log.e(TAG, "illegal access to " + fieldName, e);
		}
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
	 * Alias method to call {@link Intenso#overwrite(Object, String, Object)} with try-catch clause.
	 */
	public static void tryOverwrite(Object target, String fieldName, Object newValue) {
		try {
			overwrite(target, fieldName, newValue);
		} catch (NoSuchFieldException e) {
			Log.e(TAG, "no such field " + fieldName, e);
		} catch (IllegalAccessException e) {
			Log.e(TAG, "illegal access to " + fieldName, e);
		}
	}

	/**
	 * Overwrite a value of a final field in a specified class.
	 * @param target a target object.
	 * @param fieldName to be modified.
	 * @param newValue to be set in the specified field.
	 * @see Class#getDeclaredField(String)
	 * @see Field#set(Object, Object)
	 */
	public static void overwrite(Object target, String fieldName, Object newValue) throws NoSuchFieldException, IllegalAccessException {
		Class<?> clazz = target.getClass();
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(target, newValue);
	}

	/**
	 * Alias method to call {@link Intenso#overwrite(Class, String, Object)} with try-catch clause.
	 */
	public static void tryOverwrite(Class<?> clazz, String fieldName, Object newValue) {
		try {
			overwrite(clazz, fieldName, newValue);
		} catch (NoSuchFieldException e) {
			Log.e(TAG, "no such field " + fieldName, e);
		} catch (IllegalAccessException e) {
			Log.e(TAG, "illegal access to " + fieldName, e);
		}
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
