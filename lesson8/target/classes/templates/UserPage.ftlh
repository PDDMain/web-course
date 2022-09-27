<#-- @ftlvariable name="userId" type="ru.itmo.wp.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if userId ??>
        <div class="login">
            Login: ${userId.login}
        </div>
        <div class="id">
            Id: ${userId.id}
        </div>
        <div class="creation-time">
            Creation time: ${userId.creationTime}
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
