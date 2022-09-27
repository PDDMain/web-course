<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" type="text/css" href="css/normalize.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" type="text/css" href="css/form.css">
        <link rel="stylesheet" type="text/css" href="css/datatable.css">
        <script src="/js/jquery-3.4.1.min.js"></script>
        <script src="/js/notify.min.js"></script>
        <script src="/js/app.js"></script>
    </head>
    <body>
    <header>
        <a href="/index"><img src="img/logo.png" alt="Codeforces" title="Codeforces"/></a>
        <div class="languages">
            <a href="#"><img src="img/gb.png" alt="In English" title="In English"/></a>
            <a href="#"><img src="img/ru.png" alt="In Russian" title="In Russian"/></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                ${user.login}
                |
                <a href="/article">Create article</a>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <ul>
                <li><a href="/index">Home</a></li>
                <li><a href="/users">Users</a></li>
            </ul>
        </nav>
    </header>
    <div class="middle articles">
        <section class="articles">
            <aside>
                <section class="article">
                    <div class="title">
                        Title
                    </div>
                    <div class="text">
                        Text
                    </div>
                </section>
            </aside>
        </section>
        <main>
            <#nested/>
        </main>
    </div>
    <footer>
        <a href="/index">Codeforces</a> &copy; 2019 by Mike Mirzayanov
    </footer>

    <script>
        $(function () {
            <#if message??>
            notify("${message?js_string}");
            </#if>
        })
    </script>

    </body>
    </html>
</#macro>
