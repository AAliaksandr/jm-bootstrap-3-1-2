package webBackend.service;

import org.springframework.core.convert.converter.Converter;
import webBackend.model.Role;

public class StringToRoleConverter implements Converter<String, Role> {
    @Override
    public Role convert(String role) {
        return new Role(role);
    }
}
