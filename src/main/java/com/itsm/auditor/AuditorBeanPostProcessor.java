package com.itsm.auditor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuditorBeanPostProcessor implements BeanPostProcessor {

    private Map<String, BeanMarkedMethods> beans = new HashMap<>();
    private String url = "jdbc:mysql://localhost:3306/american_medical_app?user=root&password=root&useSSL=false";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Method[] beanMethods = bean.getClass().getMethods();
        for (Method m : beanMethods) {
            if (m.isAnnotationPresent(Auditable.class)) {
                System.out.println("Found annotation on __" + m.getName() + "__ from " + beanName);
                if (beans.containsKey(beanName)) {
                    beans.get(beanName).add(m);

                } else {
                    beans.put(beanName, new BeanMarkedMethods(bean, m));
                }
            }
        }
        return bean;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beans.containsKey(beanName)) {
            BeanMarkedMethods beanMarkedMethods = beans.get(beanName);
            Object original = beanMarkedMethods.getOriginalBean();
            return Proxy.newProxyInstance(
                    original.getClass().getClassLoader(),
                    original.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                        Object result;
                        if (beanMarkedMethods.contains(method.getName())) {
                            boolean success = true;
                            try {
                                result = method.invoke(original, args);
                            } catch (Exception e) {
                                success = false;
                                throw new Exception(e);
                            } finally {
                                String action = method.getName() + " in " + original.getClass().getName();
                                audit(success, action);
                            }

                        } else {
                            result = method.invoke(original, args);
                        }
                        return result;
                    }
            );

        }
        return bean;
    }

    private void audit(boolean success, String action) {
        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement ps = connection.prepareStatement("INSERT INTO audit_operations (time, success, action) VALUES (?, ?, ?)");
            ps.setTimestamp(1, new Timestamp(new java.util.Date().getTime()));  //now
            ps.setBoolean(2, success);
            ps.setString(3, action);
            ps.execute();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private class BeanMarkedMethods {
        private final Object originalBean;
        private List<String> methods;

        BeanMarkedMethods(Object originalBean, Method firstMethod) {
            this.originalBean = originalBean;
            this.methods = new ArrayList<>();
            this.methods.add(firstMethod.getName());
        }

        Object getOriginalBean() {
            return originalBean;
        }

        void add(Method m) {
            methods.add(m.getName());
        }

        boolean contains(String name) {
            return methods.contains(name);
        }
    }
}
