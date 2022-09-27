<#-- @ftlvariable name="noticeForm" type="ru.itmo.wp.form.NoticeCredentials" -->
<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <#if user ??>
        <div class="notice-form">
            <div class="header">Notice</div>
            <div class="body">
                <form method="post" action="">
                    <label>
                        <textarea name="content" rows="10" cols="40">${noticeForm.content!}</textarea>
                    </label>
                    <@c.error "noticeForm.content"/>
                    <div class="button-field">
                        <input type="submit" value="Save">
                    </div>
                </form>
            </div>
        </div>
    </#if>
</@c.page>
