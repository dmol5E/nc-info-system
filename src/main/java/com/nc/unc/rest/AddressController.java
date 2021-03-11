package com.nc.unc.rest;

import com.nc.unc.dto.AddressDto;
import com.nc.unc.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/address")
public class AddressController {

    private IAddressService addressService;

    @Autowired
    public AddressController(IAddressService addressService){
        this.addressService = addressService;
    }

    @GetMapping
    public List<AddressDto> getAll(){
        return addressService.getAll();
    }

    @PostMapping
    public void create(@RequestBody AddressDto address){
        addressService.insert(address);
    }

    @GetMapping("/search/address")
    public AddressDto searchAddress(@RequestParam("address") String address) {
        return addressService.getByAddress(address);
    }

    @GetMapping("/search/zipcode")
    public AddressDto searchZipcode(@RequestParam("zipcode") String zipcode) {
        return addressService.getByZipcode(zipcode);
    }

    @GetMapping("/search")
    public AddressDto search(@RequestParam("zipcode") String zipcode,
                             @RequestParam("address") String address) {
        return addressService.getByAddress(address);
    }

}
