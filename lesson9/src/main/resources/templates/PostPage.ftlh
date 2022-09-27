<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
<#-- @ftlvariable name="comment" type="ru.itmo.wp.domain.Comment" -->
<#-- @ftlvariable name="comments" type="ru.itmo.wp.domain.Comment[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if post ??>
        <@c.post post=post/>

        <#if user ??>
            <form method="post", action="">
                <div class="field">
                    <div class="name">
                        <label for="text">Your comment</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text">${comment.text!}</textarea>
                    </div>
                    <@c.error "comment.text"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Write">
                </div>
            </form>
        </#if>

        <#list comments as comment>
            <@c.comment comment=comment/>
        </#list>
    <#else>
        No post
    </#if>
</@c.page>
