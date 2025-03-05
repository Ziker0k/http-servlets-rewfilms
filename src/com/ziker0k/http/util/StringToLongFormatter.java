package com.ziker0k.http.util;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;

@UtilityClass
public class StringToLongFormatter {
    public Long format(String id) {
        return Long.parseLong(id);
    }

    public boolean isValid(String id) {
        try {
            return Optional.ofNullable(id)
                    .map(StringToLongFormatter::format)
                    .isPresent();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidList(List<String> ids) {
        var booleans = ids.stream().map(StringToLongFormatter::isValid).toList();
        return booleans.stream().allMatch(b -> b);
    }
}
