package com.shsxt.xmjf.api.constants;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016091900544545";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQDzhXzb9obP0gnK6n9YaUhWTBSFtGXOnggOA+OoHDYjuijveGKcOgeTRiJGFlhSSbIHEXp6E6jlsB0fWsVfPNICUhKCRX1E1xoDFGg6d2G/KhfGRk1Orsq1a+BEbpcKLZ1JlCq2YjPV2fgMXpWiTK8RjF/OSR9ol+zJUWcuKil08SEhOxXWhetzQOnxIQOpcuRSFiu1oia4ODT71qIWrTp4ZLGd8GjgAj3p2g4hVitRGa632246fNDGS+ROuA1ycoEOr/2LDs3YKXtRMwu8m3wePp8mgcK8Avcza5m16ld9gDtd1oxUEQnriPRCk5JM2zAEBjS6w6Gaz9zyyv4Qhn1ZAgMBAAECggEBAI+mB5AtbTq8GIC1MSdy+3PEbzqrvn5SJEg4LRumgJ00nB8esXScmolajq8vUSxBQp3+xnOqky/iYltzjQfv9aXeygJEgW+RP0vfxtfuQh+k8d9I6yNl4jeAigtz/O7JF4Ymu8dYSGF2sWaCiNvzRjYgywcBhWCKdegPd8D4p/nNeOlQO0niUitKZXNsP4KwEsbPWtQ5zcmwUESalEC0Jj13cKVxrAD1uU0dSBErN95PVA+JRMXNaDFC5fUI8kw9z0y0PSrmWQpPxqw7DjOKCwY/Qeq+5cAVTonRPjMyEr+9LUw5Iz8tr1lsOdGdVt8lq67zpJJLY3ckr8xszHbDiQECgYEA/6rQyIRTfSg8gtCe7gfhmeqJzvqk5nRoCtUU+svvOnKOb48IW6lcUR8hxEKV6MmbJtZlNviNF+/aOWPv8KJxgdkc0KtgtZUro6U1W+X7DGoS5gO/R92KHta53aP6D6/7OacaF7myANMWi1Nz74ZGUR5cig8RhXtSuT9vqUvTn7kCgYEA89agGF7ronhp9adOwEzockUL+ivba0xLlfisydtglyp6hc8HTEHjMlZT5CPSv45JgYZEm774zQKvXQeRc8xDAhELW96C3DBrBrHTpcTdaHNmLK0sjEPhiqHXuZctmA5c2cK4FqXfxTghH4shWrY8HfsW4vOVn6Oad/wWwSCIWqECgYEAnKkwsg3Tktd67LWlDTorFUNQGlIfhU9DCZA0ANHBz1Cu0tEUUAZhGRw4dqhOb4xJ0+x/rMV0v3wrfgss1YjUgLpnG75HzjyPtwDex2cPBDZjSip1pTWtEuSEk7mM2tv81wD7zhntZu/x2oHSgq0GnWI7ViWRmPdOY1DpvRVHoZECgYEA0OP9IuOuWfFuVNSP0o4QVFRyfk80GieyWThATm1xwg7SB+/kAKAxUt5B4CAQW6o5royoGgTwgDDcqoXhRaKnhQ20/W8SDDAuf/23ozuTsfeqgHuavk8o2OrtNEEe/jTPtn3p1v0jRoFfXvaCDIQx3D1Ozf5/H9O4cTG1DJ4iMgECgYEAosAWrtlYCOyCBC/9r6Eiqy54jegRPYgSfO4OA+1epZTbjQ9JEw+ctgqIw0SF7/nEX5zR0YcONBB3C/YdXGtEI2No/M5nVj9yWTv4T9m2Cv9XRx2O8b0NJ2KPTyT+U/iuKJAVJa+vc3wnFskCQGZjwlNV866eTQ96+mDFZ0+FXws=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAy6PzF584D+7TKyr9uFdhs7evViZ+VBYp5ELkQj4Wj5L5g+vHpvN7z+pTra7/Rjyx9RcoTVhfPpCsoDKrFrafa93XajAJ+kjSxfrth+joloXeTy1Jgy8+r5ipYLK/4mw1OvKaJ6dXHaWjFQutSnGFo5zALvdF69cb2o47RiqSOQmK8hhnKBzV26aqmSS9XWFKixuh9V6UmmeggIAJD/YNp/E6pGu76rdELLAWZJlywKOnhtY/6y1Tia7T5ehL0Wbe0SKcItYgoBFLxDM0ie1JRcVHHrr6D0Rz4yZN/M6Ok0qWJt2d5ncYf5pMHiJL/VvVSvxGgWBKC+9NFDZkoPs3zQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://ez5rsn.natappfree.cc/account/notifyCallBack";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://ez5rsn.natappfree.cc/account/returnCallBack";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

