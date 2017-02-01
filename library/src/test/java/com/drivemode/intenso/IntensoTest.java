package com.drivemode.intenso;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class IntensoTest {
	@Test
	public void ctor() throws Exception {
		try {
			Class<Intenso> clazz = Intenso.class;
			Constructor<Intenso> ctor = clazz.getDeclaredConstructor();
			ctor.setAccessible(true);
			ctor.newInstance();
			fail("could not be instantiated even from reflection");
		} catch (InvocationTargetException e) {
			if (!(e.getCause() instanceof AssertionError))
				fail("constructor throws some exception other than AssertionError!");
		}
	}

	@Test
	public void overwrite_staticField() throws Exception {
		assertNotEquals("hogehogehogehoge", Build.BRAND);
		Intenso.overwrite(Build.class, "BRAND", "hogehogehogehoge");
		assertEquals("hogehogehogehoge", Build.BRAND);
	}

	@Test
	public void overwrite_instanceField() throws Exception {
		InvocationTarget target = new InvocationTarget();
		assertEquals("foo", Intenso.get(target, "foo"));
		Intenso.overwrite(target, "foo", "bar");
		assertEquals("bar", Intenso.get(target, "foo"));
	}

	@Test
	public void invoke_instanceMethod() throws Exception {
		InvocationTarget target = new InvocationTarget();
		assertEquals("hogehogehoge", Intenso.invoke(target, "hoge"));
	}

	@Test
	public void invoke_classMethod() throws Exception {
		assertEquals("fugafugafuga", Intenso.invoke(InvocationTarget.class, "fuga"));
	}

	@Test
	public void getAndSetField() throws Exception {
		InvocationTarget target = new InvocationTarget();
		assertEquals("piyo", Intenso.get(target, "piyo"));
		Intenso.set(target, "piyo", "foo");
		assertEquals("foo", Intenso.get(target, "piyo"));
	}
}