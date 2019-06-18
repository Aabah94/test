package com.codecentric.sample.store.service;

import com.codecentric.sample.store.model.Customer;
import com.codecentric.sample.store.service.external.AddressService;
import com.codecentric.sample.store.service.external.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agiletestware.bumblebee.annotations.BooleanValue;
import com.agiletestware.bumblebee.annotations.Bumblebee;
import com.agiletestware.bumblebee.annotations.BumblebeeJUnitListener;


    @Bumblebee(testlab = "Root\\TestBumbleBee", testset = "TestBumbleBee", testplan = "Subject\\TestBumbleBee")
@Service
public class CustomerService {

    @Autowired
    private AddressService addressService;

    @Autowired
    private HostService hostService;


    public String getPLZAddressCombination(Customer customer) {

        String result = Integer.toString(addressService.getPLZForCustomer(customer))
                + "_"
                + addressService.getAddressForCustomer(customer);

        return result;
    }

    public String getPLZAddressCombinationIncludingHostValue(Customer customer, boolean updateExternalSystems) {

        hostService.expand(customer);

        String result = Integer.toString(addressService.getPLZForCustomer(customer))
            + "_"
            + addressService.getAddressForCustomer(customer)
            + "_"
            + customer.getHostValue();

        if (updateExternalSystems) {
            addressService.updateExternalSystems(customer);
        }

        return result;
    }
}
