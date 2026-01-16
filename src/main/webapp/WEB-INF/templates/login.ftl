<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход - Jewelry Marketplace</title>
    <link rel="stylesheet" href="/jewelry_marketplace_war/css/style.css">
</head>
<body>
<header class="header">
    <div class="container">
        <h1 class="logo">Авторские украшения</h1>
        <nav class="navbar">
            <a href="${request.contextPath}/catalog" class="nav-link">Каталог</a>
            <a href="${request.contextPath}/auth?action=login" class="nav-link active">Вход</a>
            <a href="${request.contextPath}/auth?action=register" class="nav-link">Регистрация</a>
        </nav>
    </div>
</header>

<main class="main">
    <div class="container">
        <div class="auth-form">
            <h2>Вход</h2>


            <form method="POST" action="${request.contextPath}/auth">
                <input type="hidden" name="action" value="login">

                <div class="form-group">
                    <label for="email">Электронная почта:</label>
                    <input type="email" id="email" name="email" required>
                </div>

                <div class="form-group">
                    <label for="password">Пароль:</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <button type="submit" class="btn btn-primary">Войти</button>
            </form>

            <p>Нет аккаунта?
                <a href="${request.contextPath}/auth?action=register">Зарегистрируйтесь</a>
            </p>
        </div>
    </div>
</main>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const form = document.querySelector('form');

        form.addEventListener('submit', function() {
            const button = form.querySelector('button[type="submit"]');
            button.textContent = 'Вход...';
            button.disabled = true;
        });
    });
</script>
</body>
</html>
