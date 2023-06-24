package com.encentral.entities;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class RoleAttributeConverter implements AttributeConverter<Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Role role) {
        if (role == null)
            return null;
        switch (role) {
            case EMPLOYEE:
                return 0;
            case ADMIN:
                return 1;
            default:
                throw new IllegalArgumentException(role + " not supported.");
        }
    }

    @Override
    public Role convertToEntityAttribute(Integer dbData) {
        if (dbData == null)
            return null;

        switch (dbData) {
            case 0:
                return Role.EMPLOYEE;
            case 1:
                return Role.ADMIN;
            default:
                throw new IllegalArgumentException(dbData + " not supported.");
        }
    }

}