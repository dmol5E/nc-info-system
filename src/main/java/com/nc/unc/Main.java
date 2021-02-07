package com.nc.unc;


import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

public class Main  {

    public static void main(String[] args) {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        gf f = new gf();

        gfDto gfDto = modelMapper.map(f, com.nc.unc.gfDto.class);
        System.out.println(gfDto);
    }
}
