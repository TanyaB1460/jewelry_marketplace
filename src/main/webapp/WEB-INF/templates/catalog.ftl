<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Каталог украшений - Jewelry Marketplace</title>
    <link rel="stylesheet" href="/jewelry_marketplace_war/css/style.css">
</head>
<body>
<header class="header">
    <div class="container">
        <h1 class="logo">Авторские украшения</h1>
        <nav class="navbar">
            <a href="${request.contextPath}/catalog" class="nav-link active">Каталог</a>
            <#if isLoggedIn>
                <#if role == "MAKER">
                    <a href="${request.contextPath}/product" class="nav-link">Мои товары</a>
                <#else>
                    <a href="${request.contextPath}/cart" class="nav-link">Корзина</a>
                </#if>
                <a href="${request.contextPath}/logout" class="nav-link">Выйти</a>
            <#else>
                <a href="${request.contextPath}/auth?action=login" class="nav-link">Войти</a>
            </#if>
        </nav>
    </div>
</header>

<main class="main">
    <div class="container">
        <h2>Каталог украшений</h2>

        <#if products?? && products?size gt 0>
            <div class="products-grid">
                <#list products as product>
                    <div class="product-card">
                        <h3>${product.title}</h3>
                        <p class="price">${product.price?string("0.00")} ₽</p>
                        <p class="category">${product.category}</p>
                        <p class="description">
                            <#if product.description??>
                                <#if product.description?length gt 50>
                                    ${product.description?substring(0, 50)}...
                                <#else>
                                    ${product.description}
                                </#if>
                            </#if>
                        </p>
                        <p class="maker">Автор: ${product.makerUsername}</p>
                        <#if isLoggedIn && role == "CUSTOMER">
                            <form method="POST" action="/jewelry_marketplace_war/catalog" style="display:inline;">
                                <input type="hidden" name="productId" value="${product.id}">
                                <input type="number" name="quantity" value="1" min="1" style="width:50px;">
                                <button type="submit" class="btn btn-small">В корзину</button>
                            </form>
                        </#if>
                    </div>
                </#list>
            </div>
        <#else>
            <p class="empty-message">Товары пока отсутствуют.</p>
        </#if>
    </div>
</main>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        var allForms = document.querySelectorAll('form');

        for(var i = 0; i < allForms.length; i++) {
            var form = allForms[i];

            var hasProductId = form.querySelector('input[name="productId"]');

            if(hasProductId) {
                form.onsubmit = function() {
                    var button = this.querySelector('button[type="submit"]');
                    if(button) {
                        button.textContent = 'Добавляем...';
                        button.disabled = true;
                        button.style.opacity = '0.7';
                    }
                };
            }
        }
    });
</script>
</body>
</html>
