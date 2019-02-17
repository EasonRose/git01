<script id="pcItemListTemp" type="text/x-handlebars-template">
            {{#each this}}
            <tr>
                <td><strong>{{itemRate}}</strong><span>%{{#if itemAddRate}}+{{itemAddRate}}%{{/if}}</span></td>
                <td>{{itemCycle}}{{itemCycleUnit}}</td>
                <td>
                    {{itemName}}
                        {{#compare itemIsnew '=='1}}
                        <strong class="colorful" new>NEW</strong>
                        {{/compare}}
                        {{#compare itemIsnew '=='0}}
                            {{#compare moveVip '=='1}}
                            <strong class="colorful" app>APP</strong>
                            {{/compare}}
                        {{/compare}}
                        {{#compare itemIsnew '=='0}}
                            {{#compare moveVip '=='0}}
                                {{#compare itemIsrecommend '=='1}}
                                     <strong class="colorful" hot>HOT</strong>
                                {{/compare}}
                            {{/compare}}
                        {{/compare}}
                        {{#compare itemIsnew '=='0}}
                            {{#compare moveVip '=='0}}
                                {{#compare itemIsrecommend '=='0}}
                                    {{#compare password '!='null}}
                        {{#compare password '!=' ""}}
                            <strong class="colorful" psw>LOCK</strong>
                                    {{/compare}}
                                {{/compare}}
                            {{/compare}}
                        {{/compare}}
                        {{/compare}}


                    <#--<p class="item_name">{{itemName}}</p>-->
                </td>
                <td class="trust_range">

                    {{#compare fraction '>'90}}
                        A+
                    {{/compare}}
                    {{#compare fraction '<='90}}
                        {{#compare fraction '>'85}}
                        A
                        {{/compare}}
                    {{/compare}}
                    {{#compare fraction '<='85}}
                        {{#compare fraction '>'75}}
                    A-
                        {{/compare}}
                    {{/compare}}
                    {{#compare fraction '<='75}}
                        {{#compare fraction '>'65}}
                   B
                        {{/compare}}
                    {{/compare}}

                </td>
                <td class="company">
                    <img src="{{guaranteeUsername}}" alt="">
                </td>
                <td>
                    {{#compare itemStatus '=='1}}
                        <strong class='countdown time' data-time="{{countdown}}">
                            <time class="hour"></time>&nbsp;:
                            <time class="min"></time>&nbsp;:
                            <time class="sec"></time>
                        </strong>

                    {{/compare}}
                    {{#compare itemStatus '!='1}}
                        <div class="itemScale" data-value="{{itemScale}}"></div>
                    {{/compare}}

                </td>
                <td>
                    {{#compare itemStatus '=='1}}

                    <p><a href="/item/details/{{id}}?{{id}}"><input class="countdownButton" valid type="button" value='即将开标'></a></p>

                    <#--<div class="notCountdownButton" style="display: none">-->
                        <#--<p class="left_money">可投金额{{laveAmount}}元</p>-->
                        <#--<p><a href="/item/details/{{id}}?{{id}}"><input valid type="button" value='立即投资'></a></p>-->
                    <#--</div>-->

                    {{/compare}}
                    {{#compare itemStatus '==' 10}}
                        <p class="left_money">可投金额{{laveAmount}}元</p>
                        <p><a href="/item/details/{{id}}?{{id}}"><input valid type="button" value='立即投资'></a></p>
                    {{/compare}}
                    {{#compare itemStatus '==' 13}}
                    <p class="left_money">可投金额{{laveAmount}}元</p>
                    <p><a href="/item/details/{{id}}?{{id}}"><input valid type="button" value='立即投资'></a></p>
                    {{/compare}}
                    {{#compare itemStatus '==' 18}}
                    <p class="left_money">可投金额{{laveAmount}}元</p>
                    <p><a href="/item/details/{{id}}?{{id}}"><input valid type="button" value='立即投资'></a></p>
                    {{/compare}}
                    {{#compare itemStatus '==' 20}}
                        <p><a href="/item/details/{{id}}?{{id}}"><input not_valid type="button" value='已抢完'></a></p>
                    {{/compare}}
                    {{#compare itemStatus '==' 30}}
                        <p><a href="/item/details/{{id}}?{{id}}"><input not_valid type="button" value='还款中'></a></p>
                    {{/compare}}
                    {{#compare itemStatus '==' 31}}
                    <p><a href="/item/details/{{id}}?{{id}}"><input not_valid type="button" value='还款中'></a></p>
                    {{/compare}}
                    {{#compare itemStatus '==' 32}}

                    <p style="position: relative">
                        <a href="/item/details/{{id}}?{{id}}" class="yihuankuan">已还款</a>
                        <div class="not_valid_pay"></div>
                    </p>

                    {{/compare}}
                    {{#compare itemStatus '=='23}}
                    <p><a href="/item/details/{{id}}?{{id}}"><input not_valid type="button" value='已满标'></a></p>
                    {{/compare}}

                </td>
            </tr>
            {{/each}}
        </script>
