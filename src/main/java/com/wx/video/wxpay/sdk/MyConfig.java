package com.wx.video.wxpay.sdk;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**   
 ** 功能描述：自定义的微信支付配置类
 * @Package: com.github.wxpay.sdk 
 * @author: jiguiquan   
 * @date: 2019年6月13日 上午11:08:35 
 */
public class MyConfig extends WXPayConfig {
	//读取证书文件返回的自己数组
	private byte[] certData;
	
    public MyConfig() throws Exception {
//        String certPath = "classpath:apiclient_cert.p12";
        String path = "apiclient_cert.p12";
        File file = new File(this.getClass().getClassLoader().getResource(path).getFile());
//        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    //我的appid
	@Override
	String getAppID() {
		return "wx0fb11492cabd2544";
	}

	//我的商户号
	@Override
	String getMchID() {
		return "1536427101";
	}

	//我的商户api安全key，在商户平台api安全目录下可设置
	@Override
	String getKey() {
		return "xKLPpyJOORlkVmu1dujovptx1PuxqqIx";
	}

	@Override
	InputStream getCertStream() {
		ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
	}
	
	@Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

	@Override
	IWXPayDomain getWXPayDomain() {
		return new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {
            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new DomainInfo("api.mch.weixin.qq.com", false);
            }
        };
	}
}
