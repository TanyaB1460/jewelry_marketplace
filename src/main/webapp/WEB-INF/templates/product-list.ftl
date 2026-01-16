<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Мои товары - Jewelry Marketplace</title>
    <link rel="stylesheet" href="${request.contextPath}/css/style.css">
</head>
<body>
<header class="header">
    <div class="container">
        <h1 class="logo">Авторские украшения</h1>
        <nav class="navbar">
            <a href="${request.contextPath}/catalog" class="nav-link">Каталог</a>
            <a href="${request.contextPath}/product" class="nav-link active">Мои товары</a>
            <a href="${request.contextPath}/logout" class="nav-link">Выйти</a>
        </nav>
    </div>
</header>

<main class="main">
    <div class="container">
        <h2>Мои товары</h2>

        <div class="actions">
            <a href="${request.contextPath}/product/create" class="btn btn-primary">+ Создать товар</a>
        </div>

        <#if products?? && products?size gt 0>
            <div class="products-table">
                <table>
                    <thead>
                    <tr>
                        <th>Название</th>
                        <th>Категория</th>
                        <th>Цена</th>
                        <th>Описание</th>
                        <th>Действия</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list products as product>
                        <tr>
                            <td>${product.title}</td>
                            <td>${product.category}</td>
                            <td>${product.price?string("0.00")} ₽</td>
                            <td class="description-short">
                                <#if product.description?length gt 50>
                                    ${product.description?substring(0, 50)}...
                                <#else>
                                    ${product.description}
                                </#if>
                            </td>
                            <td>
                                <a href="${request.contextPath}/product/edit/${product.id}" class="btn btn-small btn-edit">Редактировать</a>
                                <form method="POST" action="${request.contextPath}/product" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${product.id}">
                                    <button type="submit" class="btn btn-small btn-delete"
                                            onclick="return confirm('Удалить этот товар?')">
                                        Удалить
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        <#else>
            <p class="empty-message">
                Вы ещё не создали ни одного товара.
                <a href="${request.contextPath}/product/create">Создайте первый!</a>
            </p>
        </#if>
    </div>
</main>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        document.querySelectorAll('form[action*="/product"] button.btn-delete').forEach(button => {
            button.addEventListener('click', function(event) {
                if (!confirm('Удалить этот товар?')) {
                    event.preventDefault();
                }
            });
        });
    });
</script>
</body>
</html>
