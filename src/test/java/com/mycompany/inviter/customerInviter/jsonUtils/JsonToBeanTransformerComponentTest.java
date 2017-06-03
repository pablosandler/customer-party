package com.mycompany.inviter.customerInviter.jsonUtils;

import com.mycompany.inviter.customerInviter.beans.Customer;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class JsonToBeanTransformerComponentTest {

    @Test
    public void whenProperJsonObjectIsSentAJavaObjectIsCreatedSuccessfully(){
        JsonToBeanTransformerComponent<Customer> jsonToBeanTransformerComponent = new JsonToBeanTransformerComponent<Customer>();

        String jsonObject = "{\"latitude\": \"53.4692815\", \"user_id\": 7, \"name\": \"Frank Kehoe\", \"longitude\": \"-9.436036\"}";

        Customer customer = null;
        try {
            customer = jsonToBeanTransformerComponent.transform(jsonObject,Customer.class);
        } catch (IOException e) {
            fail();
        }

        assertNotNull(customer);
        assertEquals(7, customer.getId().longValue());
        assertEquals("Frank Kehoe", customer.getName());
        assertEquals(53.4692815, customer.getLocation().getLatitude().doubleValue(),0.01);
        assertEquals(-9.436036,customer.getLocation().getLongitude(),0.01);
    }

}