package com.mycompany.inviter.customerInviter.services;


import com.mycompany.inviter.customerInviter.beans.Customer;
import com.mycompany.inviter.customerInviter.exceptions.ApplicationException;
import com.mycompany.inviter.customerInviter.jsonUtils.JsonToBeanTransformerComponent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceJsonImpl implements CustomerService {

    public static final String FILE_LOCATION = "src/main/resources" + File.separator + "customers.txt";


    public List<Customer> getCustomers() throws ApplicationException {

        List<Customer> customers = new ArrayList<Customer>();

        try {
            BufferedReader bufferedReader = getBufferedReader();

            JsonToBeanTransformerComponent<Customer> customerJsonToBeanTransformerComponent = new JsonToBeanTransformerComponent<Customer>();

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                Customer customer = customerJsonToBeanTransformerComponent.transform(line, Customer.class);
                customers.add(customer);
            }

            bufferedReader.close();
        } catch (IOException e){
            throw new ApplicationException("Error retrieving customers",e);
        }

        return customers;
    }

    private BufferedReader getBufferedReader() throws FileNotFoundException {
        File file = new File(FILE_LOCATION);

        FileInputStream fis = new FileInputStream(file);

        return new BufferedReader(new InputStreamReader(fis));
    }

}
