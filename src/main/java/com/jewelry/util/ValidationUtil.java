package com.jewelry.util;

public class ValidationUtil {
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * Проверка корректности пароля
     * @param password проверяемый пароль
     * @return true если пароль валиден (не null и длиной не менее 6 символов)
     */
    public static boolean isValidPassword(String password) {
        // Пароль должен быть не null и иметь длину минимум 6 символов
        return password != null && password.length() >= 6;
    }

    /**
     * Проверка корректности имени пользователя
     * @param username проверяемое имя пользователя
     * @return true если имя пользователя валидно
     */
    public static boolean isValidUsername(String username) {
        // Имя пользователя должно:
        // 1. Быть не null
        // 2. Содержать только буквы (русские и английские), цифры, подчеркивания и пробелы
        // 3. Иметь длину от 3 до 30 символов
        return username != null && username.matches("^[a-zA-Zа-яА-ЯёЁ0-9_\\s]{3,30}$");
    }

    /**
     * Проверка корректности названия товара
     * @param title проверяемое название
     * @return true если название валидно
     */
    public static boolean isValidTitle(String title) {
        // Название должно быть не null и иметь длину от 3 до 100 символов
        // trim() убирает пробелы в начале и конце
        return title != null && title.trim().length() >= 3 && title.trim().length() <= 100;
    }

    /**
     * Проверка корректности цены
     * @param price проверяемая цена в виде строки
     * @return true если цена валидна (положительное число)
     */
    public static boolean isValidPrice(String price) {
        try {
            // Пытаемся преобразовать строку в число
            double p = Double.parseDouble(price);
            // Цена должна быть положительной
            return p > 0;
        } catch (NumberFormatException e) {
            // Если преобразование не удалось (не число), возвращаем false
            return false;
        }
    }

    /**
     * Проверка корректности количества товара
     * @param quantity проверяемое количество в виде строки
     * @return true если количество валидно (положительное целое число)
     */
    public static boolean isValidQuantity(String quantity) {
        try {
            // Пытаемся преобразовать строку в целое число
            int q = Integer.parseInt(quantity);
            // Количество должно быть положительным
            return q > 0;
        } catch (NumberFormatException e) {
            // Если преобразование не удалось, возвращаем false
            return false;
        }
    }
}