<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">


<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list orderDTOPage.content as orderDTO>
                        <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>${orderDTO.buyerName}</td>
                            <td>${orderDTO.buyerPhone}</td>
                            <td>${orderDTO.buyerAddress}</td>
                            <td>${orderDTO.orderAmount}</td>
                            <td>${orderDTO.orderStatus}</td>
                            <td>${orderDTO.payStatus}</td>
                            <td>${orderDTO.createTime}</td>
                            <td><a >详情</a></td>
                            <td>
                                <a href="">取消</a>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>


            </div>
        </div>
    </div>

</div>
</body>
</html>