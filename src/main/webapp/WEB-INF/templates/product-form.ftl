<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Товар - Jewelry Marketplace</title>
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
        <div class="form-container">
            <h2><#if product??>Редактировать товар<#else>Создать товар</#if></h2>

            <form method="POST" action="${request.contextPath}/product">
                <#if product??>
                    <input type="hidden" name="id" value="${product.id}">
                </#if>

                <div class="form-group">
                    <label for="title">Название:</label>
                    <input type="text" id="title" name="title" value="${(product.title)!''}" required>
                </div>

                <div class="form-group">
                    <label for="description">Описание:</label>
                    <textarea id="description" name="description" rows="5" required>${(product.description)!''}</textarea>
                </div>

                <div class="form-group">
                    <label for="price">Цена:</label>
                    <input type="number" id="price" name="price" step="0.01" value="${(product.price)!''}" required>
                </div>

                <div class="form-group">
                    <label for="category">Категория:</label>
                    <select id="category" name="category" required>
                        <option value="">Выберите категорию</option>
                        <option value="Кольца" <#if product?? && product.category == "Кольца">selected</#if>>Кольца</option>
                        <option value="Колье" <#if product?? && product.category == "Колье">selected</#if>>Колье</option>
                        <option value="Браслет" <#if product?? && product.category == "Браслет">selected</#if>>Браслет</option>
                        <option value="Серьги" <#if product?? && product.category == "Серьги">selected</#if>>Серьги</option>
                        <option value="Другое" <#if product?? && product.category == "Другое">selected</#if>>Другое</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">
                    <#if product??>Сохранить<#else>Создать</#if>
                </button>
                <a href="${request.contextPath}/product" class="btn btn-secondary">Отмена</a>
            </form>
        </div>
    </div>
</main>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const form = document.querySelector('form');
        const priceInput = document.getElementById('price');

        if (priceInput) {
            priceInput.addEventListener('blur', function() {
                const value = parseFloat(this.value);
                if (value <= 0) {
                    alert('Цена должна быть больше 0');
                    this.focus();
                }
            });
        }

        form.addEventListener('submit', function() {
            const button = form.querySelector('button[type="submit"]');
            button.textContent = 'Сохранение...';
            button.disabled = true;
        });
    });
</script>
</body>
</html>
