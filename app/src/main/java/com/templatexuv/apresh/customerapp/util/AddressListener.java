package com.templatexuv.apresh.customerapp.util;

import com.templatexuv.apresh.customerapp.model.Address;

/**
 * Created by Apresh on 10/24/2015.
 */
public interface AddressListener {

      void onItemRemoved(long addressId);
      void onEditAddress(Address address);
      void onAddressChecked(Address address);
}
