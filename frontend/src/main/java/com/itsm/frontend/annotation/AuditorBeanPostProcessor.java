package com.itsm.frontend.annotation;

import com.itsm.common.entity.AuditOperation;
import com.itsm.frontend.service.AuditOperationAddService;
import com.itsm.frontend.session.UserSessionHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

@Component
public class AuditorBeanPostProcessor implements BeanPostProcessor {

    private Map<String, BeanMarkedMethods> beans = new HashMap<>();

    @Lazy
    @Autowired
    private AuditOperationAddService aos;

    @Lazy
    @Autowired
    private UserSessionHolder holder;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Method[] beanMethods = bean.getClass().getMethods();
        for (Method m : beanMethods) {
            if (m.isAnnotationPresent(Auditable.class)) {
                //System.out.println("Found annotation on __" + m.getName() + "__ from " + beanName);
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
                    recursiveFindInterfaces(original),
                    (proxy, method, args) -> {
                        Object result;
                        if (beanMarkedMethods.contains(method.getName())) {
                            boolean success = true;
                            try {
                                result = method.invoke(bean, args);
                            } catch (Exception e) {
                                success = false;
                                throw e;
                            } finally {
                                String action = holder.getUsername() + "(id=" + holder.getUser().getId() + ") inv: " +
                                        method.getName() + " in " + original.getClass().getName();
                                AuditOperation auditOperation = new AuditOperation(success, action);
                                aos.add(auditOperation);
                            }

                        } else {
                            result = method.invoke(bean, args);
                        }
                        return result;
                    }
            );

        } else {
            return bean;
        }
    }

    private Class<?>[] recursiveFindInterfaces(Object o){
        Class<?> current = o.getClass();
        List<Class<?>> interfaces = new LinkedList<>();
        while (true) {
            Class<?>[] currentInterfaces = current.getInterfaces();
            interfaces.addAll(Arrays.asList(currentInterfaces));
            current = current.getSuperclass();
            if (current==Object.class) {
                break;
            }
        }
        Class<?>[] result = new Class[interfaces.size()];
        for (int i = 0; i < interfaces.size(); i++) {
            result[i] = interfaces.get(i);
        }
        return result;
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
