<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация - Jewelry Marketplace</title>
    <link rel="stylesheet" href="${request.contextPath}/css/style.css">
</head>
<body>
<header class="header">
    <div class="container">
        <h1 class="logo">Авторские украшения</h1>
        <nav class="navbar">
            <a href="${request.contextPath}/catalog" class="nav-link">Каталог</a>
            <a href="${request.contextPath}/auth?action=login" class="nav-link">Вход</a>
            <a href="${request.contextPath}/auth?action=register" class="nav-link active">Регистрация</a>
        </nav>
    </div>
</header>

<main class="main">
    <div class="container">
        <div class="auth-form">
            <h2>Регистрация</h2>

            <#if error??>
                <div class="error-message">${error}</div>
            </#if>

            <form method="POST" action="${request.contextPath}/auth">
                <input type="hidden" name="action" value="register">

                <div class="form-group">
                    <label for="email">Электронная почта:</label>
                    <input type="email" id="email" name="email" required>
                </div>

                <div class="form-group">
                    <label for="username">Имя пользователя:</label>
                    <input type="text" id="username" name="username" required>
                </div>

                <div class="form-group">
                    <label for="password">Пароль:</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <div class="form-group">
                    <label for="role">Роль:</label>
                    <select id="role" name="role" required>
                        <option value="">Выберите роль</option>
                        <option value="CUSTOMER">Покупать украшения</option>
                        <option value="MAKER">Продавать украшения</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
            </form>

            <p>Уже есть аккаунт?
                <a href="${request.contextPath}/auth?action=login">Войдите</a>
            </p>
        </div>
    </div>
</main>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const form = document.querySelector('form');

        form.addEventListener('submit', function(event) {
            const email = document.getElementById('email').value.trim();
            const password = document.getElementById('password').value;

            if (!email.includes('@')) {
                alert('Введите корректный email');
                event.preventDefault();
                return;
            }

            if (password.length < 6) {
                alert('Пароль должен содержать минимум 6 символов');
                event.preventDefault();
                return;
            }

            const button = form.querySelector('button[type="submit"]');
            button.textContent = 'Регистрация...';
            button.disabled = true;
        });
    });
</script>
</body>
</html>
