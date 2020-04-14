/**
 * Created by avinash on 2/22/20.
 */

package com.learn.custom.annotation;

import com.learn.custom.annotation.model.Employee;
import org.junit.Test;
import java.util.Date;

public class JsonConvertUnitTest {

    @Test
    public void convertToJson(){
        Employee employee =new Employee(123,"Avinash Mishra","Sisir Bagan Road", 1989);
        ObjectToJsonConvertor serializer = new ObjectToJsonConvertor();
        String jsonString = serializer.serializeObjectToJson(employee);
        Date currentDate = employee.getCurrentDate();
        String testStr = "{\"id\":123, \"emp_name\":\"Avinash Mishra\", \"dobYear\":1989, \"currentDate\":"+currentDate+", \"getAge\":31}";
        assert jsonString.equals(testStr);
    }
}
