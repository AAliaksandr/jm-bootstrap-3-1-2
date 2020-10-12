package webBackend.service;

import org.springframework.core.convert.converter.Converter;
import webBackend.model.User;

import java.util.Map;

public class StringsToUserConverter implements Converter<Map<String, String>, User> {
    @Override
    public User convert(Map<String, String> controllerParams) {
        User updateUser = new User();
        updateUser.setId(Long.parseLong(controllerParams.get("id")));
        updateUser.setName(controllerParams.get("name"));
        updateUser.setLastName(controllerParams.get("lastName"));
        updateUser.setAge(Long.parseLong(controllerParams.get("age")));
        updateUser.setEmail(controllerParams.get("email"));
        updateUser.setPassword(controllerParams.get("password"));
        return updateUser;
    }
}
