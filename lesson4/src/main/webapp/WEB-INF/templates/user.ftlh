<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if handle??>
        <#assign u = c.findBy(users, "handle", handle)!/>
    <#elseif user_id??>
        <#assign u = c.findBy(users, "id", user_id)!/>
    </#if>


    <#if u?has_content>
        <section>
            <div class="user">
                <div class="name">
                    <@c.userlink u/>
                </div>
                <div class="list">
                    <ul class="userdata">
                        <li>id: ${u.id}</li>
                        <li>handle: ${u.handle}</li>
                        <li>name: ${u.name}</li>
                    </ul>
                </div>
                <div class="img">
                    <img src="/img/no-title.jpg">
                </div>

            </div>
        </section>
        <section>
            <div class="arrows">
                <@c.userprevtextlink u "←"/>
                <@c.usernexttextlink u "→"/>
            </div>
        </section>
    <#else>
        No such user
    </#if>
</@c.page>
