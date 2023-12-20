package com.polaris.main.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Debugger {
	
	public static void showInfo(Object bean) {
		Map<String, Object> map = new HashMap<>();
		try {
			Arrays.asList(Introspector.getBeanInfo(bean.getClass(), Object.class).
									   getPropertyDescriptors()).
									   stream().
									   filter(prop -> Objects.nonNull(prop.getReadMethod())).
									   forEach(prop -> {
										   try {
											   Object value = prop.getReadMethod().invoke(bean);
											   if (value != null) {
												   map.put(prop.getName(), value);
											   }
										   } catch (Exception e) {
											   e.printStackTrace();
										   }
									   });
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (var entry : map.entrySet()) {
			System.out.println(entry.getKey() + "/" + entry.getValue());
		}
	}

}
