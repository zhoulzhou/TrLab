/*******************************************************
 * Copyright 2010 - 2012 OPPO Mobile Comm Corp., Ltd.
 * All rights reserved.
 *
 * Description	:
 * History  	:
 * (ID, Date, Author, Description)
 *
 *******************************************************/
package com.example.trlab.onoff;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class OppoDemosUtil {

	@SuppressWarnings("unused")
	private final String LOG_TAG = getClass().getSimpleName();

	public static final String PACKAGE_INTERNAL_RESOURCE = "com.android.internal";
	public static final String PACKAGE_OPPO_FILEMANAGER = "com.oppo.filemanager";

	public static Class<?> getClass(String className) {
		Class<?> c = null;
		try {
			c = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return c;
	}

	public static Object inflate(Context context, String className,
			Class<?>[] constructorSignature, Object[] constructorArgs) {
		Object instance = null;
		try {
			Class<?> clazz = context.getClassLoader().loadClass(className);
			Constructor<?> constructor = clazz
					.getConstructor(constructorSignature);
			instance = constructor.newInstance(constructorArgs);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instance;
	}

	public static Object invokeMethod(String className, String methodName,
			Class<?>[] paramTypes, Object receiver, Object... args) {
		Object result = null;
		try {
			Class<?> c = Class.forName(className);
			Method m = c.getMethod(methodName, paramTypes);
			if (receiver == null) {
				receiver = c;
			}
			result = m.invoke(receiver, args);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static boolean isPackageInstalled(Context context, String name) {
		boolean installed = false;
		if (name != null) {
			final PackageManager packageManager = context.getPackageManager();
			final List<PackageInfo> packages = packageManager
					.getInstalledPackages(0);
			if (packages != null) {
				for (PackageInfo info : packages) {
					if (info.packageName.contains(name)) {
						installed = true;
						break;
					}
				}
			}
		} else {
			installed = true;
		}
		return installed;
	}

	public static int getResource(String pkg, String type, String name) {
		Integer value = -1;
		Class<?> resource = getClass(pkg + ".R$" + type);
		if (resource != null) {
			Field field = null;
			try {
				field = resource.getField(name);
				value = (Integer) field.get(resource);
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return value;
	}

	public static int getInternalRes(String type, String name) {
		return getResource(PACKAGE_INTERNAL_RESOURCE, type, name);
	}

	public static String makeSafeString(final byte buffer[]) {
		final int len = buffer.length;
		for (int i = 0; i < len; i++) {
			if (buffer[i] == (byte) 0) {
				return new String(buffer, 0, i);
			}
		}
		return new String(buffer);
	}

	public static String getFormatDecimal(int value, int digits) {
		String format = "";
		for (int i = 0; i < digits; i++) {
			format += "0";
		}
		if (digits > 0) {
			return new DecimalFormat(format).format(value);
		}
		return String.valueOf(value);
	}

	public static String getSimpleClassName(String clsName) {
		int pos = clsName.lastIndexOf('.');
		if (pos > -1) {
			clsName = clsName.substring(pos + 1);
		}
		return clsName;
	}

	public static String updateBool(String text, Object newValue) {
		// TODO Auto-generated method stub
		String[] infos = text.split(" ");
		if (infos.length == 2) {
			infos[1] = newValue.toString();
			return infos[0] + " " + infos[1];
		}
		return text;
	}
}
