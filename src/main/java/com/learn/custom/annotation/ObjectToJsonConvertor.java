package com.learn.custom.annotation;

import com.learn.custom.annotation.exceptions.JsonConvertException;
import com.learn.custom.annotation.model.Employee;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by avinash on 2/21/20.
 */
public class ObjectToJsonConvertor {

    public <T> String serializeObjectToJson(T objName){
        Class<?> anyClass = objName.getClass();

        checkRootElement(anyClass);
        initializeInitMethods(anyClass, objName);
        return deriveFields(anyClass, objName);

    }
    private String deriveFields(Class anyClass, Object obj) {
        StringBuilder stringBuilder = new StringBuilder("{");
        Field[] fields = anyClass.getDeclaredFields();
        for(Field field :fields) {
            if(field.isAnnotationPresent(JsonElement.class)) {
                field.setAccessible(true);
                try {
                    stringBuilder.append("\""+ retriveName(field) + "\":" + retriveValue(field, obj)+", ");
                } catch (Exception e){
                    throw new JsonConvertException("Field serializing element failed");
                }
            }
        }

        Method[] methods = anyClass.getDeclaredMethods();
        for(Method method :methods) {
            if(method.isAnnotationPresent(JsonElement.class)) {
                method.setAccessible(true);
                try {
                    stringBuilder.append("\""+ retriveName(method) + "\":" + method.invoke(obj)+", ");
                } catch (Exception e){
                    throw new JsonConvertException("Method serializing element failed");
                }
            }
        }

        stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length());
        return stringBuilder.append("}").toString();
    }

    private String retriveValue(Field field, Object obj) throws Exception {
        if(field.getType() == String.class) {
            return "\""+field.get(obj).toString()+"\"";
        } else {
            return field.get(obj).toString();
        }
    }

    private String retriveName(AccessibleObject fieldOrMethod) {
        String val = fieldOrMethod.getAnnotation(JsonElement.class).name();
        if(fieldOrMethod instanceof Field){
            return val.isEmpty() ? ((Field)fieldOrMethod).getName(): val;
        } else if(fieldOrMethod instanceof Method) {
            return val.isEmpty() ? ((Method)fieldOrMethod).getName() : val;
        }
        return "";
    }

    private void initializeInitMethods(Class anyClass, Object objName) {
        for(Method method :anyClass.getDeclaredMethods()){
            if(method.isAnnotationPresent(Init.class)){
                method.setAccessible(true);
                try {
                    method.invoke(objName);
                } catch (Exception e){
                    throw new JsonConvertException("Method init failed");
                }
            }
        }
    }

    private void checkRootElement(Class anyClass) {
        if(anyClass.getAnnotation(JsonRoot.class) == null){
            throw new JsonConvertException("Root not defined");
        }

    }
}
