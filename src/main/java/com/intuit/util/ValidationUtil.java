package com.intuit.util;

import com.intuit.exceptions.InvalidEnumValueException;
import com.intuit.exceptions.InvalidUuidException;
import com.intuit.exceptions.PageSizeZeroException;
import com.intuit.exceptions.RequestObjectNullException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.regex.Pattern;


@Slf4j
@UtilityClass
public class ValidationUtil {
    private static final Pattern UUID_REGEX = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    private static final int PAGE_SIZE_ZERO = 0;

    /**
     * This method checks whether the given uuid string is a valid UUID or not.
     */
    public static void validateUuid(String uuid, String fieldName) {
        if (Strings.isBlank(uuid) || !UUID_REGEX.matcher(uuid).matches()) {
            log.error("{} is invalid", fieldName);
            throw new InvalidUuidException(fieldName + " is invalid");
        }
    }

    /**
     * This method checks whether the object is null or not
     */
    public static void validateNullRequestObject(Object data, String fieldName) {
        if (null == data) {
            log.error("{} is null", fieldName);
            throw new RequestObjectNullException(fieldName + " is null");
        }
    }

    /**
     * This method checks whether the enum value is valid or not
     */
    public static <T extends Enum<T>> void validateEnum(String value, String fieldName, Class<T> enumClass) {
        if (!EnumUtils.isValidEnum(enumClass, value.toUpperCase())) {
            log.error("{} is invalid", fieldName);
            throw new InvalidEnumValueException(fieldName + " is invalid");
        }
    }

    /**
     * This method checks whether the page size is 0
     */
    public static void validatePageSize(int pageSize) {
        if (PAGE_SIZE_ZERO == pageSize) {
            log.error("Page size is 0");
            throw new PageSizeZeroException("Page size is 0");
        }
    }

}
