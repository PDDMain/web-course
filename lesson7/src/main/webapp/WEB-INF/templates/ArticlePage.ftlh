<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if user ??>
        <div class="article">
            <div class="header">Article</div>
            <div class="body">
                <form method="post" action="">
                    <input type="hidden" name="action" value="register"/>
                    <div class="field">
                        <div class="name">
                            <label for="title">Title</label>
                        </div>
                        <div class="value">
                            <input id="title" name="title" value="${title!}"/>
                        </div>
                    </div>
                    <div class="field">
                        <div class="name">
                            <label for="text">Text</label>
                        </div>
                        <div class="value">
                            <input id="text" name="text" value="${text!}"/>
                        </div>
                    </div>
                    <div class="error">
                        ${error!}
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Save">
                    </div>
                </form>
            </div>
        </div>

        <script>
            $(function () {
                $(".article form").submit(function () {
                    const title = $(this).find("input[name='title']").val();
                    const text = $(this).find("input[name='text']").val();
                    const $error = $(this).find(".error");

                    ajax({
                        action: "save",
                        title: title,
                        text: text,
                        // userId: $(user).getId()
                    }, $error)

                    return false;
                });
            })
        </script>
    <#else>
        Please, enter for creating article
    </#if>
</@c.page>