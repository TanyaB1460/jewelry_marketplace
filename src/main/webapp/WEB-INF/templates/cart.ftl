<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Корзина - Jewelry Marketplace</title>
    <link rel="stylesheet" href="${request.contextPath}/css/style.css">
</head>
<body>
<header class="header">
    <div class="container">
        <h1 class="logo">Авторские украшения</h1>
        <nav class="navbar">
            <a href="${request.contextPath}/catalog" class="nav-link">Каталог</a>
            <a href="${request.contextPath}/cart" class="nav-link active">Корзина</a>
            <a href="${request.contextPath}/logout" class="nav-link">Выйти</a>
        </nav>
    </div>
</header>

<main class="main">
    <div class="container">
        <h2>Корзина</h2>

        <#if cartItems?? && cartItems?size gt 0>
            <div class="cart-table">
                <table>
                    <thead>
                    <tr>
                        <th>Товар</th>
                        <th>Цена</th>
                        <th>Количество</th>
                        <th>Сумма</th>
                        <th>Действие</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list cartItems as item>
                        <tr>
                            <td>${item.productTitle}</td>
                            <td>${item.productPrice?string("0.00")} ₽</td>
                            <td>${item.quantity}</td>
                            <td>${(item.productPrice * item.quantity)?string("0.00")} ₽</td>
                            <td>
                                <form method="POST" action="${request.contextPath}/cart" style="display:inline;">
                                    <input type="hidden" name="action" value="remove">
                                    <input type="hidden" name="orderId" value="${item.id}">
                                    <button type="submit" class="btn btn-small btn-delete">Удалить</button>
                                </form>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>

            <div class="cart-summary">
                <h3>Итого: ${total?string("0.00")} ₽</h3>
                <form method="POST" action="${request.contextPath}/cart" style="display:inline;">
                    <input type="hidden" name="action" value="checkout">
                    <button type="submit" class="btn btn-primary">Оформить заказ</button>
                </form>
                <a href="${request.contextPath}/catalog" class="btn btn-secondary">Продолжить покупки</a>
            </div>
        <#else>
            <p class="empty-message">
                Ваша корзина пуста.
                <a href="${request.contextPath}/catalog">Начните покупки</a>
            </p>
        </#if>
    </div>
</main>


<script>
    window.onload = function() {
        if(window.location.search.includes('success=order')) {
            alert('Заказ успешно оформлен!');

            window.history.replaceState({}, '', window.location.pathname);
        }
    };
</script>
</body>
</html>
