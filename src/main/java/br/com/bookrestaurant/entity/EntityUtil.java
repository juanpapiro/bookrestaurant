package br.com.bookrestaurant.entity;

import br.com.bookrestaurant.entity.restaurant.Address;
import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EntityUtil {

    public static String isNullOrBlank(String value, String message) {
        return Optional.ofNullable(value)
                .filter(v -> !v.isBlank())
                .orElseThrow(() -> new RestaurantInvalidException(message));
    }

    public static Integer isNull(Integer value, String message) {
        return (Integer) execute(value, message);
    }

    public static LocalTime isNull(LocalTime value, String message) {
        return (LocalTime) execute(value, message);
    }
    public static UUID isNull(UUID value, String message) {
        return (UUID) execute(value, message);
    }

    public static Address isNull(Address value, String message) {
        return (Address) execute(value, message);
    }

    public static List<OpeningHour> isNull(List<OpeningHour> value, String message) {
        return Optional.ofNullable(value)
                .filter(l -> !l.isEmpty())
                .orElseThrow(() -> new RestaurantInvalidException(message));
    }

    private static Object execute(Object value, String message) {
        return Optional.ofNullable(value)
                .orElseThrow(() -> new RestaurantInvalidException(message));
    }

    public static void validMatches(String value, String regex, String message) {
        if (!value.matches(regex)) {
            throw new RestaurantInvalidException(message);
        }
    }
}
